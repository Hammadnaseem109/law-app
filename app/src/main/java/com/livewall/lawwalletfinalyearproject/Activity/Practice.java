package com.livewall.lawwalletfinalyearproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.livewall.lawwalletfinalyearproject.R;

public class Practice extends AppCompatActivity {
    EditText editText;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        editText=findViewById(R.id.edviewID);
        btn=findViewById(R.id.btnsaveID);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText.getText().toString().trim();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key", name);
                editor.apply();
                String savedValue = sharedPreferences.getString("key", "");
                Toast.makeText(Practice.this, "SharedPreFerence value"+savedValue, Toast.LENGTH_SHORT).show();
            }
        });

    }
}