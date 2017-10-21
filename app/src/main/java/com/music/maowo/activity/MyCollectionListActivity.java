package com.music.maowo.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.music.maowo.R;
import com.music.maowo.anno.Layout;
import com.music.maowo.view.CircleImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-10-17 0017.
 */

@Layout(R.layout.activity_mine_common_layout)
public class MyCollectionListActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
    private List<CollectionInfo> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("我的收藏");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();
        list.add(new CollectionInfo(images.get(0),"author1", "2017-09-08", 1, images.get(4), "title1"));
        list.add(new CollectionInfo(images.get(1),"author2", "2017-09-08", 2, images.get(3), "title2"));
        list.add(new CollectionInfo(images.get(2),"author3", "2017-09-08", 1, images.get(2), "title3"));
        list.add(new CollectionInfo(images.get(3),"author4", "2017-09-08", 1, images.get(1), "title4"));
        list.add(new CollectionInfo(images.get(4),"author5", "2017-09-08", 3, images.get(0), "title5"));
        adapter = new SubmitArticleAdapter();
        lv_content.addHeaderView(new View(this));
        lv_content.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back,})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }

    private class SubmitArticleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return null == list ? 0 : list.size();
        }

        @Override
        public CollectionInfo getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final Viewholder holder;
            if (null == view) {
                view = View.inflate(getApplicationContext(), R.layout.activity_mycollection_item_layout, null);
                holder = new Viewholder();
                holder.civ_author_show = view.findViewById(R.id.civ_author_show);
                holder.tv_author_name = view.findViewById(R.id.tv_author_name);
                holder.tv_collection_time = view.findViewById(R.id.tv_collection_time);
                holder.tv_collection_type = view.findViewById(R.id.tv_collection_type);
                holder.iv_article_show = view.findViewById(R.id.iv_article_show);
                holder.tv_article_title = view.findViewById(R.id.tv_article_title);
                view.setTag(holder);
            } else {
                holder = (Viewholder) view.getTag();
            }
            CollectionInfo info = getItem(i);
            final CircleImageView temp_civ = holder.civ_author_show;
            temp_civ.setTag(info);
            holder.info = info;
            Glide.with(getApplicationContext()).load(info.authorUrl)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    CollectionInfo temp = (CollectionInfo) temp_civ.getTag();
                    if (holder.info !=  temp) return;
                    temp_civ.setImageBitmap(resource);
                }
            });
            holder.tv_author_name.setText(info.authorName);
            holder.tv_collection_time.setText("(" + info.collectionTime + ")");
            switch (info.type) {
                case 1:
                    holder.tv_collection_type.setText("校园");
                    holder.tv_collection_type.setTextColor(0xFF10BBE6);
                    holder.tv_collection_type.setBackgroundResource(R.drawable.collection_article_type1_shape);
                    break;
                case 2:
                    holder.tv_collection_type.setText("故事");
                    holder.tv_collection_type.setTextColor(0xFFFF9053);
                    holder.tv_collection_type.setBackgroundResource(R.drawable.collection_article_type2_shape);
                    break;
                case 3:
                    holder.tv_collection_type.setText("旅游");
                    holder.tv_collection_type.setTextColor(0xFFEA5959);
                    holder.tv_collection_type.setBackgroundResource(R.drawable.collection_article_type3_shape);
                    break;
            }
            final ImageView temp_img = holder.iv_article_show;
            temp_img.setTag(info);
            Glide.with(getApplicationContext()).load(info.imgUrl)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    CollectionInfo temp = (CollectionInfo) temp_civ.getTag();
                    if (holder.info !=  temp) return;
                    temp_img.setImageBitmap(resource);
                }
            });
            holder.tv_article_title.setText(info.articleTitle);
            return view;
        }
    }

    public static class Viewholder {
        public CircleImageView civ_author_show;
        public TextView tv_author_name;
        public TextView tv_collection_time;
        public TextView tv_collection_type;
        public ImageView iv_article_show;
        public TextView tv_article_title;
        public CollectionInfo info;
    }

    public class CollectionInfo {
        public String authorUrl;
        public String authorName;
        public String collectionTime;
        public int type;
        public String imgUrl;
        public String articleTitle;

        public CollectionInfo(String authorUrl, String authorName, String collectionTime, int type, String imgUrl, String articleTitle) {
            this.authorUrl = authorUrl;
            this.authorName = authorName;
            this.collectionTime = collectionTime;
            this.type = type;
            this.imgUrl = imgUrl;
            this.articleTitle = articleTitle;
        }
    }
}
