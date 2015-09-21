package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Contatos")
public class Contact extends ParseObject implements Serializable {
    public String getName() {
        return getString("nome");
    }

    public String getRole() {
        return getString("funcao");
    }

    public String getPhone() {
        return getString("telefone");
    }
}