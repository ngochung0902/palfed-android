package com.palfed.android.menu.activities;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Android QTS on 2/16/2016.
 */
public class TimeService extends Service {

    // constant
    public static final long NOTIFY_INTERVAL = 25* 60 *1000; // 25 min

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
//        new CountDownTimer(60*10*1000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
////                time.setText("seconds remaining: "
////                        + millisUntilFinished / 1000);
//                Log.e("count", "seconds remaining: "
//                        + millisUntilFinished / 1000);
//
//            }
//
//        public void onFinish() {
//            Intent intent = new Intent();
//            intent.setAction(QTSConst.ACTION_BROADCAST_WEB);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            sendBroadcast(intent);
//
//        }
//        }.start();
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), NOTIFY_INTERVAL, NOTIFY_INTERVAL);

    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // refresh web
//                    QTSRun.showToast(getApplicationContext(),"refresh web");
                    QTSRun.setIsService(getApplicationContext(), true);
                    Log.e("count", "run");
                    Intent intent = new Intent();
                    intent.setAction(QTSConst.ACTION_BROADCAST_WEB);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sendBroadcast(intent);
//                    stopService(intent);
                }
            });

        }
    }
}
