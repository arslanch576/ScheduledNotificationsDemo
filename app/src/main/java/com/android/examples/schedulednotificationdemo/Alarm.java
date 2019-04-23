package com.android.examples.schedulednotificationdemo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myWakeClock:wake");
        wl.acquire();

        // Put here YOUR code.
        //Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show();
        showNotification(context,"Talib e ilm","Its time for your class",
                "Its time for your class, The class will start in 15 minutes. hurryup and get ready");

        wl.release();
    }

    public void setAlarm(Context context, Calendar alarmDateTime)
    {
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        //i.putExtra("bundle",reminder.toBundle());
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, alarmDateTime.getTimeInMillis(),15000, pi); // Millisec * Second * Minute
        am.set(AlarmManager.RTC_WAKEUP, alarmDateTime.getTimeInMillis(), pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private void showNotification(Context context, String title, String smallText, String largeText){
        Log.i("sertest", "onNewPostArrive: post recieved in service");

        createNotificationChannel(context);

        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("post",NoticeBoardPost.convertToBundle(noticeBoardPost));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)System.currentTimeMillis()/10000, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"MyChannel1")
                .setSmallIcon(R.drawable.temp)
                .setContentTitle(title)
                .setContentText(smallText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(largeText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.markread,"Mark as read",pendingIntent)
                .addAction(R.drawable.open,"Open",pendingIntent)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                ;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int)System.currentTimeMillis()/10000, mBuilder.build());
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyChannel";
            String description = "MyChannel is a test chenel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MyChannel1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}