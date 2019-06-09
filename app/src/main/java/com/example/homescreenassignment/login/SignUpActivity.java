package com.example.homescreenassignment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homescreenassignment.main.MainActivity;
import com.example.homescreenassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    public EditText emailId, passwd;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up);
        setUpActivity();
    }

    private void setUpActivity() {
        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        passwd = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    public void signUp(View signUp) {

        String emailID = emailId.getText().toString();
        String paswd = passwd.getText().toString();
        if (emailID.isEmpty()) {
            emailId.setError(getString(R.string.error_empty_email));
            emailId.requestFocus();
        } else if (paswd.isEmpty()) {
            passwd.setError(getString(R.string.error_empty_password));
            passwd.requestFocus();
        } else if (emailID.isEmpty() && paswd.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
        } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
            createUser(emailID, paswd);
        } else {
            Toast.makeText(SignUpActivity.this, getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
        }

    }

    public void signIn(View signIn) {
        Intent I = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(I);
    }

    private void createUser(String emailID, String paswd) {
        firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this.getApplicationContext(),
                            getString(R.string.sign_up_fail) + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }
            }
        });
    }

}
