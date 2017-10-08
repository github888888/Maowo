package com.music.maowo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.music.maowo.R;
import com.music.maowo.bean.TopicSummaryInfo;
import com.music.maowo.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017-9-24 0024.
 */

public class HotAndNewArticelAdapter extends BaseAdapter {
    private List<TopicSummaryInfo> list;
    private Context context;

    public HotAndNewArticelAdapter(List<TopicSummaryInfo> list, Context context) {
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.hot_and_new_article_listview_item, null);
            holder = new ViewHolder();
            holder.iv_show = convertView.findViewById(R.id.iv_show);
            holder.tv_article_type = convertView.findViewById(R.id.tv_article_type);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_reply_count = convertView.findViewById(R.id.tv_reply_count);
            holder.tv_praise_count = convertView.findViewById(R.id.tv_praise_count);
            holder.civ_header = convertView.findViewById(R.id.civ_header);
            holder.tv_author = convertView.findViewById(R.id.tv_author);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TopicSummaryInfo info = list.get(i);
        Glide.with(context).load(info.imgUrl).into(holder.iv_show);
        holder.tv_title.setText(info.title);
        holder.tv_reply_count.setText("" + info.reply_count);
        holder.tv_praise_count.setText("" + info.praise_count);
        holder.tv_praise_count.setSelected(info.isPraiseByMe);
        holder.civ_header.setTag(info.authorHeaderUrl);
        Glide.with(context).load(info.authorHeaderUrl)
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                String url = (String) holder.civ_header.getTag();
                TopicSummaryInfo temp = list.get(i);
                if (!temp.authorHeaderUrl.equals(url)) return;
                holder.civ_header.setImageBitmap(resource);
            }
        });
        holder.tv_author.setText(info.author);
        holder.info = info;
        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_show;
        public TextView tv_article_type;
        public TextView tv_title;
        public TextView tv_reply_count;
        public TextView tv_praise_count;
        public CircleImageView civ_header;
        public TextView tv_author;
        public TopicSummaryInfo info;
    }
}
