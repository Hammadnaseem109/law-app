package com.livewall.lawwalletfinalyearproject.LaywerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.listofUserFragmenttoAdmin;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.FragmentListofuserToLawyerFragments;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.UpdateLawyerprofileFragment;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.ViewBookAppointmentsFragment;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.ViewPostLawyerFragments;
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.ViewusertochatFragment;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.SharedPrefPkg.PrefManager;
import com.livewall.lawwalletfinalyearproject.UserActivity.LawDiraryDetailsFragment;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserDashboard;
import com.livewall.lawwalletfinalyearproject.UserActivity.ViewUploadedPdfActivity;

public class LawyerDashboard extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_dashboard);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.borron));
        }
        LoadHomeFragmentData();
        Navdrawer();
        getCurentUserDetail();
    }
    private void LoadHomeFragmentData(){
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.framelayoutid,new ViewBookAppointmentsFragment())
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
                    case R.id.lawdiarayID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new LawDiraryDetailsFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.updatelawyerpro:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new UpdateLawyerprofileFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewchatID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ViewusertochatFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewBookingID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ViewBookAppointmentsFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.lisTofuser:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new FragmentListofuserToLawyerFragments())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewpdfID:
                        Intent intent=new Intent(getApplicationContext(),ViewLawyerPdfActivty.class);
                        startActivity(intent);
//                        getSupportFragmentManager().
//                                beginTransaction().
//                                replace(R.id.framelayoutid,new FragmentListofuserToLawyerFragments())
//                                .commit();
//                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewpostuserID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ViewPostLawyerFragments())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        ProgressDialog progressDialog=new ProgressDialog(LawyerDashboard.this);
                        progressDialog.setMessage("Signout");
                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PrefManager prefManager=new PrefManager(LawyerDashboard.this);
                                prefManager.setCurrentstatus("");
                                prefManager.setUserID("");
                                progressDialog.dismiss();
                                Toast.makeText(LawyerDashboard.this, "Signout", Toast.LENGTH_SHORT).show();
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
        DatabaseReference pathRef = database.getReference("VisitProfile").child(
                "lawyer").child(uid);
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