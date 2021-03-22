package com.example.autologoutapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedIntentReceiver extends BroadcastReceiver
{
    private SessionManager session;

    @Override
    public void onReceive(final Context context, Intent intent)
    {

        if("LogOutAction".equals(intent.getAction())){

            Log.e("LogOutAuto", intent.getAction());
            Toast.makeText(context, "Logout Action", Toast.LENGTH_SHORT).show();
            //Do your action
            Toast.makeText(context,"time is 09.00pm", Toast.LENGTH_SHORT).show();
            session = new SessionManager(context);
            session.logoutUser();
        }
    }
}
