package com.music.maowo.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.music.maowo.R;
import com.music.maowo.bean.TopicSummaryInfo;

import java.util.List;

/**
 * Created by Administrator on 2017-9-24 0024.
 */

public class HomeFragmentAdapter extends BaseAdapter {
    private List<TopicSummaryInfo> list;
    private Context context;

    public HomeFragmentAdapter(List<TopicSummaryInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public TopicSummaryInfo getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.fragment_home_gridview_item, null);
            holder = new ViewHolder();
            holder.iv_show = convertView.findViewById(R.id.iv_show);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_description = convertView.findViewById(R.id.tv_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TopicSummaryInfo info = list.get(i);
        Glide.with(context).load(info.imgUrl).into(holder.iv_show);
        holder.tv_title.setText(info.title);
        holder.tv_description.setText(info.description);
        holder.info = info;
        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_show;
        public TextView tv_title;
        public TextView tv_description;
        public TopicSummaryInfo info;
    }
}
