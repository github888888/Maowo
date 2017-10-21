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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-10-17 0017.
 */

@Layout(R.layout.activity_mine_common_layout)
public class MyMessageListActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
    private List<MessageSummaryInfo> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("我的私信");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();
        list.add(new MessageSummaryInfo(1,images.get(0), "auther1", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(2,images.get(1), "auther2", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(3,images.get(2), "auther3", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(4,images.get(3), "auther4", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(5,images.get(2), "auther5", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(6,images.get(1), "auther6", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(7,images.get(4), "auther7", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(8,images.get(0), "auther8", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(1,images.get(0), "auther1", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(2,images.get(1), "auther2", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(3,images.get(2), "auther3", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(4,images.get(3), "auther4", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(5,images.get(2), "auther5", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(6,images.get(1), "auther6", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(7,images.get(4), "auther7", "愿你开心每一天", "2017-09-08"));
        list.add(new MessageSummaryInfo(8,images.get(0), "auther8", "愿你开心每一天", "2017-09-08"));
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
        public MessageSummaryInfo getItem(int i) {
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
                view = View.inflate(getApplicationContext(), R.layout.activity_mymesage_item_layout, null);
                holder = new Viewholder();
                holder.civ_author_show = view.findViewById(R.id.civ_author_show);
                holder.tv_chat_time = view.findViewById(R.id.tv_chat_time);
                holder.tv_author_name = view.findViewById(R.id.tv_author_name);
                holder.tv_message_content = view.findViewById(R.id.tv_message_content);
                view.setTag(holder);
            } else {
                holder = (Viewholder) view.getTag();
            }
            MessageSummaryInfo info = getItem(i);
            final CircleImageView temp_civ = holder.civ_author_show;
            temp_civ.setTag(info);
            holder.info = info;
            Glide.with(getApplicationContext()).load(info.authorUrl)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    MessageSummaryInfo temp = (MessageSummaryInfo) temp_civ.getTag();
                    if (holder.info !=  temp) return;
                    temp_civ.setImageBitmap(resource);
                }
            });
            holder.tv_author_name.setText(info.authorName);
            holder.tv_chat_time.setText(info.chatTime);
            holder.tv_message_content.setText(info.firstMessage);
            return view;
        }
    }

    public static class Viewholder {
        public CircleImageView civ_author_show;
        public TextView tv_chat_time;
        public TextView tv_author_name;
        public TextView tv_message_content;
        public MessageSummaryInfo info;
    }

    public class MessageSummaryInfo {
        public int chatId;
        public String authorUrl;
        public String authorName;
        public String firstMessage;
        public String chatTime;

        public MessageSummaryInfo(int chatId, String authorUrl, String authorName, String firstMessage, String chatTime) {
            this.chatId = chatId;
            this.authorUrl = authorUrl;
            this.authorName = authorName;
            this.firstMessage = firstMessage;
            this.chatTime = chatTime;
        }
    }
}
