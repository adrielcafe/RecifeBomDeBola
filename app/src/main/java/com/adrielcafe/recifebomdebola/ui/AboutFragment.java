package com.adrielcafe.recifebomdebola.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;

import com.adrielcafe.recifebomdebola.R;
import com.joanzapata.android.iconify.Iconify;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutFragment extends Fragment {

    @Bind(R.id.coffee)
    IconTextView coffeeView;
    @Bind(R.id.github)
    IconTextView githubView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, root);
        coffeeView.setTypeface(Iconify.getTypeface(getActivity()));
        githubView.setTypeface(Iconify.getTypeface(getActivity()));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}