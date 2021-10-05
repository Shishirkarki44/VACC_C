package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URL;

public class MainMenu extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private TextView email;
    private RelativeLayout covidCheck;
    private RelativeLayout myVaccDetails;
    private RelativeLayout vaccineInfo;
    private RelativeLayout covidNow;
    private RelativeLayout healthSupport;
    private RelativeLayout covidAvoid;


    private URL checkLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        email = findViewById(R.id.session_user);
        covidCheck = findViewById(R.id.firstMenu);
        myVaccDetails = findViewById(R.id.secondMenu);
        vaccineInfo = findViewById(R.id.thirdMenu);
        healthSupport = findViewById(R.id.fourthMenu);
        covidNow = findViewById(R.id.fifthMenu);
        covidAvoid = findViewById(R.id.sixthMenu);


        email.getText().toString();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    //log out of the system to homepage
        public void returnTo(View view)
        {
            mFirebaseAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));

        }
    //check ti see if user is logged in
    public void onStart() {
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            //user is logged in
            email.setText(mFirebaseUser.getEmail());

        } else
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void checkForSymptom(View view) {
         startActivity(new Intent(this, firstMenuActivity.class));
    }

    public void myvacc(View view) {
        startActivity(new Intent(this, secondMenuActivity.class));

    }

    public void vaccinInfo(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.health.gov.au/initiatives-and-programs/covid-19-vaccines/approved-vaccines")));


    }

    public void healthsupp(View view) {
        startActivity(new Intent(this, emergencyActivity.class));

    }

    public void covidNow(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://covid19.who.int/")));
    }

    public void health(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public?adgroupsurvey={adgroupsurvey}&gclid=CjwKCAjwzOqKBhAWEiwArQGwaJD2VyWXV33aSQ4bceolbGnay7TPkpy1WWIVQ_U2n5nmoa8nQH06EhoClRYQAvD_BwE")));

    }
}
