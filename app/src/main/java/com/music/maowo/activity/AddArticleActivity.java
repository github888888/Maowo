package com.music.maowo.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.maowo.R;
import com.music.maowo.Utils.ToastUtils;
import com.music.maowo.anno.Layout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-10-28 0028.
 */
@Layout(R.layout.activity_add_article_layout)
public class AddArticleActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @Override
    protected void initDataAndListener() {
    }

    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onClick(View view) {
        if (view == iv_back) {
            finish();
        } else if (view == tv_submit) {
            ToastUtils.show("submit");
        }
    }
}
