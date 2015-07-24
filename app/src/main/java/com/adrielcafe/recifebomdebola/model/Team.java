package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Team")
public class Team extends ParseObject implements Serializable {
    public String getName() {
        return getString("name");
    }

    public String getCategory() {
        return getString("category");
    }

    public int getRpa() {
        return getInt("rpa");
    }

    public int getPositon() {
        return getInt("positon");
    }
    public void setPositon(int value) {
        put("positon", value);
    }

    public int getMatches() {
        return getInt("matches");
    }

    public int getScore() {
        return getInt("score");
    }

    public int getRedCards() {
        return getInt("redCards");
    }

    public int getYellowCards() {
        return getInt("yellowCards");
    }
}