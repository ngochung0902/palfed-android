package com.palfed.android.menu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;

/**
 * Created by TuanQTS on 27/06/2017.
 */
public class NoInternetAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonetwork_activity);
        TextView lbNetwork = (TextView) findViewById(R.id.lbNoNetwork);
        ImageButton btRefresh = (ImageButton) findViewById(R.id.ibtRefresh);
        QTSRun.setFontTV(getApplicationContext(), lbNetwork, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QTSRun.isNetworkAvailable(getApplicationContext()))
                    finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return  false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
