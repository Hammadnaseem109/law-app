package com.livewall.lawwalletfinalyearproject.AdminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.livewall.lawwalletfinalyearproject.Activity.SignupActivity;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ListofLawyerFragmenttoAdmin;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.ShowUserNdLawyersFragment;
import com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment.listofUserFragmenttoAdmin;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.SharedPrefPkg.PrefManager;
import com.livewall.lawwalletfinalyearproject.UserActivity.LawDiraryDetailsFragment;
import com.livewall.lawwalletfinalyearproject.UserActivity.ViewPostFragment;

public class AdminDashboard extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        LoadHomeFragmentData();
        Navdrawer();
    }
    private void LoadHomeFragmentData(){
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.framelayoutid,new ShowUserNdLawyersFragment())
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
                    case R.id.LawDiaryID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new LawDiraryDetailsFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.approvealawyeruserpro:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ListofLawyerFragmenttoAdmin())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.lisTofuserID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new listofUserFragmenttoAdmin())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.viewpostadminID:
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.framelayoutid,new ViewPostFragment())
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.signout:
                        FirebaseAuth.getInstance().signOut();
                        ProgressDialog progressDialog=new ProgressDialog(AdminDashboard.this);
                        progressDialog.setMessage("Signout");
                        progressDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PrefManager prefManager=new PrefManager(AdminDashboard.this);
                                prefManager.setCurrentstatus("");
                                prefManager.setUserID("");
                                progressDialog.dismiss();
                                Toast.makeText(AdminDashboard.this, "Signout", Toast.LENGTH_SHORT).show();
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
}