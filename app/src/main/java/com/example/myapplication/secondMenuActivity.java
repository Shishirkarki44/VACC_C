package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class secondMenuActivity extends AppCompatActivity {
    private static final String TAG = "";
    private EditText vaccine;
    private EditText doBirth;
    private EditText firstDose;
    private EditText secondDose;
    private EditText stateOfVaccine;
    private FirebaseFirestore dbase;
    EditText editText;
    private FirebaseAuth mFirebaseAuth;
    private String email;
    DatabaseReference databaseReference;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);

        vaccine = findViewById(R.id.nameOfVaccine);
        doBirth = findViewById(R.id.doBirth);
        firstDose = findViewById(R.id.firstDose);
        secondDose = findViewById(R.id.secondDose);
        stateOfVaccine = findViewById(R.id.stateOfVaccine);

        dbase = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("vaccination certificates");
        mFirebaseAuth = FirebaseAuth.getInstance();
    }


    public void onStart(View view)
    {
        dbase.collection("vaccination").document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "Data has been saved", Toast.LENGTH_SHORT).show();

                                vaccine.setText(documentSnapshot.getString("name of vaccine"));
                                doBirth.setText(documentSnapshot.getString("date of Birth"));
                                firstDose.setText(documentSnapshot.getString("firstDose Date"));
                                secondDose.setText(documentSnapshot.getString("secondDose Date"));
                                stateOfVaccine.setText(documentSnapshot.getString("state of Vaccination"));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "no data available to show", Toast.LENGTH_SHORT).show();
                    }
                }
    }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed in data Retrieval", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void goSave(View view)
    {
        String vaccin = vaccine.getText().toString();
        String doBirt =doBirth.getText().toString();
        String firstDos = firstDose.getText().toString();
        String secondDos =secondDose.getText().toString();
        String stateOfVaccin = stateOfVaccine.getText().toString();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        assert mFirebaseUser != null;
        email = mFirebaseUser.getEmail();
        Map<String, Object> vaccination = new HashMap<>();

        vaccination.put("name of vaccine",vaccin);
        vaccination.put("date of Birth",doBirt);
        vaccination.put("firstDose Date",firstDos);
        vaccination.put("secondDose Date",secondDos);
        vaccination.put("state of Vaccination",stateOfVaccin);

        dbase.collection("vaccination").document(email)
                .set(vaccination)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(secondMenuActivity.this, " Database Updated! ", Toast.LENGTH_SHORT).show();

                }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error in adding document", e);
                    }
                });


    }




    public void goBack(View view) {
        startActivity(new Intent(this, MainMenu.class));
    }

    public void upload(View view) {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"SELECT PDF FILE"), 12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data.getData() != null)
        {
            uploadPDFFileFirebase(data.getData());
        }
    }

    private void uploadPDFFileFirebase(Uri data) {
        final ProgressDialog pDialog= new ProgressDialog(this);
         pDialog.setTitle("loading files...");
         pDialog.show();

         StorageReference reference = storageRef.child("upload" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task <Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete()) {
                            Uri uri = uriTask.getResult();

                            addPDF addPDF = new addPDF(editText.getText().toString(), uri.toString());
                            databaseReference.child(databaseReference.push().getKey()).setValue(addPDF);

                            Toast.makeText(secondMenuActivity.this, "file uploaded", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }

                    }
                }). addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
        pDialog.setMessage("File Uploaded...");
            }
        });

    }

    public void download(View view)
    {
        Toast.makeText(secondMenuActivity.this, "Upload Your File First", Toast.LENGTH_SHORT).show();

    }
}