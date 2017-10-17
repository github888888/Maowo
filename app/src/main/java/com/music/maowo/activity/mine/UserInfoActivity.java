package com.music.maowo.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.anno.Layout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhoushaopei on 2017/10/17.
 */

@Layout(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.nick_name)
    EditText mNickName;
    @BindView(R.id.iv_age)
    EditText mAge;
    @BindView(R.id.man_btn)
    Button mManBtn;
    @BindView(R.id.woman_btn)
    Button mWomanBtn;

    String gender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManBtn.setSelected(true);

    }

    @OnClick({R.id.iv_back, R.id.iv_save, R.id.man_btn, R.id.woman_btn})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_save:
                String nickName = mNickName.getText().toString().trim();
                String age = mAge.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)) {
                    MyApplication.toast(this, "昵称不能为空");
                } else if (TextUtils.isEmpty(age)){
                    MyApplication.toast(this, "年龄不能为空");
                } else {
                    //网络请求
                }
                break;
            case R.id.man_btn:
                gender = "男";
                mManBtn.setSelected(true);
                mWomanBtn.setSelected(false);
                break;
            case R.id.woman_btn:
                gender = "女";
                mManBtn.setSelected(false);
                mWomanBtn.setSelected(true);
                break;
        }
    }

}
