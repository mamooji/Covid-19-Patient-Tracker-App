package com.example.dbmarch11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PatientAddedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Patient added to firebase database", Toast.LENGTH_SHORT).show();
    }
}
