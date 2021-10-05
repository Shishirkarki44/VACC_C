package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class emergencyActivity extends AppCompatActivity {

    private static final int CALL_PHONE_PERMISSION_CODE = 1;
   String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);


    }
    private void makeCall(int number)
    {
        String num = Integer.toString(number);
        String dial= "tel:" + num;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(dial));
        startActivity(intent);
    }
    public void ambulance(View view) {
        if (ContextCompat.checkSelfPermission(emergencyActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            makeCall(000);

        } else {
            requestCallPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PHONE_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                int num = 000;
                makeCall(num);
            }
            else
            {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                requestCallPermissions();
            }
        }
    }

    private void requestCallPermissions()
    {
            ActivityCompat.requestPermissions(emergencyActivity.this, new String[] {Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_CODE);

    }

    public void hospital(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=hospitals+near+me&oq=hospitals+&aqs=chrome.2.69i57j0i273i457j0i131i273i433j0i273l2j0i20i263i512j0i512j0i20i263i512.4939j0j4&client=ms-android-samsung-ss&sourceid=chrome-mobile&ie=UTF-8")));

    }

    public void doctor(View view) {

        makeCall(1800022222);

    }

    public void test(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nsw.gov.au/covid-19/health-and-wellbeing/clinics")));

    }
}