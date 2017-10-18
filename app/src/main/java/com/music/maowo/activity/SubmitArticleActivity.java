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

/**
 * Created by Administrator on 2017-10-17 0017.
 */

@Layout(R.layout.activity_mine_common_layout)
public class SubmitArticleActivity extends BaseActivity {
    @BindView(R.id.tv_tttle)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private SubmitArticleAdapter adapter;
    private List<SubmitArticleInfo> list;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        tv_title.setText("提交的文章");
        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        list = new ArrayList<>();
        list.add(new SubmitArticleInfo(1, "title1", "content1", "2017-09-08", true));
        list.add(new SubmitArticleInfo(2, "title2", "content1", "2017-09-07", false));
        list.add(new SubmitArticleInfo(3, "title3", "content1", "2017-07-08", true));
        adapter = new SubmitArticleAdapter();
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gotoArticleDetailActivity(list.get(i));
            }
        });
    }

    private void gotoArticleDetailActivity(SubmitArticleInfo submitArticleInfo) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.INFO_TAG, submitArticleInfo);
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
        public SubmitArticleInfo getItem(int i) {
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
                view = View.inflate(getApplicationContext(), R.layout.activity_submit_article_item_layout, null);
                viewholder = new Viewholder();
                viewholder.tv_submit_type = view.findViewById(R.id.tv_submit_type);
                viewholder.tv_submit_title = view.findViewById(R.id.tv_submit_title);
                viewholder.tv_submit_pass_description = view.findViewById(R.id.tv_submit_pass_description);
                viewholder.tv_submit_time = view.findViewById(R.id.tv_submit_time);
                view.setTag(viewholder);
            } else {
                viewholder = (Viewholder) view.getTag();
            }
            SubmitArticleInfo info = getItem(i);
            switch (info.type) {
                case 1:
                    viewholder.tv_submit_type.setText("校园");
                    viewholder.tv_submit_type.setBackgroundResource(R.drawable.submit_article_type1_shape);
                    break;
                case 2:
                    viewholder.tv_submit_type.setText("故事");
                    viewholder.tv_submit_type.setBackgroundResource(R.drawable.submit_article_type2_shape);
                    break;
                case 3:
                    viewholder.tv_submit_type.setText("旅游");
                    viewholder.tv_submit_type.setBackgroundResource(R.drawable.submit_article_type3_shape);
                    break;
            }
            viewholder.tv_submit_title.setText(info.title);
            viewholder.tv_submit_pass_description.setText(info.isPass ? "已通过" : "未通过");
            viewholder.tv_submit_time.setText("提交时间： " + info.submitTime);
            return view;
        }
    }

    public static class Viewholder {
        public TextView tv_submit_type;
        public TextView tv_submit_title;
        public TextView tv_submit_pass_description;
        public TextView tv_submit_time;

    }

    public static class SubmitArticleInfo implements Serializable{
        public int type;
        public String title;
        public String content;
        public String submitTime;

        public SubmitArticleInfo(int type, String title, String content, String submitTime, boolean isPass) {
            this.type = type;
            this.title = title;
            this.content = content;
            this.submitTime = submitTime;
            this.isPass = isPass;
        }

        public boolean isPass;
    }
}
