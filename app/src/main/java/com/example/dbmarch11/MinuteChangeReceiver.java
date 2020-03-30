package com.example.dbmarch11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
//CLASS         : MinuteChangeReceiver
//PURPOSE       : Reminds the user that the world is coming to an end once every minute
public class MinuteChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "One minute closer to the end...", Toast.LENGTH_SHORT).show();
    }
}
