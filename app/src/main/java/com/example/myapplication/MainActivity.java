package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText mail= null;
    protected EditText pass= null;
    Button signinBtn= null;
    Button registerBtn = null;
    TextView forgotView = null;
    View view = null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore dbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        dbase = FirebaseFirestore.getInstance();

        mail= findViewById(R.id.editTextEmail);
        pass= findViewById(R.id.editTextTextPassword);
        signinBtn = findViewById(R.id.buttonSign);
        registerBtn = findViewById(R.id.register);
        forgotView = findViewById(R.id.ForgotPass);


        signinBtn.setOnClickListener(view -> {
            String email = mail.getText().toString();
            String password = pass.getText().toString();

            if (!email.isEmpty() && !password.isEmpty())
            {
                mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            //sigIn complete
                            startActivity(new Intent(MainActivity.this, MainMenu.class));
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error :" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(MainActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(MainActivity.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
            }

        });

        registerBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, signupActivity.class));
        });
    }





    public void forgotPswd(View view) {
    }
}