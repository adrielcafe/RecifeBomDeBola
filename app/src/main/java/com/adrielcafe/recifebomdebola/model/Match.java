package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;

@ParseClassName("Jogos")
public class Match extends ParseObject implements Serializable {
    public String getTeam1() {
        return getString("equipe1");
    }

    public String getTeam2() {
        return getString("equipe2");
    }

    public String getTeam1Goals() {
        return getString("golsEquipe1");
    }

    public String getTeam2Goals() {
        return getString("golsEquipe2");
    }

    public String getTeam1YellowCards() {
        return getString("cartoesAmarelosEquipe1");
    }

    public String getTeam2YellowCards() {
        return getString("cartoesAmarelosEquipe2");
    }

    public String getTeam1RedCards() {
        return getString("cartoesVermelhosEquipe1");
    }

    public String getTeam2RedCards() {
        return getString("cartoesVermelhosEquipe2");
    }

    public String getStep() {
        return getString("fase");
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

    public Date getDate() {
        return getDate("data");
    }

    public String getStartHour() {
        return getString("horaInicio");
    }

    public String getEndHour() {
        return getString("horaFim");
    }

    public String getStatus() {
        return getString("situacao");
    }
}