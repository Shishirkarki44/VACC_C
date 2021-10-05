package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class firstMenuActivity extends AppCompatActivity {

    private TextView sessionmail;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_menu);

        mFirebaseAuth = FirebaseAuth.getInstance();



    }

    public void onStart()
    {
        super.onStart();
        sessionmail = findViewById(R.id.session_user);
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        sessionmail.setText(mFirebaseUser.getEmail());

    }

    public void goBack(View view) {
        startActivity(new Intent(this, MainMenu.class));
    }
}
