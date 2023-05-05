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

public class SignUp extends AppCompatActivity {

    private String emailString, passString, cnfrmpassString, usernameString;
    EditText username;
    EditText email;
    EditText pass;
    EditText confirmPass;
    private FirebaseAuth mAuth;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.repassword);
        progressDialog = new Dialog(SignUp.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Registering User...");

        MaterialButton regbtn = (MaterialButton) findViewById(R.id.signupbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username1 = username.getText().toString();
//                Toast.makeText(SignUp.this,"Username is"+username1,Toast.LENGTH_SHORT).show();

                if (validate()) {
                    signUpNewUser();
                }
            }
        });

    }

    private Boolean validate() {
        usernameString = username.getText().toString().trim();
        emailString = email.getText().toString().trim();
        passString = pass.getText().toString().trim();
        cnfrmpassString = confirmPass.getText().toString().trim();

        if (usernameString.isEmpty()) {
            username.setError("Enter your name");
            return false;
        }
        if (emailString.isEmpty()) {
            email.setError("Enter your email");
            return false;
        }
        if (passString.isEmpty()) {
            pass.setError("Enter your Password");
            return false;
        }
        if (cnfrmpassString.isEmpty()) {
            confirmPass.setError("Confirm your Password");
            return false;
        }
        if (passString.compareTo(cnfrmpassString) != 0) {
            Toast.makeText(SignUp.this, "Password should be same", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signUpNewUser() {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailString, passString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(SignUp.this, HomePage.class);
                    startActivity(intent);
                    SignUp.this.finish();

                    // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                }
            }
        });
    }
}