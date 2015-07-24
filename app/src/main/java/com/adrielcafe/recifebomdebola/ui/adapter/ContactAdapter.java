package com.adrielcafe.recifebomdebola.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.IconTextView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Contact;
import com.joanzapata.android.iconify.Iconify;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, List<Contact> items) {
        super(context, R.layout.list_item_contact, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_contact, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.iconView.setTypeface(Iconify.getTypeface(getContext()));
        viewHolder.nameView.setText(contact.getName());
        viewHolder.roleView.setText(contact.getRole());
        viewHolder.phoneView.setText(contact.getPhone());

        return convertView;
    }

    static class ViewHolder {
        @Bind(android.R.id.icon)
        IconTextView iconView;
        @Bind(R.id.name)
        TextView nameView;
        @Bind(R.id.role)
        TextView roleView;
        @Bind(R.id.phone)
        TextView phoneView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}