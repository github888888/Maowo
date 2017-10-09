package com.music.maowo.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.activity.MainActivity;
import com.music.maowo.anno.Layout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhoushaopei on 2017/10/6.
 */

@Layout(R.layout.activity_nickname)
public class NicknameActivity extends BaseActivity {

    @BindView(R.id.man_btn)
    RadioButton mManBtn;
    @BindView(R.id.woman_btn)
    RadioButton mWomanBtn;
    @BindView(R.id.ok_btn)
    Button mOkBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_nickname);
    }

    @OnClick({R.id.ok_btn, R.id.over_read_btn, R.id.user_agreement_btn})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ok_btn:
                MyApplication.toast(this, "完善资料成功");
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.over_read_btn:

                break;
            case R.id.user_agreement_btn:

                break;
        }
    }
}
