package com.sakibrafi.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openUserHomeActivity();
                }
                catch (Exception e){
                    Toast.makeText(SignUpActivity.this,"Please Enter Valid Information",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openUserHomeActivity()
    {
        Intent intent = new Intent(this, UserHomeActivity.class);
        startActivity(intent);
    }
}