package com.example.homescreenassignment.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homescreenassignment.main.MainActivity;
import com.example.homescreenassignment.R;
import com.example.homescreenassignment.util.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public EditText loginEmailId, logInpasswd;
    Button btnLogIn;
    TextView signup;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        setupActivity();
    }

    private void setupActivity() {

        loginEmailId = findViewById(R.id.loginEmail);
        logInpasswd = findViewById(R.id.loginpaswd);
        btnLogIn = findViewById(R.id.btnLogIn);
        signup = findViewById(R.id.signUp);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, getString(R.string.user_logged_in), Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(I);
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.log_in_request), Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    public void signUp(View signUp) {
        Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    public void logIn(View logIn) {
        String userEmail = loginEmailId.getText().toString();
        String userPaswd = logInpasswd.getText().toString();

        if (userEmail.isEmpty()) {
            loginEmailId.setError(getString(R.string.error_empty_email));
            loginEmailId.requestFocus();
        } else if (userPaswd.isEmpty()) {
            logInpasswd.setError(getString(R.string.error_empty_password));
            logInpasswd.requestFocus();
        } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
        } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
            login(userEmail, userPaswd);
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
        }
    }

    public void login(String email, String passwd) {
        FirebaseUtil.getFirebaseAuth().signInWithEmailAndPassword(email, passwd).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, getString(R.string.error_in_login), Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUtil.getFirebaseAuth().addAuthStateListener(authStateListener);
    }
}