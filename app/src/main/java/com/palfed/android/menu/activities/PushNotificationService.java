package com.palfed.android.menu.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.palfed.android.menu.activities.MainActivity;
import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.WebBrowser;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Android QTS on 1/25/2016.
 */
public class PushNotificationService extends GcmListenerService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public static final String TAG = "PushNotificationService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        if (!data.isEmpty()) {
            int badgeCount = Integer.parseInt(data.getString("badge"));
            QTSRun.setNotifMsg(getApplicationContext(),true);
            boolean success = ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
            QTSRun.setBadge(getApplicationContext(), badgeCount);
            if (data.toString().contains("click_destination")) {
                QTSRun.setDestination(getApplicationContext(), data.getString("click_destination"));
                Log.e(TAG, "click_destination: " + data.getString("click_destination"));

                Intent intent = new Intent();
                intent.setAction(QTSConst.ACTION_BROADCAST);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("badge", badgeCount);
                intent.putExtra("click_destination", data.getString("click_destination"));
                sendBroadcast(intent);
                sendWebNotification(data.getString("message"));
            } else {

                QTSRun.setDestination(getApplicationContext(), "");
                Intent intent = new Intent();
                intent.setAction(QTSConst.ACTION_BROADCAST);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("badge", badgeCount);
                intent.putExtra("click_destination", "");
                sendBroadcast(intent);
                sendNotification(data.getString("message"));
            }
            Log.i(TAG, "Received: " + data.toString());
            //=========================

        }

    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_push)
                .setContentTitle("Palfed")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        QTSRun.Setweb(getApplicationContext(), true);
    }
    private void sendWebNotification(String message) {
        Intent intent = new Intent(this, WebNotification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_push)
                .setContentTitle("Palfed")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//        QTSRun.Setweb(getApplicationContext(), true);
    }
    private void sendNotification1(String msg) {
        Log.d(TAG, "Preparing to send notification...: " + msg);
        mNotificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
            Intent sendIntent =  new Intent(getApplicationContext(), WebBrowser.class);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        if (type.toString().trim().equalsIgnoreCase("1")) {
            Log.d(TAG, "Preparing to send notification222...: ");
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                    sendIntent ,  PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    getApplicationContext())
                    .setSmallIcon(R.drawable.ic_push)
                    .setContentTitle("PalFed")
                    .setStyle(
                            new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg);
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            mBuilder.setAutoCancel(true);
            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
//        }
    }

}
