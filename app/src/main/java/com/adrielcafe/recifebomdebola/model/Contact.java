package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Contact")
public class Contact extends ParseObject implements Serializable {
    public String getName() {
        return getString("name");
    }

    public String getRole() {
        return getString("role");
    }

    public String getPhone() {
        return getString("phone");
    }
}