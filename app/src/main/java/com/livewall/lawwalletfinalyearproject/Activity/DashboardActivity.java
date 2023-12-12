package com.livewall.lawwalletfinalyearproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.livewall.lawwalletfinalyearproject.AdminActivity.AdminDashboard;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.LawyerDashboard;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.LawyerProfileActivity;
import com.livewall.lawwalletfinalyearproject.ModelClass.CurrentStatusDetails;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserDashboard;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserProfileActivity;

import java.util.jar.Attributes;

public class DashboardActivity extends AppCompatActivity {
    String GetcurrentStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }


    @Override
    protected void onStart() {
        super.onStart();
        GetcurrentStatus = getIntent().getExtras().getString("Status");
        if (GetcurrentStatus.equals("Lawyer")){
            startActivity(new Intent(getApplicationContext(), LawyerProfileActivity.class));
        }
        if (GetcurrentStatus.equals("User")){
            startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        }
        if (GetcurrentStatus.equals("Admin")){
            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
        }

    }
}