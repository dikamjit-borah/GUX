package com.hobarb.gux;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {


    private static String VERIFICATION_ID = "";
    Button login, get_otp;
    EditText phone;
    EditText otp;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        phone = findViewById(R.id.editText_phone_login);
        otp = findViewById(R.id.editText_otp_login);
        login = findViewById(R.id.button_login_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getText().toString().isEmpty())
                {
                    otp.setError("Enter a valid OTP");
                    return;
                }
                else
                {
                    String user_input_otp = otp.getText().toString();
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(VERIFICATION_ID, user_input_otp);
                    verifyOTP(phoneAuthCredential);
                }

            }
        });

        get_otp= findViewById(R.id.button_getOTP_login);
        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phone.getText().toString().isEmpty() || phone.getText().toString().length()!=10)
                {
                    phone.setError("Enter a valid phone number");
                    return;
                }
                else
                    {
                    String phone_str = "+91" + phone.getText().toString();
                    requestOTP(phone_str);
                    //firebaseAuth.signInWithCredential(Pho)
                }
            }
        });
    }
    private void verifyOTP(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {


                    Toast.makeText(getApplicationContext(), "Authenticated", Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference = firestore.collection("USER_DETAILS").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists())
                            {
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
                            }
                            else {
                                startActivity(new Intent(getApplicationContext(), Registration.class));
                                finish();
                            }
                        }
                    });

                }
                else
                    Toast.makeText(getApplicationContext(), "Cannot be authenticated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestOTP(final String phone_number) {
        PhoneAuthProvider phoneAuthProvider = PhoneAuthProvider.getInstance();
        phoneAuthProvider.verifyPhoneNumber(phone_number, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                VERIFICATION_ID = s;

                Toast.makeText(getApplicationContext(), "OTP has been sent", Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.INVISIBLE);
                otp.setVisibility(View.VISIBLE);
                get_otp.setEnabled(false);
                login.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(getApplicationContext(), "Timeout occurred. Try again after sometime", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyOTP(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
