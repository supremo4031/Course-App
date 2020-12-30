package com.arpan.alosproject.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arpan.alosproject.R;
import com.arpan.alosproject.ui.fragments.SignUpFragment;
import com.arpan.alosproject.util.Others;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity implements SignUpFragment.SignUpListener {

    private static final String TAG = "LogInActivity";

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private MaterialButton signInButton;
    private ProgressBar progressBar;
    private MaterialTextView forgotPassword;
    private MaterialTextView signUpText;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setUpUI();
        setUpFirebase();

        signInButton.setOnClickListener(v -> {
            if (!validateEmail(emailInput.getText().toString()) || !validatePassword(passwordInput.getText().toString()))
                return;
            progressBar.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
            signIn(emailInput.getText().toString(), passwordInput.getText().toString());
        });

        signUpText.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            signUpFragment.show(getSupportFragmentManager(), "SignUp");
        });
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);
                goToMainActivity();
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);
                Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void setUpFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
    }

    private void setUpUI() {

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.sign_in);
        signUpText = findViewById(R.id.sign_up);
        forgotPassword = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.progress_bar);
    }

    private boolean validateEmail(String email) {

        if(email.isEmpty()) {
            emailInput.setError("Field can't be empty");
            return false;
        } else if(!Others.EMAIL_PATTERN.matcher(email).matches()) {
            emailInput.setError("Please enter a valid email");
            return false;
        }
        emailInput.setError(null);
        return true;
    }

    private boolean validatePassword(String password) {

        if(password.isEmpty()) {
            passwordInput.setError("Field can't be empty");
            return false;
        } else if(!Others.PASSWORD_PATTERN.matcher(password).matches()) {
            passwordInput.setError("a-z or A-Z and min 6 char");
            return false;
        }
        passwordInput.setError(null);
        return true;
    }

    @Override
    public void signUp(String name, String email, String password) {

        ProgressDialog dialog = new ProgressDialog(LogInActivity.this);
        dialog.setMessage("Creating Account...");
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                Log.d(TAG, "createUserWithEmail:success");

                String Uid = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = mFireStore.collection("users").document(Uid);

                Map<String, Object> curUser = new HashMap<>();
                curUser.put("Name", name);
                curUser.put("Email", email);
                documentReference.set(curUser).addOnSuccessListener(v -> {
                    Log.d(TAG, "Name and Email registered");
                    dialog.dismiss();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "Failure : " + e.getMessage());
                    dialog.dismiss();
                });
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}