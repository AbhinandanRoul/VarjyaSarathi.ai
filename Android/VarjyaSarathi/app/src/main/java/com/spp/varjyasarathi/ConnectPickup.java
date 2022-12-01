package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConnectPickup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_pickup);

        Bundle b = this.getIntent().getExtras();
        Requests requests = (Requests) b.getSerializable("request");

        TextView tvStatus = findViewById(R.id.tv_status);
        tvStatus.setText(requests.status);

        TextView tvStatus2 = findViewById(R.id.tv_status2);
        tvStatus2.setText(requests.id);

        TextView tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText("111 Rs.");

        TextView tvTime = findViewById(R.id.tv_time);
        tvTime.setText("25 mins");




        Button btnConnect = findViewById(R.id.btn_connect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("requests").document(requests.id).update("status","accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        ConstraintLayout cl3 = findViewById(R.id.constraintLayout3);
                        cl3.setVisibility(View.GONE);
                        TextView tvStatus = findViewById(R.id.tv_status);
                        tvStatus.setText("Waiting");

                        TextView tvStatus2 = findViewById(R.id.tv_status2);
                        tvStatus2.setText("Waiting for Connection Confirmation");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error updating document", e);
                            }
                        });
            }
        });

    }
}