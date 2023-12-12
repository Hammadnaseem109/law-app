package com.livewall.lawwalletfinalyearproject.LaywerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.livewall.lawwalletfinalyearproject.Adapters.CommentAdapter;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddLawyerDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.ModelClass.AddUserDetailToRealtym;
import com.livewall.lawwalletfinalyearproject.ModelClass.GetCommentandPostModelClass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.UserActivity.Commenthere;
import com.livewall.lawwalletfinalyearproject.UserActivity.UserDashboard;

import java.util.ArrayList;

public class LawyerCommenthere extends AppCompatActivity {
    EditText edcom;
    ImageView sendimage,profileimage;
    String commenthere;
    String username,curentuidID;
    String postkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_commenthere);
        Clicklistener();
        Intent intent = getIntent();
        postkey = intent.getStringExtra("postkey");
        getComment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.borron));
        }
        getCurentUserDetail();
    }
    private void Clicklistener(){
        edcom=findViewById(R.id.edhereID);
        sendimage=findViewById(R.id.btncomment);
        profileimage=findViewById(R.id.profileimage);
        sendimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commenthere= edcom.getText().toString();
                edcom.setText("");
                AddUSerDataToRealtime(commenthere);
            }
        });
    }
    private void AddUSerDataToRealtime(String com){

//        String username = intent.getStringExtra("name");
//        postuserID = intent.getStringExtra("ID");
        FirebaseAuth Auth;
        Auth=FirebaseAuth.getInstance();
        FirebaseUser user = Auth.getCurrentUser();
        curentuidID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(
                "Comment").child("Post").child(postkey).child(postkey);
        String randomkey=database.getReference().push().getKey();
        GetCommentandPostModelClass modelClass=new GetCommentandPostModelClass(curentuidID,username,commenthere);
        myRef.child(randomkey).setValue(modelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LawyerCommenthere.this, "Sucesfully Updated", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LawyerCommenthere.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getComment(){
//        Intent intent = getIntent();
//       String postuserrID = intent.getStringExtra("ID");
        RecyclerView recyclerView;
        recyclerView=findViewById(R.id.commentrecycelviewID);
        ArrayList<GetCommentandPostModelClass> list;
        list=new ArrayList<>();
        CommentAdapter adapters=new CommentAdapter(list,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapters);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference ref= firebaseDatabase.getReference("Comment").child("Post")
                ;
        ref.child(postkey).child(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                                    GetCommentandPostModelClass  obj=dataSnapshot.getValue(GetCommentandPostModelClass.class);
                        String userComm = dataSnapshot.child("userComment").getValue().toString();
                        String userName = dataSnapshot.child("username").getValue().toString();
                        String userID = dataSnapshot.child("username").getValue().toString();
                        GetCommentandPostModelClass obj=new GetCommentandPostModelClass(userID,userName,userComm);
                        list.add(obj);

                    }

                    adapters.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(LawyerCommenthere.this, "No Comments", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCurentUserDetail(){
        FirebaseAuth Auth;
        Auth=FirebaseAuth.getInstance();
        FirebaseUser user = Auth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        // Query the database for a specific path or child
        DatabaseReference pathRef = database.getReference("VisitProfile").child("lawyer").child(uid);
        // Read the data once
        pathRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    AddLawyerDetailToRealtym obj=dataSnapshot.getValue(AddLawyerDetailToRealtym.class);
                    Glide.with(getApplicationContext())
                            .load(obj.getImageuri())
                            .into(profileimage);
                    username=obj.getLawyername();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                System.err.println("Database read error: " + databaseError.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),LawyerDashboard.class);
        startActivity(intent);
    }
}