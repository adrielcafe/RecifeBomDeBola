package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Jogadores")
public class Player extends ParseObject implements Serializable {
    public String getName() {
        return getString("NOME_JOGADOR");
    }

//    public String getTeam() {
//        return getString("team");
//    }

    public int getNumber() {
        return getInt("NUMERO_JOGADOR");
    }

    public int getYellowCards() {
        return getInt("CARTOES_AMARELOS");
    }

    public int getRedCards() {
        return getInt("CARTOES_VERMELHOS");
    }
}