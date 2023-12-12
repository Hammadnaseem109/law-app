package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;


public class ShowLawyerProFileDetailsToUserFragment extends Fragment {
    String userUid;
    ImageView imageView;
    TextView txtspec,textexper,txtaddresses,txtchamer,txtname;
    CardView cardapprove;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String currentimageurl,lawyerName;

    public ShowLawyerProFileDetailsToUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lawyerprofiletouser_details, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
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
        cardapprove=view.findViewById(R.id.CardbookAppointmentID);
    }
    private void ClickLsitener(){
        userUid = getArguments().getString("uid");
        currentimageurl = getArguments().getString("imageurl");
        lawyerName = getArguments().getString("lawyername");
        cardapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                Fragment fragment = new BookingprocessFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                args.putString("uid", userUid);
                args.putString("imageurl", currentimageurl);
                args.putString("lawyername",lawyerName);
                fragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayoutid, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
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
    }

