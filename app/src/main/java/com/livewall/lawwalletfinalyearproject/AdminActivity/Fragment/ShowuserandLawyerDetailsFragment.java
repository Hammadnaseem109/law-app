package com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.LawDiraryDetailsFragment;


public class ShowuserandLawyerDetailsFragment extends Fragment {
    String userUid;
    ImageView imageView;
    TextView txtspec,textexper,txtaddresses,txtchamer,txtname;
    CardView carddel,cardapprove;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String currentimageurl;

    public ShowuserandLawyerDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_showuserand_lawyer_details, container, false);
        userUid = getArguments().getString("uid");
        currentimageurl = getArguments().getString("imageurl");
        IDs(view);
        SetDetails();
        ClickLsitener();
        return view;
    }
    private void IDs(View view) {
        imageView=view.findViewById(R.id.createprofileimageid);
        txtspec=view.findViewById(R.id.speciID);
        txtaddresses=view.findViewById(R.id.addressesID);
        txtchamer=view.findViewById(R.id.chamberID);
        textexper=view.findViewById(R.id.experID);
        txtname=view.findViewById(R.id.NameID);
        cardapprove=view.findViewById(R.id.CardProveID);
        carddel=view.findViewById(R.id.CardDelID);
    }
    private void ClickLsitener(){
        cardapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase=FirebaseDatabase.getInstance();
                databaseReference  =firebaseDatabase.getReference(
                        "VisitProfile").child("lawyer").child(userUid);
                AddLawyerDetailToRealtym obj=new AddLawyerDetailToRealtym(userUid,txtname.getText().toString().trim(),
                        txtchamer.getText().toString().trim(),txtspec.getText().toString().trim(),
                        textexper.getText().toString().trim(),
                        txtaddresses.getText().toString().trim(),
                        currentimageurl,"Yes");
                databaseReference.setValue(obj);
                Toast.makeText(getContext(), "Profile Approve", Toast.LENGTH_SHORT).show();
            }
        });
        carddel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FirebaseDatabase fb=FirebaseDatabase.getInstance();
                DatabaseReference ref;
                ref  =fb.getReference("VisitProfile").child("lawyer").child(userUid);
                AddLawyerDetailToRealtym obj2=new AddLawyerDetailToRealtym(userUid,txtname.getText().toString().trim(),
                        txtchamer.getText().toString().trim(),txtspec.getText().toString().trim(),
                        textexper.getText().toString().trim(),
                        txtaddresses.getText().toString().trim(),
                        currentimageurl,"block");
//                DatabaseReference nodeToDelete = databaseReference.child(obj.getCurrentuid());
//                nodeToDelete.removeValue();
                ref.setValue(obj2);
                Toast.makeText(getContext(), "block", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SetDetails(){
        userUid = getArguments().getString("uid");
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref= firebaseDatabase.getReference("VisitProfile").child(
                "lawyer").child(userUid.toString());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   AddLawyerDetailToRealtym obj=snapshot.getValue(AddLawyerDetailToRealtym.class);

                   Glide.with(getContext()).load(obj.getImageuri()).into(imageView);
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
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            Bundle bundle=getArguments();
            Fragment fragment=new ListofLawyerFragmenttoAdmin();
            fragment.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayoutid,fragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    };

    // Register the callback in the onCreate() method
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    // Unregister the callback in the onDestroy() method
    @Override
    public void onDestroy() {
        super.onDestroy();
        onBackPressedCallback.remove();
    }
    }

