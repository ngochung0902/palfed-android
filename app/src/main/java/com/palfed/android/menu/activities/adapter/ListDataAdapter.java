package com.palfed.android.menu.activities.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.activities.customviews.CircularImageView;
import com.palfed.android.menu.activities.objects.ItemObject;
import com.palfed.android.menu.R;

@SuppressLint("InflateParams")
public class ListDataAdapter extends BaseAdapter{
	Context context;
	LayoutInflater inflater;
	private List<ItemObject> itemList = null;
	private int w = 1;
	public ListDataAdapter(Context mContext, List<ItemObject> itemList) {
		this.context = mContext;
		this.itemList = itemList;
		inflater = LayoutInflater.from(context);
		this.w = QTSRun.GetWidthDevice(this.context);
	}
	public class ViewHolder {
		TextView title;
		TextView content;
		TextView tvName;
		TextView tt_Restaurant;
		TextView tvBoder;
		TextView miles, miles2;
		TextView time;
		TextView lbEaten;
		TextView tvPortions;
		TextView day;
		TextView tvComment;
		TextView tvEaten;
		TextView tv_content5;
		ImageView iconHome, iconBaged,iconMile,iconSpace;
		ImageView iconMile2;
		CircularImageView imvAvatar;
		CircularImageView imvRestaurant;
		RelativeLayout rlRestaurant;
		RelativeLayout rlMile2;
		LinearLayout rlParentGroup;
		RelativeLayout relativeLayout;
		LinearLayout ll_group1;
		LinearLayout ll_group0;
		LinearLayout ll_icon_home;
		Button btnAdd;
		TextView tvLink;
	}
	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list, null);
			holder.rlParentGroup = (LinearLayout)convertView.findViewById(R.id.rl_ParentGroup);
			holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
			holder.tvBoder = (TextView) convertView.findViewById(R.id.tvBoder);
			holder.ll_group1 = (LinearLayout)convertView.findViewById(R.id.ll_group1);
			holder.ll_group0 = (LinearLayout)convertView.findViewById(R.id.ll_group0);
			holder.ll_icon_home = (LinearLayout)convertView.findViewById(R.id.ll_icon_home);
			holder.title = (TextView)convertView.findViewById(R.id.item_Title);
			holder.rlRestaurant = (RelativeLayout)convertView.findViewById(R.id.rl_Restaurant);
			holder.btnAdd = (Button)convertView.findViewById(R.id.btnAddFriend);
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
			holder.imvRestaurant = (CircularImageView)convertView.findViewById(R.id.ivPic_Restaurant);
			holder.tvLink = (TextView)convertView.findViewById(R.id.tvLink);
			holder.tv_content5 = (TextView) convertView.findViewById(R.id.tv_content5);
			holder.iconHome = (ImageView) convertView.findViewById(R.id.iconHome);
			holder.iconBaged = (ImageView) convertView.findViewById(R.id.iconBaged);
			holder.iconSpace = (ImageView) convertView.findViewById(R.id.iconSpace);
			holder.iconMile = (ImageView) convertView.findViewById(R.id.ivMiles);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		QTSRun.setLayoutView(holder.btnAdd,QTSRun.GetWidthDevice(this.context)/4,(int)context.getResources().getDimension(R.dimen.margin37));
		QTSRun.setLayoutView(holder.tvName,(int)(QTSRun.GetWidthDevice(this.context)/3.5),(int)context.getResources().getDimension(R.dimen.margin25));
		QTSRun.setLayoutView(holder.imvAvatar, w/8,w/8);
		QTSRun.setLayoutView(holder.imvRestaurant, w/8,w/8);
		QTSRun.setLayoutView(holder.iconHome, w/14,w/14);
		QTSRun.setLayoutView(holder.iconBaged, w/14,w/14);
		QTSRun.setLayoutView(holder.iconSpace, w/14,w/14);
		QTSRun.setLayoutView(holder.iconMile, w/14,w/14);
		QTSRun.setLayoutView(holder.iconMile2, w/14,w/14);

		QTSRun.setFontTV(context, holder.title, QTSConst.FONT_ROBOTOSLAB_BOLD);
		QTSRun.setFontTV(context, holder.content, QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context, holder.tvName, QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.tvPortions,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.tvEaten,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.lbEaten,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.tvComment,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.time,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.day,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.miles,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.miles2,QTSConst.FONT_ROBOTOSLAB_REGULAR);
		QTSRun.setFontTV(context,holder.tv_content5,QTSConst.FONT_ROBOTOSLAB_REGULAR);

		if (itemList.get(position).getIsItem().equalsIgnoreCase("1")){
			holder.rlRestaurant.setVisibility(View.GONE);
			holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
			holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text_white);
			holder.tvBoder.setText("I AM EATING THIS!");
			holder.tvBoder.setVisibility(View.VISIBLE);
			holder.ll_group1.setVisibility(View.VISIBLE);
			holder.ll_group0.setVisibility(View.VISIBLE);
			holder.tvLink.setVisibility(View.GONE);
			holder.btnAdd.setVisibility(View.GONE);
			holder.rlMile2.setVisibility(View.GONE);
			holder.tv_content5.setVisibility(View.GONE);
		}else if(itemList.get(position).getIsItem().equalsIgnoreCase("2")){
			holder.rlRestaurant.setVisibility(View.VISIBLE);
			holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
			holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text_white);
			holder.tvBoder.setText("I AM EATING THIS!");
			holder.tvBoder.setVisibility(View.VISIBLE);
			holder.ll_group1.setVisibility(View.GONE);
			holder.ll_group0.setVisibility(View.VISIBLE);
			holder.tvLink.setVisibility(View.GONE);
			holder.btnAdd.setVisibility(View.GONE);
			holder.rlMile2.setVisibility(View.GONE);
			holder.tv_content5.setVisibility(View.GONE);
		}else if (itemList.get(position).getIsItem().equalsIgnoreCase("3")){
			holder.rlRestaurant.setVisibility(View.GONE);
			holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_yellow);
			holder.tvBoder.setBackgroundResource(R.drawable.boder_rect_text);
			holder.tvBoder.setText("I AM MAKING THIS!");
			holder.tvBoder.setVisibility(View.VISIBLE);
			holder.ll_group1.setVisibility(View.VISIBLE);
			holder.ll_group0.setVisibility(View.VISIBLE);
			holder.tvLink.setVisibility(View.GONE);
			holder.btnAdd.setVisibility(View.GONE);
			holder.rlMile2.setVisibility(View.GONE);
			holder.tv_content5.setVisibility(View.GONE);
		}else if (itemList.get(position).getIsItem().equalsIgnoreCase("4")){
			holder.rlRestaurant.setVisibility(View.GONE);
			holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
			holder.tvBoder.setVisibility(View.GONE);
			holder.ll_group1.setVisibility(View.GONE);
			holder.ll_group0.setVisibility(View.GONE);
			holder.rlMile2.setVisibility(View.VISIBLE);
			holder.tvLink.setVisibility(View.VISIBLE);
			holder.btnAdd.setVisibility(View.GONE);
			holder.tv_content5.setVisibility(View.GONE);
		}else if (itemList.get(position).getIsItem().equalsIgnoreCase("5")){
			holder.rlRestaurant.setVisibility(View.GONE);
			holder.rlParentGroup.setBackgroundResource(R.drawable.boder_rect_white);
			holder.tvBoder.setVisibility(View.GONE);
			holder.ll_group1.setVisibility(View.GONE);
			holder.ll_group0.setVisibility(View.GONE);
			holder.tvLink.setVisibility(View.GONE);
			holder.btnAdd.setVisibility(View.VISIBLE);
			holder.rlMile2.setVisibility(View.GONE);
			holder.tv_content5.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	public void setData(List<ItemObject> itemList) {
		this.itemList = itemList;
		this.notifyDataSetChanged();
	}
}
