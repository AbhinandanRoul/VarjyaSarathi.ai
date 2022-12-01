package com.spp.varjyasarathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class AfterSubmission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_submission);

        Animation ttb = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        TextView tvTitle = findViewById(R.id.tv_as_thankyou);
        tvTitle.setAnimation(ttb);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                finish();
            }
        }, 3200);
    }
}