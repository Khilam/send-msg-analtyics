package com.example.mytestanalytics;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private EditText number, message;
    private Button send;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);





        setContentView(R.layout.activity_main);
        number = findViewById(R.id.number);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();

                bundle.putString("send","Send Button");
                mFirebaseAnalytics.logEvent("send_button", bundle);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendSMS();

                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);

                    }

                }
            }
        });
    }


    private void sendSMS() {
        String phoneNo = number.getText().toString().trim();
        String SMS = message.getText().toString().trim();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, SMS, null, null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send the message", Toast.LENGTH_SHORT).show();
        }
    }
}