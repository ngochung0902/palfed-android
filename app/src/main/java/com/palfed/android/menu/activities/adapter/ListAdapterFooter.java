package com.palfed.android.menu.activities.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.commonhelper.ImageLoaderAvar;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.activities.objects.FrRequestObj;

import java.util.List;

/**
 * Created by Administrator on 11/23/2016.
 */

public class ListAdapterFooter extends BaseAdapter {

    Context mContext;
    List<FrRequestObj> arrList = null;
    private LayoutInflater inflater;
    private ImageLoaderAvar imageLoader;
    private int w = 1;
    public ListAdapterFooter(Context mContext, List<FrRequestObj> arrList){
        this.mContext = mContext;
        this.arrList = arrList;
        inflater = LayoutInflater.from(mContext);
        this.w = QTSRun.GetWidthDevice(this.mContext);
        this.imageLoader = new ImageLoaderAvar(mContext);
    }
    @Override
    public int getCount() {
        return arrList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {

        TextView tvContent;
        TextView tvname;
        ImageView ivAvatar;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_friend, null);
            holder.tvname = (TextView)convertView.findViewById(R.id.item_Title);
            holder.tvContent = (TextView)convertView.findViewById(R.id.item_content);
            holder.ivAvatar = (ImageView)convertView.findViewById(R.id.viewAvatar);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        QTSRun.setLayoutView(holder.ivAvatar, w/5,w/5);
        holder.tvname.setText(arrList.get(position).getName());
        holder.tvContent.setText("Friend with : " +arrList.get(position).getRequest_html());
        imageLoader.DisplayImage(arrList.get(position).getPicture_url(),holder.ivAvatar);
        Log.e("Footer List" ,"Child List:"+arrList.get(position).getName());
        return convertView;
    }
}
