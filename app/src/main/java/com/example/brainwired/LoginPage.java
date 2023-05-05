package com.example.brainwired;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText email, pass;
    MaterialButton loginbtn;
    TextView newUser, ForgotPass;
    private FirebaseAuth mAuth;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        newUser = findViewById(R.id.NewUserSignUp);
        loginbtn = findViewById(R.id.loginbtn);
        ForgotPass = findViewById(R.id.forgotpass);

        progressDialog = new Dialog(LoginPage.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Signing in...");


        mAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    login();
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, SignUp.class);
                startActivity(intent);
            }
        });


    }

    private boolean validateData() {
        if (email.getText().toString().isEmpty()) {
            email.setError("Enter your email");
            return false;
        }
        if (pass.getText().toString().isEmpty()) {
            pass.setError("Enter your Password");
            return false;
        }
        return true;
    }

    private void login() {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                    startActivity(intent);
                    finish();

                    // Sign in success, update UI with the signed-in user's information
//                          // Log.d(TAG, "signInWithEmail:success");
//                          // FirebaseUser user = mAuth.getCurrentUser();
//                          // updateUI(user);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    // If sign in fails, display a message to the user.
//                          // Log.w(TAG, "signInWithEmail:failure", task.getException());
//                          // Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                          //         Toast.LENGTH_SHORT).show();
//                          // updateUI(null);
                }
            }
        });
    }
}