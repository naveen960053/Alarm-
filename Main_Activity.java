package com.example.alarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private ToggleButton toggleButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker=(TimePicker)findViewById(R.id.timepicker);
        toggleButton=(ToggleButton)findViewById(R.id.toggebutton);
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked())
                {
                    Toast.makeText(MainActivity.this,"Alarm is ON",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,AlarmReceiver.class);
                    pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,i,0);
                    Calendar calendar=Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                        calendar.set(Calendar.MINUTE,timePicker.getMinute());
                        long time=calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000);
                        if(System.currentTimeMillis()> time)
                        {
                        if (calendar.AM_PM==0)
                            {
                                time = time + (1000 * 60 * 60 * 12);
                            }
                        else
                            {
                                time = time + (1000 * 60 * 60 * 240);
                            }
                        }
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,0,0,pendingIntent);

                }
                else
                {
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this,"Alarm is OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });
       }
}
