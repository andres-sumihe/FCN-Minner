package com.andflow.fcnminner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText txtEmailAddressLogin, txtPasswordLogin;
    Button btnLogin, btnChangeToRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmailAddressLogin = findViewById(R.id.txtEmailAddressLogin);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnChangeToRegister = findViewById(R.id.btnChangeToRegister);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnChangeToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = txtEmailAddressLogin.getText().toString().trim();
                String password = txtPasswordLogin.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    txtEmailAddressLogin.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                   txtPasswordLogin.setError("Password is required.");
                    return;
                }

                if(password.length() < 8){
                    txtPasswordLogin.setError("Password must be more than 8 characters");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())    {
                            Toast.makeText(Login.this, "Login is successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(Login.this, "Email or password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}