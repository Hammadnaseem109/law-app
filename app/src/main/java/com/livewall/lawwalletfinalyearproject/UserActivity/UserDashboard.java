package com.livewall.lawwalletfinalyearproject.UserActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.Activity.SigninActivity;
import com.livewall.lawwalletfinalyearproject.Activity.SignupActivity;
import com.livewall.lawwalletfinalyearproject.AdminActivity.AdminDashboard;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ListofLawyerFragmenttoAdmin;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.SharedPrefPkg.PrefManager;

public class UserDashboard extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        LoadHomeFragmentData();
        Navdrawer();
        getCurentUserDetail();
    }
    private void LoadHomeFragmentData(){
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.framelayoutid,new ListofLaywertoUserFragment())
                .commit();
    }
    private void Navdrawer(){
        NavigationView navigationView;
        DrawerLayout drawerLayout;
        Toolbar toolbar=findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.nav_viewid);
        drawerLayout=findViewById(R.id.drawerlayoutid);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.updateuserpro:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new UpdateUserProfileFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.lisToflawyer:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ListofLaywertoUserFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.lawdiarayuserID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new LawDiraryDetailsFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.createpostID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new CreatepostFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.chatwithLawyers:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ChatUserToLawyerFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewpostmenuID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ViewPostFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.sharepdfID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new SharepdfFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewsharepdfID:
                       Intent intent=new Intent(getApplicationContext(),ViewUploadedPdfActivity.class);
                       startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        ProgressDialog progressDialog=new ProgressDialog(UserDashboard.this);
                        progressDialog.setMessage("Signout");
                        progressDialog.show();
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              PrefManager prefManager=new PrefManager(UserDashboard.this);
                              prefManager.setCurrentstatus("");
                              prefManager.setUserID("");
                              progressDialog.dismiss();
                              Toast.makeText(UserDashboard.this, "Signout", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                          }
                      },3000);
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getCurentUserDetail(){
        ImageView imageView;
        imageView=findViewById(R.id.createprofileimageid);
        FirebaseAuth Auth;
        Auth=FirebaseAuth.getInstance();
        FirebaseUser user = Auth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        // Query the database for a specific path or child
        DatabaseReference pathRef = database.getReference("VisitProfile").child("Users").child(uid);
        // Read the data once
        pathRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AddUserDetailToRealtym obj=dataSnapshot.getValue(AddUserDetailToRealtym.class);
                    Glide.with(getApplicationContext())
                            .load(obj.getImageurl())
                            .into(imageView);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                System.err.println("Database read error: " + databaseError.getMessage());
            }
        });
    }

}