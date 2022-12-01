package com.spp.varjyasarathi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragmentEmployee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentEmployee extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragmentEmployee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragmentEmployee.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragmentEmployee newInstance(String param1, String param2) {
        HomeFragmentEmployee fragment = new HomeFragmentEmployee();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_employee, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String userId = "";
        String userName = "";
        userId = acct.getId();
        userName = acct.getGivenName();

        TextView tvUserName = (TextView) getView().findViewById(R.id.tv_username);
        TextView tvVsp = (TextView) getView().findViewById(R.id.tv_vsp);
        tvUserName.setText(userName.toUpperCase());

        ConstraintLayout cl = (ConstraintLayout) getView().findViewById(R.id.home_c1_garbage);
        ConstraintLayout cl2 = (ConstraintLayout) getView().findViewById(R.id.home_c3_map);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GarbageScan.class);
                startActivity(intent);
            }
        });

        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EmployeeDashboard.class);
                startActivity(intent);
            }
        });

        ConstraintLayout clSocial = (ConstraintLayout) getView().findViewById(R.id.home_c2_social);
        clSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SocialDashboard.class);
                startActivity(intent);
            }
        });

        ConstraintLayout clSocial2 = (ConstraintLayout) getView().findViewById(R.id.home_cm5_social);
        clSocial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SocialDashboard.class);
                startActivity(intent);
            }
        });


        ConstraintLayout clDash = (ConstraintLayout) getView().findViewById(R.id.home_cm4_dashboard);
        clDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EmployeeDashboard.class);
                startActivity(intent);
            }
        });


        ConstraintLayout clPick = (ConstraintLayout) getView().findViewById(R.id.home_cm3_pickup);
        clPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PickupActivity.class);
                startActivity(intent);
            }
        });

        ConstraintLayout clPickup = (ConstraintLayout) getView().findViewById(R.id.home_c3_pickup);
        clPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PickupActivity.class);
                startActivity(intent);
            }
        });

        String jsonFileString = Utils.getJsonFromAssets(getContext(), "VSP_new.json");
        Log.i("data", jsonFileString);
//
//        Gson gson = new Gson();
//        Type listUserType = new TypeToken<List<Users> >() { }.getType();
//
//        List<Users> users = gson.fromJson(jsonFileString, listUserType);
//        for (int i = 0; i < users.size(); i++) {
//            Log.i("data", "> Item " + i + "\n" + users.get(i).personId );
//            if(users.get(i).personId != null){
////                FirebaseFirestore db = FirebaseFirestore.getInstance();
////                db.collection("users" ).document(users.get(i).personId).set(users.get(i));
//            }
//        }
    }
}