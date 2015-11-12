package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Classificacao")
public class LeaderBoard extends ParseObject implements Serializable {
    public String getTeam() {
        return getString("equipe");
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

    public int getPointsScored() {
        return getInt("pg");
    }

    public int getMatches() {
        return getInt("j");
    }

    public int getWins() {
        return getInt("v");
    }

    public int getDraws() {
        return getInt("e");
    }

    public int getDefeats() {
        return getInt("d");
    }
}