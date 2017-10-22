package com.music.maowo.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
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

@Layout(R.layout.activity_chat_layout)
public class MyChatDetailActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private ChatAdapter adapter;
    private List<ChatInfo> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("小罗");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();
        list.add(new ChatInfo(images.get(0), null, "你好"));
        list.add(new ChatInfo(null, images.get(1), "我好"));
        list.add(new ChatInfo(images.get(0), null, "你好附近啊卡萨丁给你啦收款方扣篮大赛荆防颗粒撒地方就撒"));
        list.add(new ChatInfo(null, images.get(1), "你好附近啊卡萨丁给你啦收款方扣篮大赛荆防颗粒撒地方就撒"));
        list.add(new ChatInfo(images.get(0), null, "你好附近啊卡萨丁给你啦收款方扣篮大赛荆防颗粒撒地方就撒你好附近啊卡萨丁给你啦收款方扣篮大赛荆防颗粒撒地方就撒"));
        list.add(new ChatInfo(null, images.get(1), "你好附近啊卡萨丁给你啦收款方扣篮大赛荆防颗粒撒地方就撒你好附近啊卡萨丁给你啦收款方扣篮大赛荆防颗粒撒地方就撒"));
        list.add(new ChatInfo(images.get(0), null, "你好"));
        list.add(new ChatInfo(null, images.get(1), "我好"));
        list.add(new ChatInfo(images.get(0), null, "你好"));
        adapter = new ChatAdapter();
        lv_content.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back,})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }

    private class ChatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return null == list ? 0 : list.size();
        }

        @Override
        public ChatInfo getItem(int i) {
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
                view = View.inflate(getApplicationContext(), R.layout.activity_mymesage_chat_item_layout, null);
                holder = new Viewholder();
                holder.civ_author_other_show = view.findViewById(R.id.civ_author_other_show);
                holder.civ_author_mine_show = view.findViewById(R.id.civ_author_mine_show);
                holder.tv_content = view.findViewById(R.id.tv_chat_content);
                view.setTag(holder);
            } else {
                holder = (Viewholder) view.getTag();
            }
            ChatInfo info = getItem(i);
            holder.info = info;
            if (!TextUtils.isEmpty(info.otherUrl)) {
                holder.civ_author_mine_show.setVisibility(View.INVISIBLE);
                holder.civ_author_other_show.setVisibility(View.VISIBLE);
                holder.tv_content.setBackgroundColor(Color.parseColor("#FFEA5959"));
                final CircleImageView temp_other_civ = holder.civ_author_other_show;
                temp_other_civ.setTag(info);
                Glide.with(getApplicationContext()).load(info.otherUrl)
                        .asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ChatInfo temp = (ChatInfo) temp_other_civ.getTag();
                        if (holder.info !=  temp) return;
                        temp_other_civ.setImageBitmap(resource);
                    }
                });
            } else {
                holder.civ_author_mine_show.setVisibility(View.VISIBLE);
                holder.civ_author_other_show.setVisibility(View.INVISIBLE);

                holder.tv_content.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                final CircleImageView temp_mine_civ = holder.civ_author_mine_show;
                temp_mine_civ.setTag(info);
                Glide.with(getApplicationContext()).load(info.mingUrl)
                        .asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ChatInfo temp = (ChatInfo) temp_mine_civ.getTag();
                        if (holder.info !=  temp) return;
                        temp_mine_civ.setImageBitmap(resource);
                    }
                });
            }
            holder.tv_content.setText(info.content);
            return view;
        }
    }

    public static class Viewholder {
        public CircleImageView civ_author_other_show;
        public CircleImageView civ_author_mine_show;
        public TextView tv_content;
        public ChatInfo info;
    }

    public static class ChatResponseInfo {
        public int otherId;
        public List<ChatInfo> list;

        public ChatResponseInfo(int otherId, List<ChatInfo> list) {
            this.otherId = otherId;
            this.list = list;
        }
    }

    public static class ChatInfo {
        public String mingUrl;
        public String otherUrl;
        public String content;

        public ChatInfo(String mingUrl, String otherUrl, String content) {
            this.mingUrl = mingUrl;
            this.otherUrl = otherUrl;
            this.content = content;
        }
    }
}
