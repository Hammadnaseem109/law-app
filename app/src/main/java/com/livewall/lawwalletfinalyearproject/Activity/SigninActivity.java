package com.livewall.lawwalletfinalyearproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.AdminActivity.AdminDashboard;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.LawyerDashboard;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.LawyerProfileActivity;
import com.livewall.lawwalletfinalyearproject.MainActivity;
import com.livewall.lawwalletfinalyearproject.ModelClass.CurrentStatusDetails;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.SharedPrefPkg.PrefManager;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserDashboard;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserProfileActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String Currentuser;
    EditText edemail,edpass;
    TextView tvforgotpass,tvsignup;
    Button btnlogin;
    ProgressDialog dialog;
    DatabaseReference databaseReference;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        // get value current value
        Intent intent = getIntent();
        Currentuser = intent.getStringExtra("Status");
        prefManager=new PrefManager(this);
        // calling method()
        Ids();
        clickListener();
        databaseReference=FirebaseDatabase.getInstance().getReference("Law Wallet").child("Account Details");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.borron));
        }


    }
    private void Ids(){
//        spinner = findViewById(R.id.spinner);
        edemail=findViewById(R.id.edemailid);
        edpass=findViewById(R.id.edpasswordid);
        tvforgotpass=findViewById(R.id.forgotpasswordid);
        btnlogin=findViewById(R.id.btnloginid);
        tvsignup=findViewById(R.id.signuptvid);
    }
    private void clickListener(){
        tvforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotpasswordActivity.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if (edemail.getText().toString().isEmpty() ||
                     edpass.getText().toString().isEmpty()){
                 Toast.makeText(SigninActivity.this, "Enter Email and password", Toast.LENGTH_SHORT).show();
             }else if(!edemail.getText().toString().contains("@gmail.com")){
                 Toast.makeText(SigninActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
             }else if(edpass.getText().toString().length()<6){
                 Toast.makeText(SigninActivity.this, "Please Enter valid Password", Toast.LENGTH_SHORT).show();
             }
             else{
                 dialog = new ProgressDialog(SigninActivity.this);
                 dialog.setMessage("please wait...");
                 dialog.show();
                 SignIn(edemail.getText().toString(),edpass.getText().toString());
             }
            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
    }
    private void SignIn(String email,String pass){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//                String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userId=currentUser.getUid();
                if (currentUser.isEmailVerified()){
                    System.out.println("userss__________________________"+currentUser);
                    databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CurrentStatusDetails currentStatusDetails= snapshot.getValue(CurrentStatusDetails.class);
                            System.out.println("user status______________________"+currentStatusDetails.getCurentstatus());
                            if (currentStatusDetails.getCurentstatus().equals("Lawyer")){
                                prefManager.setCurrentstatus("Lawyer");
                                prefManager.setUserID(userId);
                                Intent intent=new Intent(getApplicationContext(),LawyerProfileActivity.class);
                                startActivity(intent);
                                //next proccess
                            }else if (currentStatusDetails.getCurentstatus().equals("User")){
                                prefManager.setCurrentstatus("User");
                                prefManager.setUserID(userId);
                                Intent intent=new Intent(getApplicationContext(),UserProfileActivity.class);
                                startActivity(intent);
                                // next 2nd step
                            }else if (currentStatusDetails.getCurentstatus().equals("Admin")){
                                prefManager.setCurrentstatus("Admin");
                                prefManager.setUserID(currentUser.toString());
                                Intent intent=new Intent(getApplicationContext(),AdminDashboard.class);
                                startActivity(intent);

                                // 3rd step
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            System.out.println("error________________________"+error.getMessage());
                        }
                    });

//                    Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
//                    intent.putExtra("Status",Currentuser);
//                    Toast.makeText(SigninActivity.this, "welcome"+Currentuser, Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                    dialog.dismiss();
                }
                else{
                    currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SigninActivity.this, "Kindly verify your email", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.hide();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SigninActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        PrefManager prefManager1= new PrefManager(getApplicationContext());
        if (prefManager1.getCurrentstatus().equals("Lawyer")){
            //step 1.
            Intent intent=new Intent(getApplicationContext(),LawyerProfileActivity.class);
            startActivity(intent);
        }else if(prefManager1.getCurrentstatus().equals("User")){
               Intent intent=new Intent(getApplicationContext(),UserProfileActivity.class);
               startActivity(intent);
            // step 2
        }else if (prefManager1.getCurrentstatus().equals("Admin")){
            //step 2
            Intent intent=new Intent(getApplicationContext(),AdminDashboard.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        // Add your code here to handle the back button press
        super.onBackPressed(); // This line closes the app by default
        finishAffinity();
    }
}

