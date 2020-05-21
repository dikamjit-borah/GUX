package com.hobarb.gux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    Button submit;

    EditText user_name, mobile;
    String USER_ID;
    String user_name_str, phone_str, email_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        USER_ID = firebaseAuth.getCurrentUser().getUid();
        final DocumentReference documentReference_users = firebaseFirestore.collection("USER_DETAILS").document(USER_ID);

        user_name = findViewById(R.id.editText_name_reg);
        mobile = findViewById(R.id.editText_mobile_reg);

        submit = findViewById(R.id.button_submit_reg);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> user_details = new HashMap<>();
                user_details.put("USER_ID_", USER_ID);
                user_details.put("USER_NAME_", user_name.getText().toString());
                user_details.put("MOBILE_", mobile.getText().toString());

                documentReference_users.set(user_details);
            }
        });










    }
}
