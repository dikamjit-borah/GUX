package com.hobarb.gux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class User_details extends AppCompatActivity {

    TextView name, gotra, city, occupation;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    ImageView imageView;

    String USER_ID;
    String name_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        imageView = findViewById(R.id.imageView_ud);
        name = findViewById(R.id.textView_name_ud);
        gotra = findViewById(R.id.textView_gotra_ud);
        occupation = findViewById(R.id.TextView_occupation_ud);
        city = findViewById(R.id.textView_city_ud);



        Intent intent = getIntent();
        USER_ID = intent.getStringExtra("user_id");
        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(USER_ID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });

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
                occupation.setText(documentSnapshot.getString("OCCUPATION_"));
                gotra.setText(documentSnapshot.getString("GOTRA_"));
                city.setText(documentSnapshot.getString("CITY_"));

            }
        });
    }
}
