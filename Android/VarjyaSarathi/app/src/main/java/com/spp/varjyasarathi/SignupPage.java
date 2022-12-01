package com.spp.varjyasarathi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SignupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        SharedPreferences sharedPref = getSharedPreferences("pref", MODE_PRIVATE);

// read value
        String login = sharedPref.getString("login", null);
        if(login!=null && !login.equals("")){
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        }

        ConstraintLayout cvUser = findViewById(R.id.cv_user_login);
        ConstraintLayout cvEmp = findViewById(R.id.cv_employee_login);
        ConstraintLayout cvAdmin = findViewById(R.id.cv_admin_login);

        cvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("login", "user");
                editor.commit();

                Intent intent = new Intent(getApplicationContext() , SigninPage.class);
                intent.putExtra("login","user");
                startActivity(intent);
                finish();
            }
        });

        cvEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("login", "emp");
                editor.commit();

                Intent intent = new Intent(getApplicationContext() , SigninPage.class);
                intent.putExtra("login","emp");
                startActivity(intent);
                finish();
            }
        });

        cvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("login", "admin");
                editor.commit();

                Intent intent = new Intent(getApplicationContext() , SigninPage.class);
                intent.putExtra("login","admin");
                startActivity(intent);
                finish();
            }
        });
    }
}