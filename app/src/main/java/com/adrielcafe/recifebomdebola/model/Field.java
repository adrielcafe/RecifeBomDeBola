package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Field")
public class Field extends ParseObject implements Serializable {
    public String getName() {
        return getString("name");
    }

    public String getAddress() {
        return getString("address");
    }

    public String getDistrict() {
        return getString("district");
    }

    public int getRpa() {
        return getInt("rpa");
    }

    public ParseGeoPoint getLagLng() {
        return getParseGeoPoint("latLng");
    }

    public boolean isPrivate() {
        return getBoolean("isPrivate");
    }
}