package com.example.smartblinds;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.smartblinds.Firestore;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import static android.content.ContentValues.TAG;

public class UserActivity extends AppCompatActivity {
    Button btn_logout;
    Button btn_save;
    EditText temp_edit_text, time_edit_text;


    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstaneState){
        super.onCreate(savedInstaneState);
        setContentView(R.layout.user_activity);
        btn_logout = findViewById(R.id.button5);
        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(UserActivity.this, ActivityLogin.class);
                startActivity(I);

            }
        });

        //Build database / Get existing database with email
        temp_edit_text = findViewById(R.id.editText7);
        time_edit_text = findViewById(R.id.editText8);
        Firestore f_store = new Firestore();
        f_store.update_AppView(temp_edit_text, time_edit_text);
    }

    @Override
    protected void onStart(){
        super.onStart();
        temp_edit_text = findViewById(R.id.editText7);
        time_edit_text = findViewById(R.id.editText8);
        btn_save = findViewById(R.id.button9);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firestore f_store = new Firestore();
                f_store.update_database(temp_edit_text, time_edit_text, UserActivity.this);
            }
        });
    }
}
