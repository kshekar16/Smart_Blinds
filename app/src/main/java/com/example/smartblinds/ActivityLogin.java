package com.example.smartblinds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity {
    EditText login_email, login_password;
    Button login_btn, signup_btn;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        firebaseAuth = FirebaseAuth.getInstance();
        login_email = findViewById(R.id.editText6);
        login_password = findViewById(R.id.editText5);
        login_btn = findViewById(R.id.button4);
        signup_btn = findViewById(R.id.button3);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Toast.makeText(ActivityLogin.this, "User logged in", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(ActivityLogin.this, UserActivity.class);
                    startActivity(I);
                }
                else{
                    Toast.makeText(ActivityLogin.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
        signup_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent I = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(I);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String user_string = login_email.getText().toString();
                String pass_string = login_password.getText().toString();
                if (user_string.isEmpty()){
                    login_email.setError("Provide your Email first");
                    login_email.requestFocus();
                }
                else if (pass_string.isEmpty()){
                    login_password.setError("Enter password");
                    login_password.requestFocus();
                }
                else if (user_string.isEmpty() && pass_string.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(user_string.isEmpty() && pass_string.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(user_string,pass_string).addOnCompleteListener(ActivityLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(ActivityLogin.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(ActivityLogin.this, UserActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(ActivityLogin.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
