package com.android.examples.schedulednotificationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Alarm alarm;
    Calendar selectedTime;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarm=new Alarm();
        selectedTime=Calendar.getInstance();
        editText=findViewById(R.id.editText);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long delayInMills=Long.parseLong(editText.getText().toString())*1000 ;
                Date date=new Date(System.currentTimeMillis()+delayInMills);
                selectedTime.setTime(date);
                alarm.setAlarm(getApplicationContext(), selectedTime);
            }
        });

    }
}
