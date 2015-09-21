package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Equipes")
public class Team extends ParseObject implements Serializable {
    public String getName() {
        return getString("nome");
    }

    public String getGroup() {
        return getString("grupo");
    }

    public String getCategory() {
        return getString("modalidade");
    }

    public int getRpa() {
        return getInt("rpa");
    }
}