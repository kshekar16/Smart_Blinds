package com.example.smartblinds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button btnSignUp, AlreadyUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);
        btnSignUp = findViewById(R.id.button);
        AlreadyUser = findViewById(R.id.button2);
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email_str = email.getText().toString();
                String password_str = password.getText().toString();
                if (email_str.isEmpty()){
                    email.setError("Provide your Email First!");
                    email.requestFocus();
                }
                else if (password_str.isEmpty()){
                    password.setError("Set your password");
                    password.requestFocus();
                }
                else if (password_str.isEmpty() && email_str.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(email_str.isEmpty() && password_str.isEmpty())){
                    firebaseAuth.createUserWithEmailAndPassword(email_str, password_str).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this.getApplicationContext(), "SignUp unsuccessful:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlreadyUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent I = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(I);
            }
        });
    }
}


