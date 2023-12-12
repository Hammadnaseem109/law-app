package com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;


public class ViewBookingDetailsFragment extends Fragment {
    String userUid;

    TextView edtxtusername,txtlawyer,txtnumber,txtmsg;
    Button btnupdate;
    private FirebaseAuth mAuth;

    public ViewBookingDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_booking_details, container, false);
        IDs(view);
        Bundle args = getArguments();
        if (args != null) {
            txtnumber.setText(args.getString("phonenumber"));
            txtlawyer.setText(args.getString("msg"));
            edtxtusername.setText(args.getString("bookname"));
        }


        return view;
    }
    private void IDs(View view) {

        edtxtusername=view.findViewById(R.id.bookusersID);
//        txtlawyer=view.findViewById(R.id.booklawyersID);
        txtnumber=view.findViewById(R.id.bookusersnumID);
        txtlawyer=view.findViewById(R.id.bookusersmsgID);



    }
//    private void SetDetails(){
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        userUid= user.getUid();
//        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//        DatabaseReference ref= firebaseDatabase.getReference(
//                "VisitProfile").child(
//                "Users").child(userUid.toString());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    AddUserDetailToRealtym obj=snapshot.getValue(AddUserDetailToRealtym.class);
//
//                    edtxtname.setText(obj.getName());
//                    edtxtcnic.setText(obj.getCnic());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}