package com.example.firebaseprac;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button save, fetch;
    EditText fnm, lnm;
    String fnameKey = "FName", lnameKey = "LName", fname, lname;
    TextView fnm1, lnm1, fullName;
    DocumentReference db;
    //CollectionReference db1 = FirebaseFirestore.getInstance().collection("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = findViewById(R.id.save);
        fetch = findViewById(R.id.fetch);
        fnm = findViewById(R.id.fname);
        lnm = findViewById(R.id.lName);

        fnm1 = findViewById(R.id.fnm1);
        lnm1 = findViewById(R.id.lnm1);
        fullName = findViewById(R.id.fullName);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = fnm.getText().toString();
                lname = lnm.getText().toString();
                db = FirebaseFirestore.getInstance().collection("Users").document(fname.substring(0,3)+lname.substring(0,3));
                Map<String, Object> toSave = new HashMap<>();

                toSave.put(fnameKey, fname);
                toSave.put(lnameKey, lname);
                toSave.put("Full Name", fname+" "+lname);

                db.set(toSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Successful","User successfully Added");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Failed","Failed to add user");
                    }
                });
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance().collection("Users").document(fname.substring(0,3)+lname.substring(0,3));
                db.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            fname = documentSnapshot.getString(fnameKey);
                            lname = documentSnapshot.getString(lnameKey);
                            String fullNm = documentSnapshot.getString("Full Name");

                            fnm1.setText(fname);
                            lnm1.setText(lname);
                            fullName.setText(fullNm);
                        }
                    }
                });
            }
        });

    }
}
