package com.music.maowo.activity.login;

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

@Layout(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.mobile_ext)
    EditText mMobileExt;
    @BindView(R.id.password_ext)
    EditText mPasswordExt;
    @BindView(R.id.password_again_ext)
    EditText mPasswordAgainExt;
    @BindView(R.id.register_btn)
    Button mRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @OnClick({R.id.register_btn})
    public void onClick(View view) {
        if (view.getId() == R.id.register_btn) {
            String mobileStr = mMobileExt.getText().toString();
            String mPasswordStr = mPasswordExt.getText().toString();
            String mPasswordAgainStr = mPasswordAgainExt.getText().toString();
            if (TextUtils.isEmpty(mobileStr)) {
                MyApplication.toast(this, "手机号不能为空");
            } else if (!mPasswordStr.endsWith(mPasswordAgainStr)) {
                MyApplication.toast(this, "两次输入密码不一致");
            } else {
                Observable<BaseResult<LoginAndRegisterResponse>> observable =
                        RetrofitManager.getServices().register(mobileStr, mPasswordStr);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(wapper);
            }
        }
    }

    ObserverWapper wapper = new ObserverWapper<BaseResult<LoginAndRegisterResponse>>() {
        @Override
        public void onNext(BaseResult<LoginAndRegisterResponse> loginAndRegisterResponseBaseResult) {
            MyApplication.toast(RegisterActivity.this, "result = " + loginAndRegisterResponseBaseResult.data.token);
            if ((loginAndRegisterResponseBaseResult == null || loginAndRegisterResponseBaseResult.data == null)) return;
            int result = loginAndRegisterResponseBaseResult.getReasult();
            MyApplication.toast(RegisterActivity.this, result == 1 ? "注册成功" : "注册失败");
            if (result == 1) {
                MyApplication.toast(RegisterActivity.this, "请登录");
                finish();
            }
        }
    };
}
