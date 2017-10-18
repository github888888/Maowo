package com.music.maowo.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.anno.Layout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhoushaopei on 2017/10/18.
 */

@Layout(R.layout.activity_feedback)
public class FeedbackActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.feed_content)
    EditText mFeedContent;
    @BindView(R.id.feed_contact)
    EditText mFeedContact;
    @BindView(R.id.textViewNum)
    TextView mContentNum;

    public static void actionInstance(Activity activity) {
        Intent intent = new Intent(activity, FeedbackActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeedContent.addTextChangedListener(this);
    }

    @OnClick({R.id.iv_back, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.submit_btn:
                String content = mFeedContent.getText().toString().trim();
                String contact = mFeedContact.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    MyApplication.toast(this, R.string.mine_feedback_content_tip);
                } else if (TextUtils.isEmpty(contact)) {
                    MyApplication.toast(this, R.string.mine_feedback_contact_tip);
                } else {

                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        int length = s.length();
        if (length <= 300) {
            String feed = length + "/300";
            mContentNum.setText(feed);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
