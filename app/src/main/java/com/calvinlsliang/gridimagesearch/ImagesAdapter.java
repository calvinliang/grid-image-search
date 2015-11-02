package com.calvinlsliang.gridimagesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cliang on 10/30/15.
 */
public class ImagesAdapter extends ArrayAdapter<Image> {
    public ImagesAdapter(Context context, List<Image> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Image image = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);

        tvTitle.setText(image.getTitle());

        Picasso.with(getContext()).load(image.getUrl()).into(ivImage);

        return convertView;

    }
}
