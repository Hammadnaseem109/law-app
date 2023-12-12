package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.livewall.lawwalletfinalyearproject.Adapters.ShowUserAdapter;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookedModelClass;
import com.livewall.lawwalletfinalyearproject.ModelClass.BookingLawyerCLass;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;
import java.util.List;

public class ChatUserToLawyerFragment extends Fragment {

    FirebaseAuth mAuth;
    String userUid,image,ID,name;
    public ChatUserToLawyerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat_user_to_lawyer, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userUid= user.getUid();
        ShowLawyers(view,userUid);
        return  view;
    }
    private void ShowLawyers(View view,String userID){
        if (userUid!=null){
        RecyclerView recyclerView;
        recyclerView=view.findViewById(R.id.showchatdemoID);
        List<BookingLawyerCLass> list;
        list=new ArrayList<>();
        ShowLaywerforChatAdapter adapters=new ShowLaywerforChatAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapters);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref= firebaseDatabase.getReference("userBookingDetail");

        ref.child("user").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                if (datasnapshot.exists()){
                    for (DataSnapshot snapshot:datasnapshot.getChildren()){
                        try {
                            String key = snapshot.getKey();
                            image = snapshot.child("lawyerimage").getValue(String.class);
                            ID = snapshot.child("lawyeruid").getValue(String.class);
                            name = snapshot.child("lawyername").getValue(String.class);
                            Toast.makeText(getActivity(), "name"+name, Toast.LENGTH_SHORT).show();
                            BookingLawyerCLass bookingLawyerCLass=new BookingLawyerCLass(ID,name,image);
                            list.add(bookingLawyerCLass);
                        } catch (Exception e) {
                            System.out.println("Error___________________________"+e.getMessage());
                            // Handle the exception or display an error message
//                            Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

//                        list.add(obj);
                    }

                    adapters.notifyDataSetChanged();
                }else {

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }else{
            Toast.makeText(getContext(), "no Lawyer", Toast.LENGTH_SHORT).show();
        }
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