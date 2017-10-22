package com.music.maowo.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.anno.Layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.music.maowo.activity.SystemInfoDetailActivity.INFO_TAG;

/**
 * Created by Administrator on 2017-10-17 0017.
 */

@Layout(R.layout.activity_mine_common_layout)
public class SystemMessageListActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
    private List<SystemMessage> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("提交的文章");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();
        list.add(new SystemMessage(1, 1,"title1", "content1", "2017-09-08"));
        list.add(new SystemMessage(2, 2,"title2", "content2", "2017-09-08"));
        list.add(new SystemMessage(3, 3,"title3", "content3", "2017-09-08"));
        adapter = new SubmitArticleAdapter();
        lv_content.addHeaderView(new View(this));
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Viewholder holder = (Viewholder) view.getTag();
                SystemMessage message = holder.message;
                gotoArticleDetailActivity(message);
            }
        });
    }

    private void gotoArticleDetailActivity(SystemMessage systemMessage) {
        Intent intent = new Intent(this, SystemInfoDetailActivity.class);
        intent.putExtra(INFO_TAG, systemMessage);
        startActivity(intent);
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
        public SystemMessage getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Viewholder viewholder;
            if (null == view) {
                view = View.inflate(getApplicationContext(), R.layout.activity_system_message_item_layout, null);
                viewholder = new Viewholder();
                viewholder.tv_system_message_time = view.findViewById(R.id.tv_system_message_time);
                viewholder.tv_system_message_type = view.findViewById(R.id.tv_system_message_type);
                viewholder.tv_system_message_title = view.findViewById(R.id.tv_system_message_title);
                viewholder.tv_system_message_content = view.findViewById(R.id.tv_system_message_content);
                view.setTag(viewholder);
            } else {
                viewholder = (Viewholder) view.getTag();
            }
            SystemMessage info = getItem(i);
            viewholder.message = info;
            viewholder.tv_system_message_time.setText(info.time);
            switch (info.type) {
                case 1:
                    viewholder.tv_system_message_type.setText("消息");
                    viewholder.tv_system_message_type.setBackgroundResource(R.drawable.submit_article_type1_shape);
                    break;
                case 2:
                    viewholder.tv_system_message_type.setText("故事");
                    viewholder.tv_system_message_type.setBackgroundResource(R.drawable.submit_article_type2_shape);
                    break;
                case 3:
                    viewholder.tv_system_message_type.setText("旅游");
                    viewholder.tv_system_message_type.setBackgroundResource(R.drawable.submit_article_type3_shape);
                    break;
            }
            viewholder.tv_system_message_title.setText(info.title);
            viewholder.tv_system_message_content.setText(info.content);
            return view;
        }
    }

    public static class Viewholder {
        public TextView tv_system_message_time;
        public TextView tv_system_message_type;
        public TextView tv_system_message_title;
        public TextView tv_system_message_content;
        public SystemMessage message;
    }


    public static class SystemMessage implements  Serializable {
        public int id;
        public int type;
        public String title;
        public String content;
        public String time;

        public SystemMessage(int id, int type, String title, String content, String time) {
            this.id = id;
            this.type = type;
            this.title = title;
            this.content = content;
            this.time = time;
        }
    }
}
