package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class DoorstepActivity extends AppCompatActivity {


    public static final int PERMISSION_ID = 44;

    String userId = "";
    String userName = "";
    String lat_f , lon_f ;
    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doorstep);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String userId = "";
        String userName = "";
        userId = acct.getId();
        userName = acct.getGivenName();


        TextView tvStatus = findViewById(R.id.tv_status);
        tvStatus.setText("Initiated...");
        TextView tvStatus2 = findViewById(R.id.tv_status2);
        tvStatus2.setText("Sent request to queue for doorstep garbage pickup");

    }

    //    location stuff
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }





    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }


    @SuppressLint("MissingPermission")

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            lat_f  = String.valueOf(location.getLatitude());
                            lon_f  = String.valueOf(location.getLongitude());

                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            userId = acct.getId();
                            userName = acct.getGivenName();

                            TextView tvStatus = findViewById(R.id.tv_status);
//            tvStatus.setText("Initiated...");
                            TextView tvStatus2 = findViewById(R.id.tv_status2);
//            tvStatus2.setText("Sent request to queue for doorstep garbage pickup");

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference docRef = db.collection("users").document(userId);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                            Users users = document.toObject(Users.class);
                                            if(!users.requestStatus.equals("")){
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                DocumentReference docRef = db.collection("requests").document(users.requestStatus);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                                                Requests requests = document.toObject(Requests.class);
                                                                if(requests.status.equals("connecting")){
                                                                    tvStatus.setText("CONNECTING...");
                                                                    tvStatus2.setText("Looking for Employees near you");
                                                                }
                                                                else if(requests.status.equals("accepted")){
                                                                    tvStatus.setText("ACCEPTED");
                                                                    tvStatus2.setText(requests.id);
                                                                    ConstraintLayout cl = findViewById(R.id.constraintLayout3);
                                                                    cl.setVisibility(View.VISIBLE);
                                                                }
                                                                else{
                                                                    Requests request = new Requests("", lat_f, lon_f,"" ,"" , userId, "", "connecting");

                                                                    db.collection("requests")
                                                                            .add(request)
                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                    Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                                                    tvStatus.setText("CONNECTING...");
                                                                                    tvStatus2.setText("Looking for Employees near you");

                                                                                    db.collection("requests").document(documentReference.getId()).update("id",documentReference.getId());
                                                                                    db.collection("users").document(userId).update("requestStatus",documentReference.getId());

                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Log.w("TAG", "Error adding document", e);
                                                                                }
                                                                            });

                                                                }
                                                            } else {
                                                                Log.d("TAG", "No such document");
                                                                Requests request = new Requests("", lat_f, lon_f,"" ,"" , userId, "", "connecting");

                                                                db.collection("requests")
                                                                        .add(request)
                                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentReference documentReference) {
                                                                                Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                                                tvStatus.setText("CONNECTING...");
                                                                                tvStatus2.setText("Looking for Employees near you");

                                                                                db.collection("requests").document(documentReference.getId()).update("id",documentReference.getId());
                                                                                db.collection("users").document(userId).update("requestStatus",documentReference.getId());

                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Log.w("TAG", "Error adding document", e);
                                                                            }
                                                                        });

                                                            }
                                                        } else {
                                                            Log.d("TAG", "get failed with ", task.getException());
                                                        }
                                                    }
                                                });
                                            }
                                            else{
                                                Requests request = new Requests("", lat_f, lon_f,"" ,"" , userId, "", "connecting");

                                                db.collection("requests")
                                                        .add(request)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                                tvStatus.setText("CONNECTING...");
                                                                tvStatus2.setText("Looking for Employees near you");

                                                                db.collection("requests").document(documentReference.getId()).update("id",documentReference.getId());
                                                                db.collection("users").document(userId).update("requestStatus",documentReference.getId());

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("TAG", "Error adding document", e);
                                                            }
                                                        });

                                            }

                                        } else {
                                            Log.d("TAG", "No such document");

                                        }
                                    } else {
                                        Log.d("TAG", "get failed with ", task.getException());
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                finish();
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat_f  = String.valueOf(mLastLocation.getLatitude());
            lon_f  = String.valueOf(mLastLocation.getLongitude());


            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            userId = acct.getId();
            userName = acct.getGivenName();

            TextView tvStatus = findViewById(R.id.tv_status);
//            tvStatus.setText("Initiated...");
            TextView tvStatus2 = findViewById(R.id.tv_status2);
//            tvStatus2.setText("Sent request to queue for doorstep garbage pickup");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(userId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            Users users = document.toObject(Users.class);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference docRef = db.collection("requests").document(users.requestStatus);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                            Requests requests = document.toObject(Requests.class);
                                            if(requests.status.equals("connecting")){
                                                tvStatus.setText("CONNECTING...");
                                                tvStatus2.setText("Looking for Employees near you");
                                            }
                                            else if(requests.status.equals("accepted")){
                                                ConstraintLayout cl = findViewById(R.id.constraintLayout3);
                                                cl.setVisibility(View.VISIBLE);
                                            }
                                            else{
                                                Requests request = new Requests("", lat_f, lon_f,"" ,"" , userId, "", "connecting");

                                                db.collection("requests")
                                                        .add(request)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                                tvStatus.setText("CONNECTING...");
                                                                tvStatus2.setText("Looking for Employees near you");

                                                                db.collection("requests").document(documentReference.getId()).update("id",documentReference.getId());
                                                                db.collection("users").document(userId).update("requestStatus",documentReference.getId());

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("TAG", "Error adding document", e);
                                                            }
                                                        });

                                            }
                                        } else {
                                            Log.d("TAG", "No such document");
                                            Requests request = new Requests("", lat_f, lon_f,"" ,"" , userId, "", "connecting");

                                            db.collection("requests")
                                                    .add(request)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                            tvStatus.setText("CONNECTING...");
                                                            tvStatus2.setText("Looking for Employees near you");

                                                            db.collection("requests").document(documentReference.getId()).update("id",documentReference.getId());
                                                            db.collection("users").document(userId).update("requestStatus",documentReference.getId());

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("TAG", "Error adding document", e);
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.d("TAG", "get failed with ", task.getException());
                                    }
                                }
                            });
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
                getLastLocation();
            }
        }
    }
}