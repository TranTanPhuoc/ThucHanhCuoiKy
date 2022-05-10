package com.example.a12_19531531_trantanphuoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterMain extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtEmail,txtPass,txtPassAgain;
    private Button btnRegister,btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        txtEmail = findViewById(R.id.txtEmailRegister);
        txtPass = findViewById(R.id.txtPasswordRegister);
        txtPassAgain = findViewById(R.id.txtPasswordAgainRegister);
        btnLogin = findViewById(R.id.btnSignInRegister);
        btnRegister = findViewById(R.id.btnRegisterMain);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                String passwordAgain = txtPassAgain.getText().toString().trim();
                if(password.equalsIgnoreCase(passwordAgain)){
                    auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterMain.this,MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    });
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMain.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}