package com.spp.varjyasarathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.spp.varjyasarathi.ml.CardboardGlassMetalPaperPlastic89;
import com.spp.varjyasarathi.ml.FloodRoadNew;
import com.spp.varjyasarathi.ml.Wastermodel;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cctv extends AppCompatActivity {

    int REQUEST_IMAGE_CAPTURE = 100;
    List<Category> probability;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        dispatchTakePictureIntent();
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
                FloodRoadNew model = FloodRoadNew.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorImage image = TensorImage.fromBitmap(imageBitmap);

                // Runs model inference and gets result.
                FloodRoadNew.Outputs outputs = model.process(image);
                probability = outputs.getProbabilityAsCategoryList();
                double cScore = 0;
                if(probability.get(0).getScore() >probability.get(1).getScore()){
                    type = "Flood";
                    cScore = probability.get(0).getScore() * 100 ;
                }
                else{
                    type = "Not Flood";
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