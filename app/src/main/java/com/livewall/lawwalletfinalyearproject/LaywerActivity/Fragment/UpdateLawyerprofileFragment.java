package com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.HashMap;
import java.util.Map;

public class UpdateLawyerprofileFragment extends Fragment {
    String userUid;
    ImageView imageView;
    EditText txtspec,textexper,txtaddresses,txtchamer,txtname;
    CardView carddel,cardapprove;
    Button btn;
    private FirebaseAuth mAuth;
    public UpdateLawyerprofileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update_lawyerprofile, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
        IDs(view);
        SetDetails();
        ClickListener();
        return view;
    }
    private void IDs(View view) {
        imageView=view.findViewById(R.id.lawprofileID);
        txtspec=view.findViewById(R.id.tvIDspecID);
        txtaddresses=view.findViewById(R.id.tvIDaddressID);
        txtchamer=view.findViewById(R.id.tvIDchcamnumberID);
        textexper=view.findViewById(R.id.tvIDexperiID);
        txtname=view.findViewById(R.id.tvnameID);
        btn=view.findViewById(R.id.btnupdateID);

    }
    private void ClickListener(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateprofileID();
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
                "lawyer").child(userUid.toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    AddLawyerDetailToRealtym obj=snapshot.getValue(AddLawyerDetailToRealtym.class);
                    Glide.with(getContext()).load(obj.getImageuri()).placeholder(R.drawable.progressbarimage).into(imageView);
                    txtname.setText(obj.getLawyername());
                    txtaddresses.setText(obj.getAddresses());
                    textexper.setText(obj.getExper());
                    txtchamer.setText(obj.getLaywernumber());
                    txtspec.setText(obj.getSpec());
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
        DatabaseReference usersRef = database.getReference("VisitProfile").child("lawyer").child(userUid);
        Map<String, Object> updates = new HashMap<>();
        updates.put("lawyername",txtname.getText().toString().trim());
        updates.put("laywernumber",txtchamer.getText().toString().trim());
        updates.put("spec",txtspec.getText().toString().trim());
        updates.put("exper",textexper.getText().toString().trim());
        updates.put("addresses",txtaddresses.getText().toString().trim());
        usersRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Sucessfully", Toast.LENGTH_SHORT).show();
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