package com.music.maowo.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.anno.Layout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-10-18 0018.
 */
@Layout(R.layout.activity_article_detail_layout)
public class ArticleDetailActivity extends BaseActivity {
    public static final String INFO_TAG = "info_tag";

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_submit_title)
    TextView tv_submit_title;
    @BindView(R.id.tv_article_detail_type)
    TextView tv_article_detail_type;
    @BindView(R.id.tv_article_detail_time)
    TextView tv_article_detail_time;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private SubmitArticleActivity.SubmitArticleInfo info;

    @Override
    protected void initDataAndListener() {
        info = (SubmitArticleActivity.SubmitArticleInfo) getIntent().getSerializableExtra(INFO_TAG);
        tv_submit_title.setText(info.title);
        switch (info.type) {
            case 1:
                tv_article_detail_type.setText("校园");
                break;
            case 2:
                tv_article_detail_type.setText("故事");
                break;
            case 3:
                tv_article_detail_type.setText("旅游");
                break;
        }
        tv_article_detail_time.setText("发布时间:" + info.submitTime);
        tv_content.setText(info.content);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }
}
