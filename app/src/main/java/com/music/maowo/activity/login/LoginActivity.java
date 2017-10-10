package com.music.maowo.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.music.maowo.Constants;
import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.anno.Layout;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.LoginAndRegisterResponse;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhoushaopei on 2017/10/5.
 */

@Layout(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.mobile_ext)
    EditText mMobileExt;
    @BindView(R.id.delete_btn)
    ImageButton mDeleteBtn;
    @BindView(R.id.password_ext)
    EditText mPasswordExt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.register_btn)
    Button mRegisterBtn;
    @BindView(R.id.forgot_password_btn)
    Button mForgotPasswordBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @OnClick({R.id.iv_back, R.id.login_btn, R.id.register_btn, R.id.forgot_password_btn, R.id.delete_btn})
    public void onClick(View view){
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.login_btn:
                String mobileStr = mMobileExt.getText().toString();
                String passwordStr = mPasswordExt.getText().toString();
                if (TextUtils.isEmpty(mobileStr) || TextUtils.isEmpty(passwordStr)) {
                    MyApplication.toast(this, "用户名或密码不能为空");
                } else {
                    Observable<BaseResult<LoginAndRegisterResponse>> observable =
                            RetrofitManager.getServices().login(mobileStr, passwordStr);
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(wapper);
                }
                break;
            case R.id.register_btn:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forgot_password_btn:
                intent = new Intent(this, UpdatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_btn:
                mMobileExt.setText("");
                break;
        }
    }

    ObserverWapper wapper = new ObserverWapper<BaseResult<LoginAndRegisterResponse>>() {
        @Override
        public void onNext(BaseResult<LoginAndRegisterResponse> loginAndRegisterResponseBaseResult) {
            MyApplication.toast(LoginActivity.this, "result = " + loginAndRegisterResponseBaseResult.data.token);
            if ((loginAndRegisterResponseBaseResult == null || loginAndRegisterResponseBaseResult.data == null || loginAndRegisterResponseBaseResult.data.token == 0)) return;
            int result = loginAndRegisterResponseBaseResult.getReasult();
            Constants.access_token = String.valueOf(loginAndRegisterResponseBaseResult.data.token);
            MyApplication.toast(LoginActivity.this, result == 1 ? "登录成功":"登录失败");
            if (result == 1) {
                Intent intent = new Intent(LoginActivity.this, NicknameActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    };

}
