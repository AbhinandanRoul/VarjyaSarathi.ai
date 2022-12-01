package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SigninPage extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 11;
    static final String TAG = "googleSignIn rupel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        // Signed in successfully, show authenticated UI.
        if (acct != null) {

            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String photo;

            if(personPhoto == null){
                photo = "";
            }
            else{
                photo = personPhoto.toString();
            }

            Users user = new Users(personName,personGivenName,personFamilyName,personEmail,personId , photo, 0 );
            Bundle b = new Bundle();
            b.putSerializable("user" , user);
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            intent.putExtras(b);
            intent.putExtra("login", getIntent().getExtras().getString("login") );

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference docIdRef = rootRef.collection("users").document(personId);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "Document exists!");
                        } else {
                            Log.d(TAG, "Document does not exist!");
                            // Add a new document with a generated ID
                                    docIdRef.set(user);
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });



            startActivity(intent);
            finish();

            Log.d(TAG, "" + personEmail);
        }

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if (acct != null) {

                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                String photo;

                if(personPhoto == null){
                    photo = "";
                }
                else{
                    photo = personPhoto.toString();
                }

                Users user = new Users(personName,personGivenName,personFamilyName,personEmail,personId , photo );
                Bundle b = new Bundle();
                b.putSerializable("user" , user);
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.putExtras(b);
                intent.putExtra("login", getIntent().getExtras().getString("login") );

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                DocumentReference docIdRef = rootRef.collection("users").document(personId);
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "Document exists!");
                            } else {
                                Log.d(TAG, "Document does not exist!");
                                // Add a new document with a generated ID
                                docIdRef.set(user);
                            }
                        } else {
                            Log.d(TAG, "Failed with: ", task.getException());
                        }
                    }
                });

                startActivity(intent);
                finish();

                Log.d(TAG, "" + personEmail);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, e);
//            Intent intent = new Intent( getApplicationContext(),MainMenu.class);
//            startActivity(intent);
//            finish();
        }
    }
}