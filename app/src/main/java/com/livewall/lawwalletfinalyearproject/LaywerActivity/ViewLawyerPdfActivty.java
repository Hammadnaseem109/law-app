package com.livewall.lawwalletfinalyearproject.LaywerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.Adapters.ViewuploaedPdfAdapter;
import com.livewall.lawwalletfinalyearproject.ModelClass.SharePdfModelClass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserDashboard;
import com.livewall.lawwalletfinalyearproject.UserActivity.ViewUploadedPdfActivity;

import java.util.ArrayList;

public class ViewLawyerPdfActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lawyer_pdf_activty);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.borron));
        }
        loadBookDetails();
    }
    private void loadBookDetails() {
        ProgressDialog dialog=new ProgressDialog(ViewLawyerPdfActivty.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading Document");
        dialog.show();
        RecyclerView recyclerView;
        recyclerView=findViewById(R.id.rvpdfviewID);
        ViewuploaedPdfAdapter adapter;
        ArrayList<SharePdfModelClass> list;
        list=new ArrayList<>();
        adapter=new ViewuploaedPdfAdapter(list,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(
                "lawyerPDf").child(userId.toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                if (datasnapshot.exists()){
                    for (DataSnapshot snapshot:datasnapshot.getChildren()) {
//                        String pdfname = snapshot.child("pdfname").getValue().toString();
//                        String pdfurl = snapshot.child("pdfurl").getValue().toString();
                        String pdfname = snapshot.child("pdfname").getValue().toString();
                        String pdfurl = snapshot.child("pdfurl").getValue().toString();
                        String Crname = snapshot.child("curentusername").getValue().toString();
                        String ID = snapshot.child("lawyeruid").getValue().toString();
                        SharePdfModelClass bookingLawyerCLass=new SharePdfModelClass(ID,Crname,pdfname,pdfurl);
                        list.add(bookingLawyerCLass);
                        dialog.hide();
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    dialog.hide();
                    Toast.makeText(ViewLawyerPdfActivty.this, "No Document", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ViewLawyerPdfActivty.this,LawyerDashboard.class));
    }
}