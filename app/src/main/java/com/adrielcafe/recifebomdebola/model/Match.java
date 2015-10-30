package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Jogos")
public class Match extends ParseObject implements Serializable {
    public String getTeam1() {
        return getString("equipe1");
    }

    public String getTeam2() {
        return getString("equipe2");
    }

    public int getTeam1Goals() {
	    Number number = getNumber("golsEquipe1");
	    return number == null ? -1 : number.intValue();
    }

    public int getTeam2Goals() {
	    Number number = getNumber("golsEquipe2");
	    return number == null ? -1 : number.intValue();
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

    public String getField() {
        return getString("campo");
    }

    public String getDate() {
        return getString("data");
    }

    public String getStartHour() {
        return getString("horaInicio");
    }

    public String getStatus() {
        return getString("situacao");
    }
}