package com.sakibrafi.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button logIn = (Button) findViewById(R.id.logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openUserHomeActivity();
                }
                catch (Exception e){
                    Toast.makeText(SignInActivity.this,"Please Enter Valid Information",Toast.LENGTH_SHORT).show();
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