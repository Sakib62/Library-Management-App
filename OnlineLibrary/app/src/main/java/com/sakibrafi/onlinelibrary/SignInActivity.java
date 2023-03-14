package com.sakibrafi.onlinelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText signInEmail, signInPassword;
    private FirebaseAuth mAuth;
    private Button signIn;
    private ProgressBar progressBar;
    private Spinner spinner;
    String[] signUpOptions;

    //Check if the user is already signed in
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user.isEmailVerified()) {
                startActivity(new Intent(SignInActivity.this, UserHomeActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signUpOptions = getResources().getStringArray(R.array.signUpOption);
        signUpOptions[0] = "  " + signUpOptions[0] + "  ";
        signUpOptions[1] = " " + signUpOptions[1];
        signUpOptions[2] = " " + signUpOptions[2] + "  ";

        spinner = findViewById(R.id.spinnerlogin);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.sample_view, R.id.sampleViewTV, signUpOptions);
        spinner.setAdapter(adapter);

        signIn = findViewById(R.id.signIn);
        signInEmail = findViewById(R.id.email);
        signInPassword = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignIn();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String curItem = spinner.getSelectedItem().toString();
                if(curItem == signUpOptions[1]) {
                    startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                }
                else if(curItem == signUpOptions[2]) {
                    startActivity(new Intent(SignInActivity.this, AdminSignUpActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void userSignIn() {

        String email = signInEmail.getText().toString().trim();
        String password = signInPassword.getText().toString().trim();

        //Validation of email & password
        if(email.isEmpty()) {
            signInEmail.setError("Email is required!");
            signInEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInEmail.setError("Please enter a valid email address!");
            signInEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            signInPassword.setError("Password is required!");
            signInPassword.requestFocus();
            return;
        }

        if(password.length() < 8) {
            signInPassword.setError("Password length must be at least 8 characters!");
            signInPassword.requestFocus();
            return;
        }

        if(password.length() > 15) {
            signInPassword.setError("Password length must not exceed 15 characters!");
            signInPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //Signing In
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        //redirect to user profile
                        if(email.endsWith("@student.sust.edu")) {
                            startActivity(new Intent(SignInActivity.this, UserHomeActivity.class));
                        }
                        else {
                            startActivity(new Intent(SignInActivity.this, AdminHomeActivity.class));
                        }
                        finish();
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "Verify your Email Address to sign in!\nCheck mail for Verification Link.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(SignInActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Exit app
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignInActivity.this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit app?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
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