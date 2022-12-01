package com.spp.varjyasarathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class AdminEditDustbin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_dustbin);


        EditText etId = findViewById(R.id.et_id);
        EditText etLat = findViewById(R.id.et_lat);
        EditText etLon = findViewById(R.id.et_lon);
        EditText etType = findViewById(R.id.et_type);

        Button btn = findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etId.equals("") && !etLat.equals("") && !etLon.equals("") && !etType.equals("") ){
                    Dustbins dustbins = new Dustbins(etId.getText().toString(), etLat.getText().toString(),etLon.getText().toString(), etType.getText().toString(),0 );
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Dustbins" ).document(dustbins.DustbinID.trim()).set(dustbins);
                    Intent intent = new Intent(getApplicationContext() , DustbinStatus.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getBaseContext(), "Fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}