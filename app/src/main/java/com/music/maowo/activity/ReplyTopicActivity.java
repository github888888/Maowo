package com.music.maowo.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.music.maowo.R;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.CommentInfo;
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
public class ReplyTopicActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
    private List<ReplyTopicInfo> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("参与的帖子");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();
        list.add(new ReplyTopicInfo("topic1",images.get(0),"author1","2017-09-08","相当完美","今天的活动很有意义"));
        list.add(new ReplyTopicInfo("topic2",images.get(1),"author1","2017-09-08","相当完美2","今天的活动很有意义3"));
        list.add(new ReplyTopicInfo("topic3",images.get(2),"author1","2017-09-08","相当完美3","今天的活动很有意义4"));
        list.add(new ReplyTopicInfo("topic4",images.get(3),"author1","2017-09-08","相当完美4","今天的活动很有意义5"));
        list.add(new ReplyTopicInfo("topic5",images.get(4),"author1","2017-09-08","相当完美5","今天的活动很有意义6"));
        adapter = new SubmitArticleAdapter();
        lv_content.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back,})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }

    private Random random = new Random();
    private class SubmitArticleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return null == list ? 0 : list.size();
        }

        @Override
        public ReplyTopicInfo getItem(int i) {
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
                view = View.inflate(getApplicationContext(), R.layout.activity_reply_topic_item_layout, null);
                holder = new Viewholder();
                holder.civ_author_show = view.findViewById(R.id.civ_author_show);
                holder.tv_reply_time = view.findViewById(R.id.tv_reply_time);
                holder.tv_author_name = view.findViewById(R.id.tv_author_name);
                holder.tv_reply_content_value = view.findViewById(R.id.tv_reply_content_value);
                holder.tv_topic_content_value = view.findViewById(R.id.tv_topic_content_value);
                view.setTag(holder);
            } else {
                holder = (Viewholder) view.getTag();
            }
            ReplyTopicInfo info = getItem(i);
            final CircleImageView temp_civ = holder.civ_author_show;
            temp_civ.setTag(info);
            holder.info = info;
            Glide.with(getApplicationContext()).load(info.authorUrl)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ReplyTopicInfo temp = (ReplyTopicInfo) temp_civ.getTag();
                    if (holder.info !=  temp) return;
                    temp_civ.setImageBitmap(resource);
                }
            });
            holder.tv_reply_time.setText(info.replyTime);
            holder.tv_author_name.setText(info.authorName);
            holder.tv_reply_content_value.setText(info.replyContent);
            holder.tv_topic_content_value.setText(info.topicContent);
            return view;
        }
    }

    public static class Viewholder {
        public CircleImageView civ_author_show;
        public TextView tv_reply_time;
        public TextView tv_author_name;
        public TextView tv_reply_content_value;
        public TextView tv_topic_content_value;
        public ReplyTopicInfo info;
    }


    public static class ReplyTopicInfo implements Serializable {
        public String topicId;
        public String authorUrl;
        public String authorName;
        public String replyTime;
        public String replyContent;
        public String topicContent;

        public ReplyTopicInfo(String topicId, String authorUrl, String authorName, String replyTime, String replyContent, String topicContent) {
            this.topicId = topicId;
            this.authorUrl = authorUrl;
            this.authorName = authorName;
            this.replyTime = replyTime;
            this.replyContent = replyContent;
            this.topicContent = topicContent;
        }
    }
}
