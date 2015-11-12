package com.adrielcafe.recifebomdebola;

import android.app.Application;

import com.adrielcafe.recifebomdebola.model.Category;
import com.adrielcafe.recifebomdebola.model.Contact;
import com.adrielcafe.recifebomdebola.model.Field;
import com.adrielcafe.recifebomdebola.model.LeaderBoard;
import com.adrielcafe.recifebomdebola.model.Match;
import com.adrielcafe.recifebomdebola.model.Player;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.tsengvn.typekit.Typekit;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {
    public static final String INSTAGRAM_ACCESS_TOKEN = "194598492.b42fc03.ef31e8592cf542bdaec4c0f7b0c9a8be";

    public static final String JUDGMENTS_PDF_URL = "https://recifebomdebola.files.wordpress.com/2014/10/julgamentos-2014.pdf";
    public static final String RULES_PDF_URL = "https://recifebomdebola.files.wordpress.com/2014/09/regulamento-oficial-aprovado-pdf.pdf";

    public static final String CONTACT_EMAIL = "recifebomdebola@gmail.com";
    public static final String SITE_URL = "http://recifebomdebola.wordpress.com";
    public static final String FACEBOOK_URL = "http://facebook.com/pages/Recife-Bom-de-Bola/453918341372251";
    public static final String INSTAGRAM_URL = "http://instagram.com/recifebomdebola";

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_RPA = "rpa";
    public static final String EXTRA_DATE = "date";

    public static final int RPA_COUNT = 6;

    @Override
    public void onCreate() {
        super.onCreate();
        initParse();
        JodaTimeAndroid.init(this);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/Ubuntu-Regular.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/Ubuntu-Bold.ttf"))
                .addItalic(Typekit.createFromAsset(this, "fonts/Ubuntu-Italic.ttf"))
                .addBoldItalic(Typekit.createFromAsset(this, "fonts/Ubuntu-BoldItalic.ttf"));
    }

    private void initParse(){
        ParseObject.registerSubclass(LeaderBoard.class);
        ParseObject.registerSubclass(Match.class);
        ParseObject.registerSubclass(Field.class);
        ParseObject.registerSubclass(Category.class);
        ParseObject.registerSubclass(Contact.class);
        ParseObject.registerSubclass(Player.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseUser.enableAutomaticUser();
    }

}