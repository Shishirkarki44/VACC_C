package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private String mail= null;
    protected String pass= null;
    private FirebaseAuth mAuth;

    Button signinBtn= null;
    Button registerBtn = null;
    TextView forgotView = null;
    View view = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signinBtn = (Button) findViewById(R.id.buttonSign);
        registerBtn = (Button) findViewById(R.id.register);
        forgotView = (TextView) findViewById(R.id.ForgotPass);

        signinBtn.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick (View view)
            {
                Intent myIntent = new Intent(MainActivity.this, MainMenu.class);
                myIntent.putExtra("Login Successful", "");
                MainActivity.this.startActivity(myIntent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view)
            {
                Intent myIntent = new Intent(MainActivity.this, signupActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    mAuth = FirebaseAuth.getInstance();
    /**public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    };**/

}

    public void forgotPswd(View view) {
    }
}