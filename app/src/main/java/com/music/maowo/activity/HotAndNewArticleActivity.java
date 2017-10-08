package com.music.maowo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.adapter.HotAndNewArticelAdapter;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.TopicSummaryInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-10-8 0008.
 */

@Layout(R.layout.activity_hot_and_new_layotu)
public class HotAndNewArticleActivity extends BaseActivity {
    public static final String TYPE_SELECTED = "type_selected";
    public static final int TYPE_HOT = 1; // 热门文章
    public static final int TYPE_NEW = 2; // 最新文章

    @BindView(R.id.tv_tttle)
    TextView tv_tttle;
    @BindView(R.id.tv_indicator)
    TextView tv_indicator;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private int currentType;
    private HotAndNewArticelAdapter adapter;
    // test
    private List<String> images;
    @Override
    protected void initDataAndListener() {
        Intent intent = getIntent();
        currentType = intent.getIntExtra(TYPE_SELECTED, TYPE_HOT);
        tv_tttle.setText(currentType == TYPE_HOT ? "热门排行榜" : "最新文章");
        tv_indicator.setText("文章分类");

        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        List<TopicSummaryInfo> list = new ArrayList<>();
        list.add(new TopicSummaryInfo("nusicUrl", images.get(0), "title1", "description1", 1, 2, "author1", images.get(4), true));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(1), "title2", "description2", 2, 4,  "author2", images.get(3),false));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(2), "title3", "description3", 3, 6,  "author3", images.get(2),false));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(3), "title4", "description4", 4, 8,  "author4", images.get(1),true));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(4), "title5", "description5", 5, 10,  "author5", images.get(0),false));
        adapter = new HotAndNewArticelAdapter(list ,this);
        lv_content.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back,})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }
}
