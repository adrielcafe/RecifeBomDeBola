package com.adrielcafe.recifebomdebola.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.ui.adapter.ContactAdapter;
import com.joanzapata.android.iconify.Iconify;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactFragment extends Fragment {

    @Bind(android.R.id.list)
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);
        View header = inflater.inflate(R.layout.list_header_contact, null, false);
        ButterKnife.bind(this, root);

        IconTextView iconWebSite = (IconTextView) header.findViewById(android.R.id.icon1);
        IconTextView iconEmail = (IconTextView) header.findViewById(android.R.id.icon2);
        TextView webSite = (TextView) header.findViewById(R.id.website);
        TextView email = (TextView) header.findViewById(R.id.email);
        iconWebSite.setTypeface(Iconify.getTypeface(getActivity()));
        iconEmail.setTypeface(Iconify.getTypeface(getActivity()));
        webSite.setText(App.SITE_URL.replace("http://", ""));
        email.setText(App.CONTACT_EMAIL);

        listView.addHeaderView(header);
        listView.setAdapter(new ContactAdapter(getActivity(), Db.contacts));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}