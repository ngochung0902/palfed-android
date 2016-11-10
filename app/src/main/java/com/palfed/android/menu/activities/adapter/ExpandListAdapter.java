package com.palfed.android.menu.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palfed.android.menu.activities.WebBrowser;
import com.palfed.android.menu.activities.commonhelper.ImageLoaderAvar;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.activities.customviews.CircularImageView;
import com.palfed.android.menu.activities.objects.MenuObject;
import com.palfed.android.menu.activities.objects.OptionObject;
import com.palfed.android.menu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Android QTS on 1/4/2016.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ImageLoaderAvar imageLoader;
    private List<MenuObject> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<MenuObject, List<OptionObject>> _listDataChild;
    private int w = 1;
//    private String addfriend_url;

    public ExpandListAdapter(Context context,
                             List<MenuObject> listDataHeader,
                                 HashMap<MenuObject, List<OptionObject>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.w = QTSRun.GetWidthDevice(this._context);
        this.imageLoader = new ImageLoaderAvar(_context);
//        this.addfriend_url = addfriend_url;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(
                this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    public class ViewHolder {
        TextView line1, space_width;
        TextView title;
        TextView content;
        TextView tvName;
        TextView tt_Restaurant;
        TextView tvBoder,tvSp;
        TextView miles;
        TextView time;
        TextView lbEaten;
        TextView tvPortions;
        TextView day;
        TextView tvComment;
        TextView tvEaten;
        TextView tv_content5;
        TextView space_top, space_top1, space_bottom;
        ImageView iconHome, iconBaged,iconMile,iconSpace;
        CircularImageView imvAvatar;
        CircularImageView imvRestaurant;
        CircularImageView imvAvatarTitle;
        RelativeLayout rlRestaurant;
        RelativeLayout rlMile;
        LinearLayout rlParentGroup;
        RelativeLayout relativeLayout;
        LinearLayout ll_group2;
        LinearLayout ll_group1;
        LinearLayout ll_group0;
        LinearLayout ll_icon_home;
        LinearLayout ll_right_parent;
        LinearLayout ll_group_main;
        Button btnAdd, btnInviteFriend, btnFriendFinder, btnMake;
        TextView tvLink, align_top_menu;
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final OptionObject childText = _listDataChild.get(
                _listDataHeader.get(groupPosition)).get(childPosition);
        int sizeChild = getChildrenCount(groupPosition);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_list, null);
            holder.rlParentGroup = (LinearLayout)convertView.findViewById(R.id.rl_ParentGroup);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            holder.tvBoder = (TextView) convertView.findViewById(R.id.tvBoder);
            holder.space_top = (TextView) convertView.findViewById(R.id.spacetop);
            holder.space_top1 = (TextView) convertView.findViewById(R.id.spacetop1);
            holder.space_bottom = (TextView) convertView.findViewById(R.id.space_bottom);
            holder.ll_group1 = (LinearLayout)convertView.findViewById(R.id.ll_group1);
            holder.ll_group0 = (LinearLayout)convertView.findViewById(R.id.ll_group0);
            holder.ll_group2 = (LinearLayout)convertView.findViewById(R.id.ll_group2);
            holder.ll_group_main = (LinearLayout)convertView.findViewById(R.id.ll_group_main);
            holder.ll_icon_home = (LinearLayout)convertView.findViewById(R.id.ll_icon_home);
            holder.ll_right_parent = (LinearLayout)convertView.findViewById(R.id.ll_right_parent);
            holder.space_width = (TextView) convertView.findViewById(R.id.space_width);
            holder.tvSp = (TextView) convertView.findViewById(R.id.tvSp);
            holder.line1 = (TextView) convertView.findViewById(R.id.line1);
            holder.title = (TextView)convertView.findViewById(R.id.item_Title);
            holder.rlRestaurant = (RelativeLayout)convertView.findViewById(R.id.rl_Restaurant);
            holder.btnAdd = (Button)convertView.findViewById(R.id.btnAddFriend);
            holder.btnMake = (Button)convertView.findViewById(R.id.btnMake);
            holder.btnInviteFriend = (Button)convertView.findViewById(R.id.btnInviteFriend);
            holder.btnFriendFinder = (Button)convertView.findViewById(R.id.btnFriendFinder);
            holder.rlMile = (RelativeLayout)convertView.findViewById(R.id.rl_Road);
            holder.tt_Restaurant = (TextView)convertView.findViewById(R.id.tvRestaurant);
            holder.content = (TextView)convertView.findViewById(R.id.item_content);
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            holder.miles = (TextView)convertView.findViewById(R.id.tv_mile);
            holder.time = (TextView)convertView.findViewById(R.id.tvHour);
            holder.day = (TextView)convertView.findViewById(R.id.tvHour_ago);
            holder.lbEaten = (TextView)convertView.findViewById(R.id.item3);
            holder.tvEaten = (TextView)convertView.findViewById(R.id.item4);
            holder.tvPortions = (TextView)convertView.findViewById(R.id.item2);
            holder.tvComment = (TextView)convertView.findViewById(R.id.item5);
            holder.imvAvatar = (CircularImageView)convertView.findViewById(R.id.view);
            holder.imvAvatarTitle = (CircularImageView)convertView.findViewById(R.id.ivPic_Title);
            holder.imvRestaurant = (CircularImageView)convertView.findViewById(R.id.ivPic_Restaurant);
            holder.tvLink = (TextView)convertView.findViewById(R.id.tvLink);
            holder.tv_content5 = (TextView) convertView.findViewById(R.id.tv_content5);
            holder.iconHome = (ImageView) convertView.findViewById(R.id.iconHome);
            holder.iconBaged = (ImageView) convertView.findViewById(R.id.iconBaged);
            holder.iconSpace = (ImageView) convertView.findViewById(R.id.iconSpace);
            holder.iconMile = (ImageView) convertView.findViewById(R.id.ivMiles);
            holder.align_top_menu = (TextView) convertView.findViewById(R.id.align_top_menu);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        QTSRun.setLayoutView(holder.btnAdd, (int)(w / 4.5), (int) _context.getResources().getDimension(R.dimen.margin30));
        QTSRun.setLayoutView(holder.btnMake,(int)(w/4.5),(int)_context.getResources().getDimension(R.dimen.margin30));
        QTSRun.setLayoutView(holder.btnInviteFriend,(int)(w/4),(int)_context.getResources().getDimension(R.dimen.margin30));
        QTSRun.setLayoutView(holder.btnFriendFinder,(int)(w/4),(int)_context.getResources().getDimension(R.dimen.margin30));
        QTSRun.setLayoutView(holder.space_width,(int)(w/3.3),(int)_context.getResources().getDimension(R.dimen.margin5));
        QTSRun.setLayoutView(holder.tvSp,(int)(w/3.3),(int)_context.getResources().getDimension(R.dimen.margin25));
        QTSRun.setLayoutView(holder.imvAvatar, w/5,w/5);
        QTSRun.setLayoutView(holder.imvRestaurant, w/7,w/7);
        QTSRun.setLayoutView(holder.iconHome, w/14,w/14);
        QTSRun.setLayoutView(holder.iconBaged, w/14,w/14);
        QTSRun.setLayoutView(holder.iconSpace, w/14,w/14);
        QTSRun.setLayoutView(holder.iconMile, w / 14, w / 14);
        QTSRun.setLayoutView(holder.btnAdd, (int)(w / 3.5), (int) _context.getResources().getDimension(R.dimen.margin30));

        QTSRun.setFontTV(_context, holder.title, QTSConst.FONT_ROBOTOSLAB_BOLD);
        QTSRun.setFontTV(_context, holder.tt_Restaurant, QTSConst.FONT_ROBOTOSLAB_BOLD);
        QTSRun.setFontTV(_context, holder.content, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(_context, holder.tvName, QTSConst.FONT_ROBOTOSLAB_BOLD);
        QTSRun.setFontTV(_context, holder.tvPortions, QTSConst.FONT_ROBOTOSLAB_BOLD);
        QTSRun.setFontTV(_context,holder.tvEaten,QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(_context,holder.lbEaten,QTSConst.FONT_ROBOTOSLAB_BOLD);
        QTSRun.setFontTV(_context,holder.tvComment,QTSConst.FONT_ROBOTOSLAB_BOLD);
        QTSRun.setFontTV(_context,holder.time,QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(_context,holder.day,QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(_context,holder.miles,QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(_context, holder.tv_content5, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(_context, holder.btnAdd, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(_context, holder.btnInviteFriend, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(_context, holder.btnFriendFinder, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(_context, holder.btnMake, QTSConst.FONT_ROBOTOSLAB_REGULAR);

        holder.title.setText(childText.getTitle());
        holder.content.setText(childText.getDescription());
        Log.e("getGroupView","childrent:"+childText.getTitle());
        if (childText.getName() != null ){
            holder.tvName.setText(childText.getName().toUpperCase());
        }else{
            holder.tvName.setText("");
        }
        if (childText.getFood_image_url().length()>0){
            holder.imvAvatarTitle.setVisibility(View.VISIBLE);
            imageLoader.DisplayImage(childText.getFood_image_url(),holder.imvAvatarTitle);
        }else {
            holder.imvAvatarTitle.setVisibility(View.GONE);
        }
        if (childText.getNotify_text() != null ){
            holder.tvBoder.setText(childText.getNotify_text().toUpperCase());
        }else{
            holder.tvBoder.setText("");
        }
        if (childText.getComments() != null ){
            holder.tvComment.setText("COMMENTS: "+childText.getComments());
        }else{
            holder.tvComment.setText("COMMENTS: 0");
        }
        if (childText.getDistance()!= null){
            holder.miles.setText(childText.getDistance()+" "+childText.getDistance_unit());
            holder.miles.setVisibility(View.VISIBLE);
            holder.iconMile.setVisibility(View.VISIBLE);

        }else{
            holder.miles.setVisibility(View.GONE);
            holder.iconMile.setVisibility(View.GONE);
        }
        if (childText.getAvailable_portions() != null ){
            holder.tvPortions.setText("PORTIONS AVAILABLE: " + childText.getAvailable_portions());
        }else{
            holder.tvPortions.setText("");
        }
        if (childText.getEating() != null ){
            holder.tvEaten.setText(childText.getEating());
        }else{
            holder.tvEaten.setText("");
        }
        if (childText.getReady_time() != null ){
            holder.time.setText(childText.getReady_time());
        }else{
            holder.time.setText("");
        }
        if (childText.getFrom_now_text() != null ){
            holder.day.setText(childText.getFrom_now_text());
        }else{
            holder.day.setText("");
        }
        if (childText.getCan_join()!= null && childText.getCan_join().equalsIgnoreCase("1")){
            holder.iconHome.setBackgroundResource(R.drawable.ic_home);
        }else if(childText.getCan_join()!= null && childText.getCan_join().equalsIgnoreCase("0")) {
            holder.iconHome.setBackgroundResource(R.drawable.item_car);
        }
        if (childText.getBring_container() != null && childText.getBring_container().equalsIgnoreCase("1")){
            holder.iconBaged.setVisibility(View.VISIBLE);
        }else if(childText.getBring_container() != null && childText.getBring_container().equalsIgnoreCase("0")){
            holder.iconBaged.setVisibility(View.INVISIBLE);
        }
        if (childText.getProfile_pic_url()!= null){
            imageLoader.DisplayImage(childText.getProfile_pic_url(), holder.imvAvatar);
        }
        if (childText.getRestaurant_profile_pic_url()!= null){
            imageLoader.DisplayImage(childText.getRestaurant_profile_pic_url(), holder.imvRestaurant);
        }
        if (childPosition == (sizeChild - 1)){
            holder.space_bottom.setVisibility(View.VISIBLE);
        }else{
            holder.space_bottom.setVisibility(View.GONE);
        }
        if (childText.getMenu_type().equalsIgnoreCase("food")){
            holder.rlRestaurant.setVisibility(View.GONE);
            holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
            if (childText.getNotify_text().trim().length()> 0){
                holder.align_top_menu.setVisibility(View.VISIBLE);
                holder.tvBoder.setVisibility(View.VISIBLE);
                holder.space_top.setVisibility(View.INVISIBLE);
                holder.space_top1.setVisibility(View.GONE);
                if (childText.getI_am_making_this().equalsIgnoreCase("1")){
                    holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text);
                }else{
                    holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text_white);
                }
            }else{
                holder.align_top_menu.setVisibility(View.GONE);
                holder.space_top1.setVisibility(View.INVISIBLE);
                holder.space_top.setVisibility(View.GONE);
                holder.tvBoder.setVisibility(View.GONE);
            }
            holder.miles.setVisibility(View.VISIBLE);
            holder.iconMile.setVisibility(View.VISIBLE);
            holder.ll_group1.setVisibility(View.VISIBLE);
            holder.ll_group0.setVisibility(View.VISIBLE);
            holder.tvLink.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.GONE);
            holder.tv_content5.setVisibility(View.GONE);
            holder.ll_group2.setVisibility(View.GONE);
            holder.line1.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.tvSp.setVisibility(View.VISIBLE);
            holder.ll_group_main.setEnabled(true);
        }else if(childText.getMenu_type().equalsIgnoreCase("eating_out")){
            holder.rlRestaurant.setVisibility(View.VISIBLE);
            holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
            if (childText.getNotify_text().trim().length()> 0){
                holder.align_top_menu.setVisibility(View.VISIBLE);
                holder.tvBoder.setVisibility(View.VISIBLE);
                holder.space_top.setVisibility(View.INVISIBLE);
                holder.space_top1.setVisibility(View.GONE);
                if (childText.getI_am_making_this().equalsIgnoreCase("1")){
                    holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text);
                }else{
                    holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text_white);
                }
            }else{
                holder.align_top_menu.setVisibility(View.GONE);
                holder.space_top1.setVisibility(View.INVISIBLE);
                holder.space_top.setVisibility(View.GONE);
                holder.tvBoder.setVisibility(View.GONE);
            }
            holder.ll_group1.setVisibility(View.GONE);
            holder.ll_group0.setVisibility(View.VISIBLE);
            holder.tvLink.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.GONE);
            holder.tv_content5.setVisibility(View.GONE);
            holder.ll_group2.setVisibility(View.GONE);
            holder.line1.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.tvSp.setVisibility(View.VISIBLE);
            holder.ll_group_main.setEnabled(true);
        }else if (childText.getMenu_type().equalsIgnoreCase("i_am_making")){
            holder.rlRestaurant.setVisibility(View.GONE);
            holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_yellow);
            if (childText.getNotify_text().trim().length()> 0){
                holder.align_top_menu.setVisibility(View.VISIBLE);
                holder.space_top.setVisibility(View.INVISIBLE);
                holder.space_top1.setVisibility(View.GONE);
                holder.tvBoder.setVisibility(View.VISIBLE);
                if (childText.getI_am_making_this().equalsIgnoreCase("1")){
                    holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text);
                }else{
                    holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text_white);
                }
            }else{
                holder.align_top_menu.setVisibility(View.GONE);
                holder.space_top1.setVisibility(View.INVISIBLE);
                holder.space_top.setVisibility(View.GONE);
                holder.tvBoder.setVisibility(View.GONE);
            }
            holder.ll_group1.setVisibility(View.VISIBLE);
            holder.ll_group0.setVisibility(View.VISIBLE);
            holder.tvLink.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.GONE);
            holder.tv_content5.setVisibility(View.GONE);
            holder.rlMile.setVisibility(View.GONE);
            holder.line1.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.tvSp.setVisibility(View.VISIBLE);
            holder.ll_group2.setVisibility(View.GONE);
            holder.ll_group_main.setEnabled(true);
        }else if (childText.getMenu_type().equalsIgnoreCase("advert")){
            holder.align_top_menu.setVisibility(View.GONE);
            holder.rlRestaurant.setVisibility(View.GONE);
            holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
            holder.tvBoder.setVisibility(View.GONE);
            holder.ll_group1.setVisibility(View.GONE);
            holder.ll_group0.setVisibility(View.GONE);
            holder.tvLink.setVisibility(View.VISIBLE);
            holder.btnAdd.setVisibility(View.GONE);
            holder.tv_content5.setVisibility(View.GONE);
            holder.ll_group2.setVisibility(View.GONE);
            holder.line1.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.tvSp.setVisibility(View.VISIBLE);
            holder.ll_group_main.setEnabled(true);
            holder.space_top1.setVisibility(View.INVISIBLE);
            holder.space_top.setVisibility(View.GONE);
        }else if (childText.getMenu_type().equalsIgnoreCase("mutual_friend")){
            holder.align_top_menu.setVisibility(View.GONE);
            holder.rlRestaurant.setVisibility(View.GONE);
            holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
            holder.tvBoder.setVisibility(View.GONE);
            holder.ll_group1.setVisibility(View.GONE);
            holder.ll_group0.setVisibility(View.GONE);
            holder.tvLink.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.tv_content5.setVisibility(View.VISIBLE);
            holder.rlMile.setVisibility(View.GONE);
            holder.ll_group2.setVisibility(View.GONE);
            holder.line1.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.tvSp.setVisibility(View.VISIBLE);
            holder.ll_group_main.setEnabled(true);
            holder.space_top1.setVisibility(View.INVISIBLE);
            holder.space_top.setVisibility(View.GONE);
        }else if (childText.getMenu_type().equalsIgnoreCase("message_item")){
            holder.align_top_menu.setVisibility(View.GONE);
            holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
            holder.ll_group2.setVisibility(View.VISIBLE);
            holder.line1.setVisibility(View.GONE);
            holder.relativeLayout.setVisibility(View.GONE);
            holder.tvSp.setVisibility(View.GONE);
            holder.tvBoder.setVisibility(View.GONE);
            holder.rlRestaurant.setVisibility(View.GONE);
            holder.ll_group1.setVisibility(View.GONE);
            holder.ll_group_main.setEnabled(false);
            holder.space_top1.setVisibility(View.INVISIBLE);
            holder.space_top.setVisibility(View.GONE);
        }
        holder.ll_group_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("url", childText.getUrl());
                Intent intent = new Intent(_context,WebBrowser.class);
                intent.putExtra("url",childText.getUrl());
                _context.startActivity(intent);
            }
        });

        holder.imvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("url", childText.getUrl());
                Intent intent = new Intent(_context,WebBrowser.class);
                intent.putExtra("url",childText.getUrl());
                _context.startActivity(intent);
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("url", childText.getUrl());

                Intent intent = new Intent(_context,WebBrowser.class);
                intent.putExtra("url",childText.getUrl());
                _context.startActivity(intent);
            }
        });
        holder.btnFriendFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_context,WebBrowser.class);
                intent.putExtra("url",QTSConst.URL_FRIEND_FINDER);
                _context.startActivity(intent);
            }
        });
        holder.btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_context,WebBrowser.class);
                intent.putExtra("url",QTSConst.URL_MAKE);
                _context.startActivity(intent);
            }
        });
        holder.btnInviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_context,WebBrowser.class);
                intent.putExtra("url",QTSConst.URL_INVITE_FRIENDS);
                _context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(
                this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final MenuObject itemMenu = (MenuObject) getGroup(groupPosition);
        // Log.e("size item parent", getGroupCount() + "");

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_header_list, null);
        }
        Log.e("getGroupView","parent:"+itemMenu.getDay());
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tvDay);
        TextView date_parent = (TextView) convertView
                .findViewById(R.id.tvDate);
        QTSRun.setFontTV(_context, lblListHeader, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(_context,date_parent,QTSConst.FONT_ROBOTOSLAB_REGULAR);
        if (itemMenu.getDay_name().length()> 0){
            lblListHeader.setText(itemMenu.getDay_name());
            date_parent.setVisibility(View.VISIBLE);
            date_parent.setText(itemMenu.getDay());
        }else{
            lblListHeader.setText(itemMenu.getDay());
            date_parent.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}