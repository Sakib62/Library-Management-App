package com.sakibrafi.onlinelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText userName, signUpEmail, registrationNo, signUpPassword, confirmPassword;
    private FirebaseAuth mAuth;
    private Button signUp;
    DatabaseReference databaseReference;
    //Check if the user is already Signed In
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null) {
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            String uMail = user.getEmail();
//            if(user.isEmailVerified()) {
//                if(uMail.endsWith("student.sust.edu")) {
//                    startActivity(new Intent(SignUpActivity.this, UserHomeActivity.class));
//                    finish();
//                }
//                else {
//                    startActivity(new Intent(SignUpActivity.this, AdminHomeActivity.class));
//                    finish();
//                }
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.stSignUp);
        userName = findViewById(R.id.stUserName);
        registrationNo = findViewById(R.id.stRegistration);
        signUpEmail = findViewById(R.id.stEmail);
        signUpPassword = findViewById(R.id.stPassword);
        confirmPassword = findViewById(R.id.stConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
    }

    private void userRegister() {
        String regiNo = registrationNo.getText().toString().trim();
        String username = userName.getText().toString().trim();
        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String confirmPW = confirmPassword.getText().toString().trim();

        //Clears previous error
        registrationNo.setError(null);
        userName.setError(null);
        signUpEmail.setError(null);
        signUpPassword.setError(null);
        confirmPassword.setError(null);

        //Validation part
        if(regiNo.isEmpty()) {
            registrationNo.setError("Registration No. is required!");
            registrationNo.requestFocus();
            return;
        }
        if(regiNo.length() != 10) {
            registrationNo.setError("Registration No. should consist of 10 digits.");
            registrationNo.requestFocus();
            return;
        }
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
        if(!email.endsWith("@student.sust.edu")) {
            signUpEmail.setError("Student mail must ends with \"@student.sust.edu\"");
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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                    if(user.isEmailVerified()) {
                        Toast.makeText(SignUpActivity.this, "Email is already used!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Verification email has already been sent. Click the link in the email to continue.", Toast.LENGTH_LONG).show();
                    }
                }
                else if (task.isSuccessful()) {
                    String key = databaseReference.push().getKey();
                    UserModel student = new UserModel(regiNo, username, email, password);
                    databaseReference.child(key).setValue(student);

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "An email has been sent to your given address. Click the link in the email to continue.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {// If sign up fails, display a message to the user.
                    Toast.makeText(SignUpActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() { // Exit app
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);
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
    }
}