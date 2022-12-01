package com.spp.varjyasarathi;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class LanguageSelection extends AppCompatActivity {

    static final String ENGLISH = "en";
    static final String HINDI = "hi";
    static final String ODIA = "or";

    static final String TAG = "langMOFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        Button btnEnglish = findViewById(R.id.btn_lang_eng);
        Button btnHindi = findViewById(R.id.btn_lang_hindi);
        Button btnOdia = findViewById(R.id.btn_lang_odia);

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale(ENGLISH);
                Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                Bundle b = new Bundle();
                b.putString("language" , ENGLISH);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale(HINDI);
                Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                Bundle b = new Bundle();
                b.putString("language" , HINDI );
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        btnOdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocale(ODIA);
                Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                Bundle b = new Bundle();
                b.putString("language" , ODIA );
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setLocale(String localCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ){
            config.setLocale(new Locale(localCode.toLowerCase()));
            Log.d(TAG, "setLocale: set" );
        }
        else{
            Log.d(TAG, "setLocale: setty");
            config.locale = new Locale(localCode.toLowerCase());
        }
        res.updateConfiguration(config,dm);
    }
}