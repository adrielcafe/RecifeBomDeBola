package com.adrielcafe.recifebomdebola.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Category")
public class Category extends ParseObject implements Serializable {
    public String getName() {
        return getString("name");
    }
}