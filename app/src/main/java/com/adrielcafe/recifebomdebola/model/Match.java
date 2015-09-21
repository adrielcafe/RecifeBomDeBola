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

    public String getTeam1Goals() {
        return getString("golsEquipe1");
    }

    public String getTeam2Goals() {
        return getString("golsEquipe2");
    }

    public int getTeam1YellowCards() {
        return getInt("cartoesAmarelosEquipe1");
    }

    public int getTeam2YellowCards() {
        return getInt("cartoesAmarelosEquipe2");
    }

    public int getTeam1RedCards() {
        return getInt("cartoesVermelhosEquipe1");
    }

    public int getTeam2RedCards() {
        return getInt("cartoesVermelhosEquipe2");
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

    public String getDate() {
        return getString("data");
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