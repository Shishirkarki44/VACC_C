package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {

    private EditText remail, rpassword, rfname, rdob, rphoneNo;
    private Button registerAC = null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore dbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        dbase= FirebaseFirestore.getInstance();
        remail = findViewById(R.id.enterEmail);
        rpassword = findViewById(R.id.registerpsw);
        rfname = findViewById(R.id.eTPersonName);
        rdob = findViewById(R.id.editTextDate);
        rphoneNo = findViewById(R.id.contact);
        registerAC = findViewById(R.id.registerAccount);

        registerAC.setOnClickListener(new View.OnClickListener() {

                                          @Override
                 public void onClick(View view) {
                        String email1 = remail.getText().toString();
                        String pswd1 = rpassword.getText().toString();
                        String fullname1 = rfname.getText().toString();
                        String dateOfBirth1 = rdob.getText().toString();
                        String phoneNum1 = rphoneNo.getText().toString();

                        if (!email1.isEmpty() && !pswd1.isEmpty() && !fullname1.isEmpty() && !dateOfBirth1.isEmpty() && !phoneNum1.isEmpty())
                                              //registration form filled
                                              {
                                                  if (pswd1.length() <= 7) {
                                                      Toast.makeText(signupActivity.this, "Password length not valid, must be > 7", Toast.LENGTH_SHORT).show();
                                                  } else {
                                                      //password length accepted then
                                                      mFirebaseAuth.createUserWithEmailAndPassword(email1, pswd1).addOnCompleteListener(task -> {
                                                          if (task.isSuccessful()) {
                                                              //user has been registered

                                                              Map<String, Object> users = new HashMap<>();

                                                              users.put("Email Address", email1);
                                                              users.put("password", pswd1);
                                                              users.put("Full Name",fullname1);
                                                              users.put("Date of Birth", dateOfBirth1);
                                                              users.put("Phone Number",phoneNum1);

                                                              dbase.collection("Account")
                                                                      .add(users)
                                                                      .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                          @Override
                                                                          public void onSuccess(DocumentReference documentReference) {
                                                                              Toast.makeText(signupActivity.this, "database added with id: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                                                                          }
                                                                      })
                                                                      .addOnFailureListener(new OnFailureListener() {
                                                                          @Override
                                                                          public void onFailure(@NonNull Exception e) {
                                                                              Log.w("error in adding document", e);
                                                                          }
                                                                      });
















                                                              Toast.makeText(signupActivity.this, "Registration Successful"+"\n"+"Login to Start", Toast.LENGTH_SHORT).show();
                                                              startActivity(new Intent(signupActivity.this, MainActivity.class));

                                                          } else {
                                                              //registration failed
                                                              Toast.makeText(signupActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                                          }
                                                      });
                                                  } } else {
                                            //form have empty data
                                                    Toast.makeText(signupActivity.this, "please fill up the empty information", Toast.LENGTH_SHORT).show();
                                                    }
                        }//onclick
        }//registerAC-button

        );
}
}
