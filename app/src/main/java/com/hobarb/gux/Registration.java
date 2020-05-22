package com.hobarb.gux;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private static final int PICK_MEDIA_REQUEST = 44;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    Button submit, select_img;

    EditText user_name, mobile, city, occupation, qualify, gotra, birth_place;
    String USER_ID;
    TextView file;
    String user_name_str, phone_str, email_str;
    Uri uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_MEDIA_REQUEST && resultCode == RESULT_OK && data !=null && data.getData()!=null )
        {
            uri = data.getData();
            file.setText(uri.getPath().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        user_name = findViewById(R.id.editText_name_reg);
        mobile = findViewById(R.id.editText_mobile_reg);
        city = findViewById(R.id.editText_city_reg);
        occupation = findViewById(R.id.editText_occupation_reg);
        qualify = findViewById(R.id.editText_qualification_reg);
        gotra = findViewById(R.id.editText_gotra_reg);
        birth_place = findViewById(R.id.editText_birthplace_reg);


        file = findViewById(R.id.textView_file_reg);

        select_img=findViewById(R.id.button_imgsel_reg);
        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_MEDIA_REQUEST);
            }
        });

        submit = findViewById(R.id.button_submit_reg);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_name.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || city.getText().toString().isEmpty() ||occupation.getText().toString().isEmpty()||qualify.getText().toString().isEmpty()||birth_place.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please provide complete details", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    upload_details();
                    Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    finish();

                }
            }
        });










    }

    private void upload_details() {

        USER_ID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference_users = firebaseFirestore.collection("USER_DETAILS").document(USER_ID);

        Map<String, Object> user_details = new HashMap<>();
        user_details.put("USER_ID_", USER_ID);
        user_details.put("USER_NAME_", user_name.getText().toString());
        user_details.put("MOBILE_", mobile.getText().toString());
        user_details.put("CITY_", city.getText().toString());
        user_details.put("OCCUPATION_", occupation.getText().toString());
        user_details.put("GOTRA_", gotra.getText().toString());
        user_details.put("BIRTHPLACE_", birth_place.getText().toString());
        user_details.put("QUALIFICATION_", qualify.getText().toString());

        documentReference_users.set(user_details);


    }
}
