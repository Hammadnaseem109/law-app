package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livewall.lawwalletfinalyearproject.Adapters.ShowLaywerAdapter;
import com.livewall.lawwalletfinalyearproject.Adapters.ViewProfileLaywerAdapter;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ListofLaywertoUserFragment extends Fragment {
    private SearchView searchBox;
    ProgressDialog dialog;
    ArrayList<AddLawyerDetailToRealtym> list;
    ViewProfileLaywerAdapter adapters;
    public ListofLaywertoUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_listof_laywerto_user, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.borron));
        }
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait...");
        dialog.show();
        Getlistoflawyer(view);
        return view;

    }
    private void Getlistoflawyer(View view){
        RecyclerView recyclerView;
        recyclerView=view.findViewById(R.id.listlawyersrecycleviewID);

        list=new ArrayList<>();
        adapters=new ViewProfileLaywerAdapter(list,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapters);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref= firebaseDatabase.getReference("VisitProfile").child(
                "lawyer");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        AddLawyerDetailToRealtym  obj=dataSnapshot.getValue(AddLawyerDetailToRealtym.class);
                        list.add(obj);
                    }
                    dialog.dismiss();
                    adapters.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error"+error, Toast.LENGTH_SHORT).show();
            }
        });
        // search recycle view
        searchBox = view.findViewById(R.id.searchEdtxt);
        searchBox.clearFocus();
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Filterlist(newText);
                return true;
            }
        });


    }

    private void Filterlist(String Text) {
        List<AddLawyerDetailToRealtym> filterlist=new ArrayList<>();
        for (AddLawyerDetailToRealtym item: list){
            if(item.getAddresses().toLowerCase().contains(Text.toLowerCase(Locale.ROOT))){
                filterlist.add(item);
            }
        }
        if (filterlist.isEmpty())
        {
            Toast.makeText(getContext(), "not Found", Toast.LENGTH_SHORT).show();
        }else{
          adapters.setFilteredlist(filterlist);
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