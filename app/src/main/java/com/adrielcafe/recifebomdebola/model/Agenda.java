package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

@ParseClassName("Agenda")
public class Agenda extends ParseObject implements Serializable {
    public String getTeam1() {
        return getString("team1");
    }

    public String getTeam2() {
        return getString("team2");
    }

    public String getField() {
        return getString("field");
    }

    public String getCategory() {
        return getString("category");
    }

    public Date getDate() {
        return getDate("date");
    }

    public int getStartHour() {
        return getInt("startHour");
    }

    public int getEndHour() {
        return getInt("endHour");
    }

    public int getRpa() {
        return getInt("rpa");
    }

    public boolean isConfirmed() {
        return getBoolean("confirmed");
    }
}