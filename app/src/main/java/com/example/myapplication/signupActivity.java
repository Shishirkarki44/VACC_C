package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupActivity extends AppCompatActivity {

    private EditText remail, rpassword, rfname, rdob, rphoneNo;
    private Button registerAC;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    remail = findViewById(R.id.enterEmail);
    rpassword = findViewById(R.id.registerpsw);
    rfname = findViewById(R.id.eTPersonName);
    rdob = findViewById(R.id.editTextDate);
    rphoneNo = findViewById(R.id.contact);

    registerAC= findViewById(R.id.register);

    String email = remail.getText().toString();
    String pswd = rpassword.getText().toString();
    String fullname = rfname.getText().toString();
    String dateOfBirth = rdob.getText().toString();
    String phoneNum = rphoneNo.getText().toString();

    registerAC.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = remail.getText().toString();
            String pswd = rpassword.getText().toString();
            String fullname = rfname.getText().toString();
            String dateOfBirth = rdob.getText().toString();
            String phoneNum = rphoneNo.getText().toString();

            if(!email.isEmpty() && !pswd.isEmpty() && !fullname.isEmpty() && !dateOfBirth.isEmpty() && !phoneNum.isEmpty())
            //registration form filled
            {
                if (pswd.length()<= 7)
                {
                    Toast.makeText(signupActivity.this,"Password length not valid, must be > 7", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //password length accepted then
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //user has been registered
                                startActivity(new Intent(signupActivity.this, MainActivity.class));
                                finish();
                            }
                            else
                            {
                                //registration failed
                                Toast.makeText(signupActivity.this,"Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(signupActivity.this,"Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Toast.makeText(signupActivity.this,"Registering Cancelled, try again", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
            else
            {
                //form have empty data
                Toast.makeText(signupActivity.this,"please fill up the empty information", Toast.LENGTH_SHORT).show();

            }
        }
    });

}
}