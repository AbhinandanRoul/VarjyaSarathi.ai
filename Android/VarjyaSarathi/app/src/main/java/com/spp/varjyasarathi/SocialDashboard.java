package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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
import java.util.Objects;

public class SocialDashboard extends AppCompatActivity implements socialAdapter.OnPostListener {

    ArrayList<Users> al;
    String username = "";
    String userId= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_dashboard);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userName = "";
        userId = acct.getId();
        userName = acct.getGivenName();

        al = new ArrayList<Users>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Users users = document.toObject(Users.class);
                                System.out.println(users.personId);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                if(!Objects.isNull(users)) al.add(users);
                            }
                            setRecyclerViewData(al,userId);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void setRecyclerViewData(ArrayList<Users> al, String username){
        Collections.sort(al, new Comparator<Users>() {
            public int compare(Users o1, Users o2) {
                // compare two instance of `Score` and return `int` as result.
                return o1.vsp < o2.vsp? -1
                        : o1.vsp > o2.vsp ? 1
                        : 0;
            }
        });

        Collections.reverse(al);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new socialAdapter(al, this, username));
    }

    @Override
    public void onPostClick(int position) {

    }

    @Override
    public void onLikeCLick(int position, ImageView iv) {

    }
}