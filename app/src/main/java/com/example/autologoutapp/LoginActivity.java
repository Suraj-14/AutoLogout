package com.example.autologoutapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    MaterialEditText email, password;
    Button btn_login;

    String text="suraj14";
    private  SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 21);
        calendar1.set(Calendar.MINUTE, 34);
        calendar1.set(Calendar.SECOND, 0);
       // Toast.makeText(LoginActivity.this, "time    " + calendar.getTime()+"\n"+calendar1.getTime(), Toast.LENGTH_SHORT).show();
        if(calendar1.getTimeInMillis()==calendar.getTimeInMillis()) {
            Toast.makeText(LoginActivity.this, "time    " + calendar1.getTime(), Toast.LENGTH_SHORT).show();
        }
        sessionManager=new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString().toLowerCase();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else {
                    if(txt_email.equals(text.trim()) && txt_password.equals(text.trim())) {
                        Toast.makeText(LoginActivity.this, "Successfully Login......", Toast.LENGTH_SHORT).show();
                        callAutoLogout();
                        sessionManager.createLoginSession(txt_email.toLowerCase(), txt_password);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
            }

        });
    }
    private void callAutoLogout() {
        btn_login.setVisibility(View.INVISIBLE);
        Intent alaramIntent = new Intent(LoginActivity.this, BootCompletedIntentReceiver.class);
        alaramIntent.setAction("LogOutAction");
        Log.e("MethodCall","AutoLogOutCall");
        alaramIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alaramIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        Log.e("Logout", "Auto Logout set at..!" + calendar.getTime());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}







