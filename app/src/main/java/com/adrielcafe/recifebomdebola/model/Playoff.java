package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Eliminatorias")
public class Playoff extends ParseObject implements Serializable {
    public String getTeam1() {
        return getString("equipe1");
    }

    public String getTeam2() {
        return getString("equipe2");
    }

    public int getKey() {
        return getInt("chave");
    }

    public String getCategory() {
        return getString("modalidade");
    }

    public int getRpa() {
        return getInt("rpa");
    }

    public String getField() {
        return getString("campo");
    }

    public String getDate() {
        return getString("data");
    }

    public String getStartHour() {
        return getString("horaInicio");
    }
}