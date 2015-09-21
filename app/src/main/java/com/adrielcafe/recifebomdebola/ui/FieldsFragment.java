package com.adrielcafe.recifebomdebola.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.model.Field;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;

public class FieldsFragment extends Fragment implements OnMapReadyCallback {
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fields, container, false);
        ButterKnife.bind(this, root);
        activity.setLoading(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap map) {
        LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        for(Field field : Db.fields) {
            LatLng latLng = new LatLng(field.getLagLng().getLatitude(), field.getLagLng().getLongitude());
            latLngBoundsBuilder.include(latLng);
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(field.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(), 50));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Field field = Db.getFieldByName(marker.getTitle());
                Spanned message = Html.fromHtml("RPA " + field.getRpa() + ", " + field.getDistrict() + "<br>"
                        + field.getAddress() + (field.isPrivate() ? "<br><b>PRIVADO</b>" : ""));
                if (field != null) {
                    final NiftyDialogBuilder fieldDialog = NiftyDialogBuilder.getInstance(getActivity());
                    fieldDialog.withTitle(field.getName())
                            .withMessage(message)
                            .withDuration(300)
                            .withEffect(Effectstype.Fadein)
                            .withTitleColor(getResources().getColor(android.R.color.black))
                            .withMessageColor(getResources().getColor(android.R.color.black))
                            .withDialogColor(getResources().getColor(R.color.accent))
                            .withButton1Text(getString(R.string.close))
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fieldDialog.dismiss();
                                }
                            });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fieldDialog.show();
                        }
                    }, 250);
                }
                return false;
            }
        });
        activity.setLoading(false);
    }
    
}