package com.hobarb.gux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_details extends AppCompatActivity {

    TextView name;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    String USER_ID;
    String name_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.textView_name_ud);


        Intent intent = getIntent();
        USER_ID = intent.getStringExtra("user_id");

        DocumentReference documentReference = firebaseFirestore.collection("USER_DETAILS").document(USER_ID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            /*    try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                name_str = documentSnapshot.getString("USER_NAME_");
                name.setText(name_str);

            }
        });
    }
}
