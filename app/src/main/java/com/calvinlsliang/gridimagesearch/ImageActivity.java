package com.calvinlsliang.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ImageActivity extends AppCompatActivity {

    private static final String API_BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";

    private AsyncHttpClient client = new AsyncHttpClient();

    private ArrayList<Image> images;
    private ImagesAdapter imagesAdapter;
    private GridView gvImages;

    private final int REQUEST_CODE = 20;

    private String spinnerImageSize = null;
    private String spinnerColorFilter = null;
    private String spinnerImageType = null;
    private String spinnerSiteFilter = null;
    private String searchQuery = null;

    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        images = new ArrayList<Image>();
        imagesAdapter = new ImagesAdapter(this, images);
        gvImages = (GridView) findViewById(R.id.gvImages);
        gvImages.setAdapter(imagesAdapter);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image i = (Image) gvImages.getItemAtPosition(position);

                Intent intent = new Intent(ImageActivity.this, FullImageActivity.class);
                intent.putExtra("image", i);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        gvImages.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                getImages(searchQuery);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                getImages(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getImages(String query) {
        String url = formatUrl(query);

        if (query == null) {
            return;
        }

        client.get(url, null, new JsonHttpResponseHandler() {
            JSONArray imagesArray = null;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    imagesArray = response.getJSONObject("responseData").getJSONArray("results");
                    if (offset == 0) {
                        imagesAdapter.clear();
                    }

                    for (int i = 0; i < imagesArray.length(); i++) {
                        JSONObject imageJSON = imagesArray.getJSONObject(i);

                        String title = imageJSON.getString("titleNoFormatting");
                        String url = imageJSON.getString("url");
                        String tbUrl = imageJSON.getString("tbUrl");
                        Image im = new Image(tbUrl, url, title);
                        images.add(im);
                    }

                    offset += 8;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ImageActivity.this, "FAIL", Toast.LENGTH_SHORT).show();

                }
                imagesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.menuSettings) {
            Intent intent = new Intent(ImageActivity.this, ImageSettingsActivity.class);
            Settings settings = new Settings(spinnerImageSize, spinnerColorFilter, spinnerImageType, spinnerSiteFilter);
            intent.putExtra("settings", settings);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Settings settings = data.getParcelableExtra("settings");

            spinnerImageSize = settings.getImageSize();
            spinnerColorFilter = settings.getColorFilter();
            spinnerImageType = settings.getImageType();
            spinnerSiteFilter = settings.getSiteFilter();

            offset = 0;
            imagesAdapter.clear();
            imagesAdapter.notifyDataSetChanged();
            getImages(searchQuery);
        }
    }

    private String formatUrl(String query) {
        String url = API_BASE_URL;

        try {
            if (query != null) {
                url += "&q=" + URLEncoder.encode(query, "utf-8");
            }

            if (spinnerSiteFilter != null) {
                url += "&as_sitesearch=" + URLEncoder.encode(spinnerSiteFilter, "utf-8");
            }

            if (spinnerColorFilter != null) {
                url += "&imgcolor=" + URLEncoder.encode(spinnerColorFilter, "utf-8");
            }

            if (spinnerImageSize != null) {
                url += "&imgsz=" + URLEncoder.encode(spinnerImageSize, "utf-8");
            }

            if (spinnerImageType != null) {
                url += "&imgtype=" + URLEncoder.encode(spinnerImageType, "utf-8");
            }

            if (offset != 0) {
                url += "start=" + offset;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return url;
        }
        return url;
    }
}
