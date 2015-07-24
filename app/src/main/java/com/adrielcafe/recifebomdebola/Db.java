package com.adrielcafe.recifebomdebola;

import android.content.Context;

import com.adrielcafe.recifebomdebola.model.Agenda;
import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.Contact;
import com.adrielcafe.recifebomdebola.model.Field;
import com.adrielcafe.recifebomdebola.model.Photo;
import com.adrielcafe.recifebomdebola.model.Player;
import com.adrielcafe.recifebomdebola.model.Team;
import com.goebl.david.Webb;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Db {
    public static List<Team> teams;
    public static List<Field> fields;
    public static List<Contact> contacts;
    public static List<Photo> photos;
    public static List<Category> categories;
    public static List<String> rpas;

    public static void loadDb(Context context){
        try {
            if(Util.isConnected(context)) {
                fields = ParseQuery.getQuery(Field.class).find();
                ParseObject.pinAllInBackground(fields);
            } else {
                fields = ParseQuery.getQuery(Field.class)
                        .fromLocalDatastore()
                        .find();
            }
        } catch (Exception e){
            fields = new ArrayList<>();
        }

        try {
            if(Util.isConnected(context)) {
                contacts = ParseQuery.getQuery(Contact.class).find();
                ParseObject.pinAllInBackground(contacts);
            } else {
                contacts = ParseQuery.getQuery(Contact.class)
                        .fromLocalDatastore()
                        .find();
            }
        } catch (Exception e){
            contacts = new ArrayList<>();
        }

        try {
            if(Util.isConnected(context)) {
                teams = ParseQuery.getQuery(Team.class).find();
                ParseObject.pinAllInBackground(teams);
            } else {
                teams = ParseQuery.getQuery(Team.class)
                        .fromLocalDatastore()
                        .find();
            }
        } catch (Exception e){
            teams = new ArrayList<>();
        }

        try {
            if(Util.isConnected(context)) {
                categories = ParseQuery.getQuery(Category.class).find();
                ParseObject.pinAllInBackground(categories);
            } else {
                categories = ParseQuery.getQuery(Category.class)
                        .fromLocalDatastore()
                        .find();
            }
        } catch (Exception e){
            categories = new ArrayList<>();
        }

        rpas = new ArrayList<>();
        for(int i = 1; i <= App.RPA_COUNT; i++){
            rpas.add("RPA " + i);
        }
        rpas.add("TODOS");
    }

    public static void loadPhotos(){
        if(photos == null || photos.isEmpty()) {
            try {
                JSONObject json = Webb.create()
                        .get(Util.INSTAGRAM_MEDIA_RECENT_URL)
                        .asJsonObject()
                        .getBody();
                JSONArray photosJson = json.getJSONArray("data");
                photos = new ArrayList<>();
                for (int i = 0; i < photosJson.length(); i++) {
                    JSONObject photoJson = photosJson.getJSONObject(i);
                    if (photoJson.getString("type").equalsIgnoreCase("image")) {
                        String url = photoJson
                                .getJSONObject("images")
                                .getJSONObject("standard_resolution")
                                .getString("url");
                        Photo photo = new Photo(url);
                        photos.add(photo);
                    }
                }
            } catch (Exception e) { }
        }
    }

    public static void getTeams(Context context, String category, int rpa, FindCallback<Team> callback){
        try {
            if(Util.isConnected(context)) {
                if(rpa == 0){
                    ParseQuery.getQuery(Team.class)
                            .whereEqualTo("category", category)
                            .findInBackground(callback);
                } else {
                    ParseQuery.getQuery(Team.class)
                            .whereEqualTo("category", category)
                            .whereEqualTo("rpa", rpa)
                            .findInBackground(callback);
                }
            } else {
                ParseQuery.getQuery(Team.class)
                        .fromLocalDatastore()
                        .whereEqualTo("category", category)
                        .whereEqualTo("rpa", rpa)
                        .findInBackground(callback);
            }
        } catch (Exception e){ }
    }

    public static void getAgenda(Context context, String category, int rpa, FindCallback<Agenda> callback){
        try {
            if(Util.isConnected(context)) {
                if(rpa == 0){
                    ParseQuery.getQuery(Agenda.class)
                            .whereEqualTo("category", category)
                            .orderByDescending("date")
                            .findInBackground(callback);
                } else {
                    ParseQuery.getQuery(Agenda.class)
                            .whereEqualTo("category", category)
                            .whereEqualTo("rpa", rpa)
                            .orderByDescending("date")
                            .findInBackground(callback);
                }
            } else {
                ParseQuery.getQuery(Agenda.class)
                        .fromLocalDatastore()
                        .whereEqualTo("category", category)
                        .whereEqualTo("rpa", rpa)
                        .orderByDescending("date")
                        .findInBackground(callback);
            }
        } catch (Exception e){ }
    }

    public static void getTeamPlayers(Context context, String teamName, FindCallback<Player> callback){
        try {
            if(Util.isConnected(context)) {
                ParseQuery.getQuery(Player.class)
                        .whereEqualTo("team", teamName)
                        .orderByAscending("number")
                        .findInBackground(callback);
            } else {
                ParseQuery.getQuery(Player.class)
                        .fromLocalDatastore()
                        .whereEqualTo("team", teamName)
                        .orderByAscending("number")
                        .findInBackground(callback);
            }
        } catch (Exception e){ }
    }

    public static Field getFieldByName(String name){
        for(Field field : fields){
            if(field.getName().equalsIgnoreCase(name)){
                return field;
            }
        }
        return null;
    }

    public static int getRpaAtIndex(int rpaIndex){
        return rpaIndex == App.RPA_COUNT ? 0 : rpaIndex + 1;
    }

}