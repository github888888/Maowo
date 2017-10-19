package com.music.maowo.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.anno.Layout;

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
public class PublishTopicActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
    private List<TopicGroupInfo> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("发表的帖子");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();

        List<TopicInfo> tempList= new ArrayList<>();
        tempList.add(new TopicInfo("id1","title1", "description1", 2, 3));
        tempList.add(new TopicInfo("id2","title2", "description2", 3, 5));
        list.add(new TopicGroupInfo("8月25日", tempList));
        tempList= new ArrayList<>();
        tempList.add(new TopicInfo("id3","title3", "description3", 20, 45));
        list.add(new TopicGroupInfo("8月26日", tempList));
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
        public TopicGroupInfo getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Viewholder viewholder = null;
            if (null == view) {
                view = View.inflate(getApplicationContext(), R.layout.activity_post_topic_group_item_layout, null);
                viewholder = new Viewholder();
                viewholder.iv_date_show = view.findViewById(R.id.iv_date_show);
                viewholder.tv_post_time = view.findViewById(R.id.tv_post_time);
                viewholder.ll_container = view.findViewById(R.id.ll_container);
                view.setTag(viewholder);
            } else {
                viewholder = (Viewholder) view.getTag();
            }
            TopicGroupInfo info = getItem(i);
            int randomNum = random.nextInt(2);
            viewholder.iv_date_show.setImageResource(randomNum == 0 ? R.mipmap.date_blue_icon : R.mipmap.date_orange_icon);
            viewholder.tv_post_time.setTextColor(randomNum == 0 ? 0xFF10BBE6 : 0xFFFF9053);
            viewholder.tv_post_time.setText(info.publishTime);

            viewholder.ll_container.removeAllViews();
            for (int index = 0; index < info.list.size(); index++) {
                View innerView = View.inflate(getApplicationContext(), R.layout.activity_post_topic_item_layout, null);
                TextView tv_submit_title = innerView.findViewById(R.id.tv_submit_title);
                TextView tv_submit_pass_description = innerView.findViewById(R.id.tv_submit_pass_description);
                TextView tv_read_count = innerView.findViewById(R.id.tv_read_count);
                TextView tv_reply_count = innerView.findViewById(R.id.tv_reply_count);

                TopicInfo topicInfo = info.list.get(index);
                tv_submit_title.setText(topicInfo.title);
                tv_submit_pass_description.setText(topicInfo.content);
                tv_read_count.setText("阅读(" +topicInfo.readCount+")");
                tv_reply_count.setText("评论(" +topicInfo.commentCount+")");
                innerView.findViewById(R.id.v_up).setVisibility(index == 0 ? View.INVISIBLE : View.VISIBLE);
                viewholder.ll_container.addView(innerView);
            }
            return view;
        }
    }

    public static class Viewholder {
        public ImageView iv_date_show;
        public TextView tv_post_time;
        public LinearLayout ll_container;
    }


    public static class TopicGroupInfo implements  Serializable{
        public String publishTime;

        public TopicGroupInfo(String publishTime, List<TopicInfo> list) {
            this.publishTime = publishTime;
            this.list = list;
        }

        public List<TopicInfo> list;
    }

    public static class TopicInfo implements Serializable {
        public String id;
        public String title;
        public String content;
        public int readCount;

        public TopicInfo(String id, String title, String content, int readCount, int commentCount) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.readCount = readCount;
            this.commentCount = commentCount;
        }

        public int commentCount;
    }
}
