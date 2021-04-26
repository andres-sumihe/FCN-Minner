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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    String userID;
    EditText txtFullname, txtEmail, txtPassword, txtConfirmPassword;
    Button btnRegister, btnChangeToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        txtFullname = findViewById(R.id.txtFullnameRegister);
        txtEmail = findViewById(R.id.txtEmailAddressRegister);
        txtPassword = findViewById(R.id.txtPasswordRegister);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnChangeToLogin = findViewById(R.id.btnChangeToLogin);

        btnChangeToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = txtFullname.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(fullname)){
                    txtFullname.setError("Fullname is required.");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    txtEmail.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    txtPassword.setError("Password is required.");
                    return;
                }

                if(password.length() < 8){
                    txtPassword.setError("Password must be more than 8 characters");
                    return;
                }

                if(TextUtils.isEmpty(confirmPassword)){
                    txtConfirmPassword.setError("Confirm Password is required.");
                    return;
                }

                if(!password.equals(confirmPassword)){
                    txtConfirmPassword.setError("Password does not match");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fullName", fullname);
                            user.put("email", email);
                            documentReference.set(user);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Email is registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}