package com.spp.varjyasarathi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spp.varjyasarathi.ml.Wastermodel;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;

public class ScanGarbage extends AppCompatActivity {

    static int REQUEST_IMAGE_CAPTURE = 100;
    List<Category> probability;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_garbage);
        dispatchTakePictureIntent();
        probability = null;
        type= "";

        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.home_c1_garbage);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GarbageScan.class);
                startActivity(intent);
            }
        });

        ConstraintLayout clMap = (ConstraintLayout) findViewById(R.id.home_c3_map);
        clMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("geo:20.3409858,85.8884363?q=20.3409858,85.8884363 (Dustbin)");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = (ImageView) findViewById(R.id.iv_gp1);
            TextView tvType = findViewById(R.id.tv_waste_type);
            imageView.setImageBitmap(imageBitmap);

            try {
                Wastermodel model = Wastermodel.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorImage image = TensorImage.fromBitmap(imageBitmap);

                // Runs model inference and gets result.
                Wastermodel.Outputs outputs = model.process(image);
                probability = outputs.getProbabilityAsCategoryList();
                double cScore = 0;
                if(probability.get(0).getScore() >probability.get(1).getScore()){
                    type = "Organic";
                    cScore = probability.get(0).getScore() * 100 ;
                }
                else{
                    type = "Recyclable";
                    cScore = probability.get(1).getScore() * 100 ;
                }
                tvType.setText(type + "\nCScore : " + cScore);
                Log.d("TAGP", probability.toString());
                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
            }

        }
        else{
            Intent intent = new Intent(getApplicationContext() , MainMenu.class);
            startActivity(intent);
            finish();
        }
    }
}