package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Campos")
public class Field extends ParseObject implements Serializable {
    public String getName() {
        return getString("nome");
    }

    public String getAddress() {
        return getString("endereco");
    }

    public String getDistrict() {
        return getString("bairro");
    }

    public int getRpa() {
        return getInt("rpa");
    }

    public ParseGeoPoint getLagLng() {
        return getParseGeoPoint("latLng");
    }

    public boolean isPrivate() {
        return getBoolean("privado");
    }
}