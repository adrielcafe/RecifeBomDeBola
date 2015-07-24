package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

@ParseClassName("Player")
public class Player extends ParseObject implements Serializable {
    public String getName() {
        return getString("name");
    }

    public String getTeam() {
        return getString("team");
    }

    public String getCoach() {
        return getString("coach");
    }

    public String getAgent() {
        return getString("agent");
    }

    public String getCity() {
        return getString("city");
    }

    public String getDistrict() {
        return getString("district");
    }

    public Date getBirth() {
        return getDate("birth");
    }

    public int getNumber() {
        return getInt("number");
    }

    public int getRedCards() {
        return getInt("redCards");
    }

    public int getYellowCards() {
        return getInt("yellowCards");
    }
}