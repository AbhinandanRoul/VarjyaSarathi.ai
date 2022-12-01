package com.spp.varjyasarathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.spp.varjyasarathi.databinding.ActivityMainBinding;
import com.spp.varjyasarathi.databinding.ActivityMainMenuBinding;

import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity {

    String lat_f , lon_f ;
    FusedLocationProviderClient mFusedLocationClient;
    public static final int PERMISSION_ID = 44;
    private static final int CAMERA_PERMISSION_CODE = 100;

    ActivityMainMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref = getSharedPreferences("pref", MODE_PRIVATE);

// read value
        String loginPref = sharedPref.getString("login", null);
        if(loginPref!=null){
            Toast.makeText(getBaseContext(), loginPref, Toast.LENGTH_SHORT).show();
        }

        if(loginPref.equals("user")){
            replaceFragment(new HomeFragment());
        }
        else if(loginPref.equals("emp")){
            replaceFragment(new HomeFragmentEmployee());
        }
        else if(loginPref.equals("admin")){
            replaceFragment(new HomeFragmentAdmin());
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    if(loginPref.equals("user")){
                        replaceFragment(new HomeFragment());
                    }
                    else if(loginPref.equals("emp")){
                        replaceFragment(new HomeFragmentEmployee());
                    }
                    else if(loginPref.equals("admin")){
                        replaceFragment(new HomeFragmentAdmin());
                    }
                    else{

                    }
                    break;
                case R.id.scan:
                    Intent intent = new Intent(this , ScanGarbage.class);
                    intent.putExtra("s", "b");
                    startActivity(intent);
                    replaceFragment(new HomeFragment() );
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainMenu.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(MainMenu.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainMenu.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(MainMenu.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
    }

}