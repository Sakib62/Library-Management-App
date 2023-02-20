package com.sakibrafi.onlinelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText signInEmail, signInPassword;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            finish();
            openUserHomeActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button logIn = findViewById(R.id.logIn);
        signInEmail = findViewById(R.id.email);
        signInPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignIn();
            }
        });
    }

    private void userSignIn() {

        String email = signInEmail.getText().toString().trim();
        String password = signInPassword.getText().toString().trim();

        if(email.isEmpty()) {
            signInEmail.setError("Enter an email address");
            signInEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInEmail.setError("Enter a valid email address");
            signInEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            signInPassword.setError("Enter a password");
            signInPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            signInPassword.setError("Password length should be at least 6");
            signInPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    openUserHomeActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login Failed. Try Again.", Toast.LENGTH_SHORT).show();
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