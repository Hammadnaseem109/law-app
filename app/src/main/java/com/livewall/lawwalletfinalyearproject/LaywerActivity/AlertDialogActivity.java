package com.livewall.lawwalletfinalyearproject.LaywerActivity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AlertDialogActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String uid;
    String statusapproveID;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        firebaseDatabase=FirebaseDatabase.getInstance();
         Checkprofile();
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
//         ShowApproveprofile();
        // Initialize the Handler and Runnable

    }

    private void Checkprofile(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        DatabaseReference ref= firebaseDatabase.getReference("VisitProfile").child(
                "lawyer").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    AddLawyerDetailToRealtym userProfile = dataSnapshot.getValue(AddLawyerDetailToRealtym.class);
                    statusapproveID = userProfile.getStatus();
                    if (statusapproveID!=null&&statusapproveID.equals("Yes")){
                        Intent intent=new Intent(AlertDialogActivity.this,LawyerDashboard.class);
                        startActivity(intent);
                    }else{
//                        Toast.makeText(AlertDialogActivity.this, "Status" + statusapproveID, Toast.LENGTH_SHORT).show();
                        Toast.makeText(AlertDialogActivity.this, "Admin dont approve your profile yet", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AlertDialogActivity.this, "Error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        if (uid!=null) {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            uid = user.getUid();
            DatabaseReference ref = firebaseDatabase.getReference("VisitProfile").child(
                    "lawyer").child(uid);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        AddLawyerDetailToRealtym userProfile = dataSnapshot.getValue(AddLawyerDetailToRealtym.class);
                        statusapproveID = userProfile.getStatus();
                        if (statusapproveID != null && statusapproveID.equals("Yes")) {
                            Intent intent = new Intent(AlertDialogActivity.this, LawyerDashboard.class);
                            startActivity(intent);
                        } else {
//                            Toast.makeText(AlertDialogActivity.this, "Status" + statusapproveID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(AlertDialogActivity.this, "Admin dont approve your profile yet", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(AlertDialogActivity.this, "Error" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (uid!=null){
            DatabaseReference ref= firebaseDatabase.getReference("VisitProfile").child(
                    "lawyer").child(uid);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        AddLawyerDetailToRealtym userProfile = dataSnapshot.getValue(AddLawyerDetailToRealtym.class);
                        statusapproveID = userProfile.getStatus();
                        if (statusapproveID!=null&&statusapproveID.equals("Yes")){
                            Intent intent=new Intent(AlertDialogActivity.this,LawyerDashboard.class);
                            startActivity(intent);
                        }else{
//                            Toast.makeText(AlertDialogActivity.this, "Status" + statusapproveID, Toast.LENGTH_SHORT).show();
                            Toast.makeText(AlertDialogActivity.this, "Admin dont approve your profile yet", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(AlertDialogActivity.this, "Error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            Exit();
        }
    };
    private void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("LegalOpt");
        builder.setMessage("Are you sure you want to exit the LegalOpt?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}

