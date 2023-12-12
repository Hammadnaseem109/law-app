package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.Activity.SignupActivity;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.HashMap;
import java.util.Map;


public class UpdateUserProfileFragment extends Fragment {
    String userUid;
    ImageView imageView;
    TextView edtxtname,edtxtcnic;
    Button btnupdate;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    public UpdateUserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_update_user_profile, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
        IDs(view);
        SetDetails();
        ClickListener();
        return  view;
    }
    private void IDs(View view) {
        imageView=view.findViewById(R.id.updatepickimageid);
        edtxtcnic=view.findViewById(R.id.edupdatecnicid);
        edtxtname=view.findViewById(R.id.edupdatenameid);
        btnupdate=view.findViewById(R.id.btnupdatecreateprofileid);

    }
    private void ClickListener(){
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofileID();
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("please wait...");
                dialog.show();
            }
        });
    }
    private void SetDetails(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userUid= user.getUid();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref= firebaseDatabase.getReference(
                "VisitProfile").child(
                "Users").child(userUid.toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    AddUserDetailToRealtym obj=snapshot.getValue(AddUserDetailToRealtym.class);
                    Glide.with(getContext()).load(obj.getImageurl()).placeholder(R.drawable.progressbarimage).into(imageView);
//                    Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
                    edtxtname.setText(obj.getName());
                    edtxtcnic.setText(obj.getCnic());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateprofileID(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("VisitProfile").child("Users").child(userUid);
        Map<String, Object> updates = new HashMap<>();
        updates.put("name",edtxtname.getText().toString().trim());
        updates.put("cnic",edtxtcnic.getText().toString().trim());
        usersRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(getContext(), "UpDated Sucessfuly", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

}