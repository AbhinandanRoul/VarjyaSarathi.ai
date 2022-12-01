package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminDeleteDustbin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_dustbin);


        EditText etId = findViewById(R.id.et_id);
        EditText etId2 = findViewById(R.id.et_id2);

        Button btn = findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etId.getText().toString().equals(etId2.getText().toString()) && !etId.getText().toString().equals("") ){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Dustbins").document(etId.getText().toString())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                    Toast.makeText(getBaseContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error deleting document", e);
                                    Toast.makeText(getBaseContext(), "Could not delete now", Toast.LENGTH_SHORT).show();
                                }
                            });
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