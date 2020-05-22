package com.hobarb.gux;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    ListView listView;
    Button logout, fetch;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    ArrayList<String> records = new ArrayList<>();
    ArrayList<String>  user_ids = new ArrayList<>();
    String single_ID, single_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        listView = findViewById(R.id.listView_dash);
        logout = findViewById(R.id.button_logout_dash);
        fetch = findViewById(R.id.button_fetch_dash);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        /*ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item_1, mobileArray);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);*/

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    firestore.collection("USER_DETAILS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                records.clear();
                                int i = 0;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    i++;
                                    single_ID = (String) document.getId();
                                    single_name = (String) document.get("USER_NAME_");

                                    user_ids.add(single_ID);
                                    records.add(i + ") " + single_ID + " " + single_name);
                                }


                            }
                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_item_1, records);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String u_id = user_ids.get(position);
                        Intent intent = new Intent(getApplicationContext(), User_details.class);
                        intent.putExtra("user_id", u_id);
                        startActivity(intent);

                    }
                });
            }
        });
    }
}
