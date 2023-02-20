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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpEmail, signUpPassword;
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
        setContentView(R.layout.activity_sign_up);

        Button register = findViewById(R.id.register);
        signUpEmail = findViewById(R.id.email);
        signUpPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
    }

    private void userRegister() {

        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();

        if(email.isEmpty()) {
            signUpEmail.setError("Enter an email address");
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpEmail.setError("Enter a valid email address");
            signUpEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            signUpPassword.setError("Enter a password");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            signUpPassword.setError("Passowrd's length should be at least 6");
            signUpPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            Toast.makeText(SignUpActivity.this, "Account Created.",
                                    Toast.LENGTH_SHORT).show();
                            openUserHomeActivity();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    public void openUserHomeActivity() {
        Intent intent = new Intent(this, UserHomeActivity.class);
        startActivity(intent);
    }
}