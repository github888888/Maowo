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
@Layout(R.layout.activity_message_detail_layout)
public class SystemInfoDetailActivity extends BaseActivity {
    public static final String INFO_TAG = "info_tag";

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_submit_title)
    TextView tv_submit_title;
    @BindView(R.id.tv_article_detail_time)
    TextView tv_article_detail_time;
    @BindView(R.id.tv_content)
    TextView tv_content;

    private SystemMessageListActivity.SystemMessage info;

    @Override
    protected void initDataAndListener() {
        info = (SystemMessageListActivity.SystemMessage) getIntent().getSerializableExtra(INFO_TAG);
        tv_submit_title.setText(info.title);
        tv_article_detail_time.setText("发布时间:" + info.time);
        tv_content.setText(info.content);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        }
    }
}
