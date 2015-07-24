package com.adrielcafe.recifebomdebola.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;
import com.adrielcafe.recifebomdebola.model.Photo;
import com.adrielcafe.recifebomdebola.ui.adapter.PhotoAdapter;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotosFragment extends Fragment {
    private MainActivity activity;

    @Bind(android.R.id.list)
    AsymmetricGridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, root);
        if(Util.isConnected(activity) || (Db.photos != null && !Db.photos.isEmpty())){
            loadInstagramPhotos();
        } else {
            Toast.makeText(activity, R.string.connect_to_internet, Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadInstagramPhotos(){
        activity.setLoading(true);
        new AsyncTask<Void, Void, List<Photo>>(){
            @Override
            protected List<Photo> doInBackground(Void... params) {
                if(Db.photos == null || Db.photos.isEmpty()){
                    Db.loadPhotos();
                }
                return Db.photos;
            }
            @Override
            protected void onPostExecute(List<Photo> photos) {
                if(photos != null){
                    PhotoAdapter adapter = new PhotoAdapter(activity, photos);
                    AsymmetricGridViewAdapter asymmetricAdapter = new AsymmetricGridViewAdapter<>(activity, gridView, adapter);
                    gridView.setAdapter(asymmetricAdapter);
                } else {
                    Toast.makeText(activity, R.string.no_photo_found, Toast.LENGTH_SHORT).show();
                }
                activity.setLoading(false);
            }
        }.execute();
    }
}