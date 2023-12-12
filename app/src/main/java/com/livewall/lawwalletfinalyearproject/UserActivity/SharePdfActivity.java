package com.livewall.lawwalletfinalyearproject.UserActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.livewall.lawwalletfinalyearproject.ModelClass.SharePdfModelClass;
import com.livewall.lawwalletfinalyearproject.R;
import com.livewall.lawwalletfinalyearproject.databinding.ActivitySharePdfBinding;


public class SharePdfActivity extends AppCompatActivity {
    private ActivitySharePdfBinding binding;
    private static final String TAG="ADD_PDF_TAG";
    private static final int PDF_PICK_CODE =1000;
    private Uri pdfUri;
    String receivedData,lawyeruid;
    String Filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySharePdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.borron));
        }
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            }
        });
        binding.attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPickIntent();
            }
        });

        Upload();
    }
    private void Upload(){
        receivedData = getIntent().getStringExtra("name");
        lawyeruid = getIntent().getStringExtra("LawID");
        binding.titlet.setText(receivedData);
        if (lawyeruid!=null){
            uploadpdf();
        }else{

        }
    }
    private void uploadpdf(){
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfUri!=null){
                    uploadpdftostorage();
                }else{
                    Toast.makeText(SharePdfActivity.this, "please select pdf", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void pdfPickIntent() {
        Log.d(TAG,"pdfPickIntent:Starting pdf pick intent");
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF"),PDF_PICK_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode==PDF_PICK_CODE){
                Log.d(TAG,"On Activity Result : Pdf Picked");
                pdfUri=data.getData();
                Filename=getFileName(pdfUri);
                binding.filenameID.setText(Filename);
                binding.carviewshow.setVisibility(View.VISIBLE);
                Toast.makeText(this, ""+Filename, Toast.LENGTH_SHORT).show();
                Log.d(TAG,"On Activity Result : URI:"+pdfUri);
            }
        }else {
            Log.d(TAG,"On Activity Result: cancelled picking pdf");
            Toast.makeText(this, "cancelling pdf picking", Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor =getContentResolver().query(uri,
                    null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private void uploadpdftostorage() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        Log.d(TAG,"Uploading....");
        progressDialog.setTitle("Uploading Pdf....");
        progressDialog.show();
        long timestamp=System.currentTimeMillis();
        String filepathname="PDf/"+timestamp;
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathname);
        storageReference.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG,"On Success :PDF Uploaded Successfully");
                Log.d(TAG,"Getting Pdf url");
                progressDialog.dismiss();
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (! uriTask.isSuccessful());
                String uploadedPdfUrl=""+uriTask.getResult();
                uploadpdfInfoToDb(uploadedPdfUrl);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d(TAG,"On Failure Pdf Uploaded Failure"+e.getMessage());
                Toast.makeText(SharePdfActivity.this, "PDF uploaded failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void uploadpdfInfoToDb(String uploadedPdfUrl) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ProgressDialog progressDialog=new ProgressDialog(this);
        Log.d(TAG,"Uploadpdftostorage:uploading to firebase db...");
        progressDialog.setMessage("Uploading Pdf Info");
        String userId = user.getUid();
        SharePdfModelClass obj=new SharePdfModelClass(lawyeruid,userId,Filename,uploadedPdfUrl);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("lawyerPDf");
        reference.child(lawyeruid).push().setValue(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Log.d(TAG,"Successfully Uploaded");
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("userpdf");
                ref.child(userId).push().setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SharePdfActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d(TAG,"Failed To Upload Due to"+e.getMessage());
                Toast.makeText(SharePdfActivity.this, "Failed To Upload Due To"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
        finish();
    }
}