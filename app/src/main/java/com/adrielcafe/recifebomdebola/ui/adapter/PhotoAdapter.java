package com.adrielcafe.recifebomdebola.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Photo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoAdapter extends ArrayAdapter<Photo> {
    private final LayoutInflater layoutInflater;

    public PhotoAdapter(Context context, List<Photo> items) {
        super(context, 0, items);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Photo photo = getItem(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_photo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Util.getImageLoader(getContext())
                .load(photo.url)
                .into(viewHolder.photoView);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 0;
    }

    static class ViewHolder {
        @Bind(R.id.photo)
        ImageView photoView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}