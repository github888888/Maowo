package com.music.maowo.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.music.maowo.R;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.anno.Layout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhoushaopei on 2017/10/5.
 */

@Layout(R.layout.update_password_activity)
public class UpdatePasswordActivity extends BaseActivity {

    @BindView(R.id.auth_code_ext)
    EditText mAuthCodeExt;
    @BindView(R.id.password_ext)
    EditText mPasswordExt;
    @BindView(R.id.password_again_ext)
    EditText mPasswordAgainExt;
    @BindView(R.id.ok_btn)
    Button mOkBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @OnClick({R.id.ok_btn})
    public void onClick(View view) {
        if (view.getId() == R.id.ok_btn) {

        }
    }
}
