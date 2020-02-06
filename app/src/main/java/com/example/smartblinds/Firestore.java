package com.example.smartblinds;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class Firestore extends Activity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email = user.getEmail();

    public void add_data(){
        //Create a new user with email
        Map<String, Object> user = new HashMap<>();
        user.put("Email", email);
        user.put("Temperature", "0");
        user.put("Time", "0");

        db.collection("users").document(email)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Document SnapShot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void update_AppView(final EditText temp_edit_text, final EditText time_edit_text){
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Log.d(TAG, "DocumentSnapshot data: "+document.getData());
                        user_data data = document.toObject(user_data.class);
                        temp_edit_text.setText(data.Temperature);
                        time_edit_text.setText(data.Time);
                    }
                    else{
                        Log.d(TAG, "No such document");
                        add_data();
                    }
                }
                else{
                    Log.d(TAG, "get failed with", task.getException());
                }
            }
        });
    }

    public void update_database(EditText temp_edit_text, EditText time_edit_text, final Context UserActivity){
        DocumentReference emailRef = db.collection("users").document(email);

        emailRef
                .update("Temperature", temp_edit_text.getText().toString(), "Time", time_edit_text.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(UserActivity.getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        Toast.makeText(UserActivity.getApplicationContext(), "Save Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
