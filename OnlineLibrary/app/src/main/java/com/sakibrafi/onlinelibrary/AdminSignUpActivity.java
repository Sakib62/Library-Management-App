package com.sakibrafi.onlinelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class AdminSignUpActivity extends AppCompatActivity {

    private EditText userName, signUpEmail, signUpPassword, confirmPassword;
    private FirebaseAuth mAuth;
    private Button signUp;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uMail = user.getEmail();
            if(user.isEmailVerified()) {
                if(uMail.endsWith("student.sust.edu")) {
                    startActivity(new Intent(AdminSignUpActivity.this, UserHomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(AdminSignUpActivity.this, AdminHomeActivity.class));
                    finish();
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        signUp = findViewById(R.id.adSignUp);
        userName = findViewById(R.id.adUserName);
        signUpEmail = findViewById(R.id.adEmail);
        signUpPassword = findViewById(R.id.adPassword);
        confirmPassword = findViewById(R.id.adConfirmPassword);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminRegister();
            }
        });

    }

    private void adminRegister() {

        String username = userName.getText().toString().trim();
        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String confirmPW = confirmPassword.getText().toString().trim();

        //Clears previous error
        userName.setError(null);
        signUpEmail.setError(null);
        signUpPassword.setError(null);
        confirmPassword.setError(null);

        //Validation part
        if(username.isEmpty()) {
            userName.setError("Username is required!");
            userName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            signUpEmail.setError("Email is required!");
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpEmail.setError("Please enter a valid email address!");
            signUpEmail.requestFocus();
            return;
        }

        if(!email.endsWith("@gmail.com")) {
            signUpEmail.setError("Student mail must ends with \"@gmail.com\"");
            signUpEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            signUpPassword.setError("Password is required!");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length() < 8) {
            signUpPassword.setError("Password length must be at least 8 characters!");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length() > 15) {
            signUpPassword.setError("Password length must not exceed 15 characters!");
            signUpPassword.requestFocus();
            return;
        }

        if(confirmPW.isEmpty()) {
            confirmPassword.setError("Re-enter password!");
            confirmPassword.requestFocus();
            return;
        }

        if(!password.equals(confirmPW)) {
            confirmPassword.setError("Passwords do not match!");
            confirmPassword.requestFocus();
            return;
        }

        //Creating New Account
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(AdminSignUpActivity.this, "An email has been sent to your given address. Please click the link in the mail to continue.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AdminSignUpActivity.this, SignInActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(AdminSignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified()) {
                            Toast.makeText(AdminSignUpActivity.this, "User email Exists", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AdminSignUpActivity.this, "Verification mail has already been sent. Click on the link in the mail to continue.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(AdminSignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Exit app
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminSignUpActivity.this);
        alertDialog.setTitle("Cancel");
        alertDialog.setMessage("Do you want to cancel Sign Up?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alertDialog.show();
        // exit app
    }
}