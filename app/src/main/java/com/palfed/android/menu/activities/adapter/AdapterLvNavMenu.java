package com.palfed.android.menu.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.activities.objects.LVNav;
import com.palfed.android.menu.activities.objects.NavMenuObject;

import java.util.ArrayList;

/**
 * Created by MyPC on 22/09/2017.
 */
public class AdapterLvNavMenu extends BaseAdapter {
    Context context;
    ArrayList<LVNav> arrayList;

    public AdapterLvNavMenu(Context context, ArrayList<LVNav> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.linelv,null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv_lvnavmenu);
        LVNav lvNav = arrayList.get(position);
        tv.setText(lvNav.getTitle());
        QTSRun.setFontTV(context, tv, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        return convertView;
    }
}
