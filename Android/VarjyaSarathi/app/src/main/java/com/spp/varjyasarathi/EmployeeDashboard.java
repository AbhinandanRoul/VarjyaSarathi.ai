package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmployeeDashboard extends AppCompatActivity implements postsAdapter.OnPostListener {

    ArrayList<Dustbins> al;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);
        al = new ArrayList<Dustbins>();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(acct !=null){
            username = acct.getId();
        }

        ConstraintLayout cl1Add = findViewById(R.id.cl_1_menu);
        ConstraintLayout cl2Edit = findViewById(R.id.cl_2_report);
        ConstraintLayout cl3Delete = findViewById(R.id.cl_3_pothole);

        cl1Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , AdminAddDustbin.class);
                startActivity(intent);
            }
        });

        cl2Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , AdminEditDustbin.class);
                startActivity(intent);
            }
        });

        cl3Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , AdminDeleteDustbin.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Dustbins")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Dustbins dustbins = document.toObject(Dustbins.class);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                al.add(dustbins);
                            }
                            setRecyclerViewData(al,username,1);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_rrod:
                if (checked){
                    if(al.size()>0){
                        setRecyclerViewData(al,username,0);
                    }
                }
                Toast.makeText(getBaseContext(), "Red", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_location:
                if (checked){
                    if(al.size()>0){
                        setRecyclerViewData(al,username,1);
                    }
                }
                Toast.makeText(getBaseContext(), "Black", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setRecyclerViewData(ArrayList<Dustbins> al,String username, int x){
        if(x == 0){
            Collections.sort(al, new Comparator<Dustbins>() {
                public int compare(Dustbins o1, Dustbins o2) {
                    // compare two instance of `Score` and return `int` as result.
                    return o1.R_RODs < o2.R_RODs ? -1
                            : o1.R_RODs > o2.R_RODs ? 1
                            : 0;
                }
            });
            Collections.reverse(al);
        }
        else{
            Collections.sort(al, new Comparator<Dustbins>() {
                public int compare(Dustbins o1, Dustbins o2) {
                    // compare two instance of `Score` and return `int` as result.
                    return o1.DustbinID.compareTo(o2.DustbinID) ;
                }
            });
        }

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new postsAdapter(al, this, username));
    }

    @Override
    public void onPostClick(int position) {
        Log.d("TAGrupel", "onPostClick: clicked.");
        Dustbins dustbins = al.get(position);
        Intent intent = new Intent(getApplicationContext(), EmployeeCleanGarbage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("dustbin" , dustbins );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLikeCLick(int position, ImageView iv) {

    }
}