package com.calvinlsliang.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ImageSettingsActivity extends AppCompatActivity {

    private Spinner sImageSize;
    private Spinner sColorFilter;
    private Spinner sImageType;

    private String spinnerImageSize = null;
    private String spinnerColorFilter = null;
    private String spinnerImageType = null;

    private EditText etSiteFilter;
    private String editTextSiteFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_settings);

        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        initializeStrings();
        initializeSpinners();
        initializeValues();
        initializeListenerActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeStrings() {
        Settings settings = getIntent().getParcelableExtra("settings");
        spinnerImageSize = settings.getImageSize();
        spinnerColorFilter = settings.getColorFilter();
        spinnerImageType = settings.getImageType();
        editTextSiteFilter = settings.getSiteFilter();
    }

    private void initializeSpinners() {
        sImageSize = (Spinner) findViewById(R.id.sImageSize);
        ArrayAdapter<CharSequence> aImageSize = ArrayAdapter.createFromResource(this, R.array.size_array, android.R.layout.simple_spinner_item);
        aImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sImageSize.setAdapter(aImageSize);

        sColorFilter = (Spinner) findViewById(R.id.sColorFilter);
        ArrayAdapter<CharSequence> aColorFilter = ArrayAdapter.createFromResource(this, R.array.color_array, android.R.layout.simple_spinner_item);
        aColorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sColorFilter.setAdapter(aColorFilter);

        sImageType = (Spinner) findViewById(R.id.sImageType);
        ArrayAdapter<CharSequence> aImageType = ArrayAdapter.createFromResource(this, R.array.image_array, android.R.layout.simple_spinner_item);
        aImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sImageType.setAdapter(aImageType);
    }

    private void initializeValues() {
        sImageSize.setSelection(selectByValue(sImageSize, spinnerImageSize));
        sColorFilter.setSelection(selectByValue(sColorFilter, spinnerColorFilter));
        sImageType.setSelection(selectByValue(sImageType, spinnerImageType));
        etSiteFilter.setText(editTextSiteFilter);
    }

    private void initializeListenerActivity() {
        sImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = sImageSize.getItemAtPosition(position).toString();

                switch (selection) {
                    case "Any":
                        selection = null;
                        break;
                    case "Small":
                        selection = "small";
                        break;
                    case "Medium":
                        selection = "medium";
                        break;
                    case "Large":
                        selection = "xxlarge";
                        break;
                    case "Extra-large":
                        selection = "huge";
                        break;
                    default:
                        selection = null;
                        break;
                }
                spinnerImageSize = selection;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = sColorFilter.getSelectedItem().toString();

                switch (selection) {
                    case "Any":
                        selection = null;
                        break;
                    default:
                        selection = selection.toLowerCase();
                        break;
                }
                spinnerColorFilter = selection;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        sImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = sImageType.getSelectedItem().toString();

                switch (selection) {
                    case "Any":
                        selection = null;
                        break;
                    default:
                        selection = selection.toLowerCase();
                        break;
                }
                spinnerImageType = selection;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

    public void onSaveClick(View view) {
        editTextSiteFilter = etSiteFilter.getText().toString();

        Settings settings = new Settings(spinnerImageSize, spinnerColorFilter, spinnerImageType, editTextSiteFilter);

        Intent intent = new Intent();
        intent.putExtra("settings", settings);
        setResult(RESULT_OK, intent);
        finish();
    }

    public int selectByValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }
}
