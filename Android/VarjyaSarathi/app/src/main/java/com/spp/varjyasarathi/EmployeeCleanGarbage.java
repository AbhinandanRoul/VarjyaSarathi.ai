package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmployeeCleanGarbage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_clean_garbage);

        Bundle b = this.getIntent().getExtras();
        Dustbins dustbins = (Dustbins) b.getSerializable("dustbin");

        TextView tvId = findViewById(R.id.tv_username);
        TextView tvRod = findViewById(R.id.tv_vsp);

        tvId.setText(dustbins.DustbinID);
        tvRod.setText("R-ROD: " + String.valueOf(dustbins.R_RODs));

        ConstraintLayout clMap = findViewById(R.id.home_c3_map);
        ConstraintLayout clClean = findViewById(R.id.home_c1_garbage);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(dustbins.DustbinID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Dustbins dustbin = document.toObject(Dustbins.class);
                    } else {
                        Log.d("TAG", "No such document");

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        clMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+dustbins.Lat+","+dustbins.Long+"?q="+dustbins.Lat+","+dustbins.Long+" (Dustbin)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        clClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GarbageScan.class );
                startActivity(intent);
            }
        });
    }
}