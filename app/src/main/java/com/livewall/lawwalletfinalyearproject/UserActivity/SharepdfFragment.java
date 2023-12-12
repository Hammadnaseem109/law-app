package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.Adapters.ShowLaywerforChatAdapter;
import com.livewall.lawwalletfinalyearproject.Adapters.ShowLaywerforSharePdfAdapter;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookedModelClass;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookingLawyerCLass;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;


public class SharepdfFragment extends Fragment {


     String image,id,name;

    public SharepdfFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sharepdf, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
        ShowLawyers(view);
        return  view;
    }
    private void ShowLawyers(View view){
        FirebaseAuth mAuth;
        RecyclerView recyclerView;
        recyclerView=view.findViewById(R.id.showchatdemoID);
        ArrayList<BookingLawyerCLass> list;
        list=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userUid= user.getUid();
        Toast.makeText(getContext(), "USerID"+userUid, Toast.LENGTH_SHORT).show();
        ShowLaywerforSharePdfAdapter adapters=new ShowLaywerforSharePdfAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapters);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref= firebaseDatabase.getReference("userBookingDetail").child(
                "user");

        ref.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
//                        BookedModelClass  obj=dataSnapshot.getValue(BookedModelClass.class);
//                        list.add(obj);
                         image = snapshot.child("lawyerimage").getValue(String.class);
                         id = snapshot.child("lawyeruid").getValue(String.class);
                         name = snapshot.child("lawyername").getValue(String.class);
                        BookingLawyerCLass bookingLawyerCLass=new BookingLawyerCLass(id,name,image);
                        list.add(bookingLawyerCLass);
                    }

                    adapters.notifyDataSetChanged();
                }else {

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}