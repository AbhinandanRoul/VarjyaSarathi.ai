package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ConvertVsp extends AppCompatActivity {

    int vsp =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_vsp);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userId = "";
        String userName = "";
        userId = acct.getId();
        userName = acct.getGivenName();

        TextView tvVsp = findViewById(R.id.tv_vsp);
        TextView tvCr = findViewById(R.id.tv_crate);
        TextView tvCa = findViewById(R.id.tv_camount);
        EditText etVsp = findViewById(R.id.editTextNumber);

        ConstraintLayout cl = findViewById(R.id.convert_c1_knowmore);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , PdfView.class);
                startActivity(intent);
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        Users user = document.toObject(Users.class);
                        vsp = user.vsp;
                        tvVsp.setText(String.valueOf(user.vsp) + " VSP ");

                    } else {
                        Log.d("TAG", "No such document");

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        etVsp.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(!etVsp.getText().toString().equals("")){
                    int evsp = Integer.parseInt(etVsp.getText().toString());
                    if(evsp < vsp ){
                        double num = evsp*2.345;
                        tvCa.setText(String.valueOf(num) + " $");
                    }
                    else{
                        tvCa.setText("ENTERED VSP NOT AVAILABLE");
                    }
                }
                else{
                    tvCa.setText("0 $");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }
}