package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class PickupActivity extends AppCompatActivity implements requestsAdapter.OnPostListener {

    ArrayList<Requests> al;
    String username = "";
    String userId= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userName = "";
        userId = acct.getId();
        userName = acct.getGivenName();

        al = new ArrayList<Requests>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Requests requests = document.toObject(Requests.class);
                                System.out.println(requests.id);
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                if(!Objects.isNull(requests)) al.add(requests);
                            }
                            setRecyclerViewData(al,userId);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void setRecyclerViewData(ArrayList<Requests> al, String username){
//        Collections.sort(al, new Comparator<Users>() {
//            public int compare(Requests o1, Requests o2) {
//                // compare two instance of `Score` and return `int` as result.
//                return o1.vsp < o2.vsp? -1
//                        : o1.vsp > o2.vsp ? 1
//                        : 0;
//            }
//        });

//        Collections.reverse(al);

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new requestsAdapter(al, this, username));
    }

    @Override
    public void onPostClick(int position) {
        Requests p = al.get(position);
        Intent intent = new Intent(getApplicationContext(), ConnectPickup.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("request" , p );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLikeCLick(int position, ImageView iv) {

    }
}