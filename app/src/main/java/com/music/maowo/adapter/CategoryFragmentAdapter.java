package com.music.maowo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.music.maowo.R;
import com.music.maowo.activity.HotAndNewArticleActivity;
import com.music.maowo.bean.CategoryResponse;

import java.util.List;

/**
 * Created by Administrator on 2017-9-24 0024.
 */

public class CategoryFragmentAdapter extends BaseAdapter {
    private List<CategoryResponse.DataBean.CateListBean> list;
    private Context context;

    public CategoryFragmentAdapter(List<CategoryResponse.DataBean.CateListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public CategoryResponse.DataBean.CateListBean getItem(int i) {
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
            convertView = View.inflate(context, R.layout.fragment_category_listview_item, null);
            holder = new ViewHolder();
            holder.iv_show = convertView.findViewById(R.id.iv_show);
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_reply_count = convertView.findViewById(R.id.tv_reply_count);
            holder.tv_praise_count = convertView.findViewById(R.id.tv_praise_count);
            holder.tv_description = convertView.findViewById(R.id.tv_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CategoryResponse.DataBean.CateListBean info = list.get(i);
        Glide.with(context).load(info.getPicture()).into(holder.iv_show);
        holder.tv_title.setText(info.getTitile());
        holder.tv_reply_count.setText("0");
        holder.tv_praise_count.setText("0");
        holder.tv_description.setText(info.getDescription());
        holder.info = info;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotAndNewArticleActivity.class);
                intent.putExtra(HotAndNewArticleActivity.TYPE_SELECTED, info.getCate_id());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public ImageView iv_show;
        public TextView tv_title;
        public TextView tv_reply_count;
        public TextView tv_praise_count;
        public TextView tv_description;
        public CategoryResponse.DataBean.CateListBean info;
    }
}
