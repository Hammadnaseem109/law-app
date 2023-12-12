package com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.livewall.lawwalletfinalyearproject.Adapters.ViewBookingAdapter;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookAppiontmentModelClass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.LawDiraryDetailsFragment;

import java.util.ArrayList;

public class ViewBookAppointmentsFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
     String name,image,ID;

    public ViewBookAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_book_appointments, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        ViewBookedAppointment(view,uid);
        return view;
    }
    private void ViewBookedAppointment(View view,String userId){
        if (userId!=null){
            RecyclerView recyclerView;
            recyclerView=view.findViewById(R.id.recycleappointmentsID);
            ArrayList<BookAppiontmentModelClass> list;
            list=new ArrayList<>();
            ViewBookingAdapter adapters=new ViewBookingAdapter(list,getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(adapters);
//            Toast.makeText(getContext(), "uid"+uid, Toast.LENGTH_SHORT).show();
            firebaseDatabase=FirebaseDatabase.getInstance();
            databaseReference=firebaseDatabase.getReference(
                    "lawyerBookingDetail").child("lawyers").child(userId);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            BookAppiontmentModelClass obj=dataSnapshot.getValue(BookAppiontmentModelClass.class);
                            list.add(obj);
//                            ID = snapshot.child("id").getValue(String.class);
//                            name = snapshot.child("name").getValue(String.class);
//                            BookAppiontmentModelClass obj =new BookAppiontmentModelClass(ID,name,"","");
//                            list.add(obj);

                        }

                        adapters.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getContext(), "NO Appointment", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "no appointment", Toast.LENGTH_SHORT).show();
        }


    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            Bundle bundle=getArguments();
            Fragment fragment=new LawDiraryDetailsFragment();
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