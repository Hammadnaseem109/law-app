package com.livewall.lawwalletfinalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.livewall.lawwalletfinalyearproject.R;

public class DemoActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    ImageView imagepick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        imagepick=findViewById(R.id.pickimageid);
        imagepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }
        });
    }
    private void PickImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_LOAD_IMG) {
            final Uri imageUri = data.getData();
//            if (imageUri!=null){
                Toast.makeText(this, "Imageuir"+imageUri, Toast.LENGTH_SHORT).show();
               imagepick.setImageURI(imageUri);
//            }else{
//                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}