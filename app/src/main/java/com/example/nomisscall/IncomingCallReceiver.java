package com.example.nomisscall;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;


public class IncomingCallReceiver extends BroadcastReceiver {

    Context context;
    int count=1;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        // Check if the received intent has the expected action string
        if (intent.getAction() != null && intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);


                // Retrieve stored numbers and their associated messages
                SharedPreferences sharedPreferences = context.getSharedPreferences("ContactMessages", Context.MODE_PRIVATE);
                Map<String, ?> allEntries = sharedPreferences.getAll();



                // Check if the incoming number matches any stored number
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    String storedNumber = entry.getKey();
                    String storedMessage = entry.getValue().toString();

                    // Compare incoming number with stored numbers
                    if (incomingNumber != null && incomingNumber.equals(storedNumber) && count==1) {
                        // I will have to send the message from here
                        Toast.makeText(context, "the message is " + storedMessage, Toast.LENGTH_LONG).show();



                        break; // Break the loop if a match is found
                    }
                }
                count++;
            }
        }
    }
}

