package com.music.maowo.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mob.commons.SHARESDK;
import com.music.maowo.Constants;
import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.Utils.Logger;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.activity.MainActivity;
import com.music.maowo.anno.Layout;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.LoginAndRegisterResponse;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
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

    @OnClick({R.id.iv_back, R.id.login_btn, R.id.register_btn, R.id.forgot_password_btn, R.id.delete_btn, R.id.wechat_btn, R.id.sina_btn, R.id.qq_btn})
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
            case R.id.wechat_btn:
                shareSDKLogin(Wechat.NAME);
                break;
            case R.id.sina_btn:
                shareSDKLogin(SinaWeibo.NAME);
                break;
            case R.id.qq_btn:
                shareSDKLogin(QQ.NAME);
                break;
        }
    }

    private void shareSDKLogin(String name) {
        Platform platform = ShareSDK.getPlatform(name);
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }
        platform.setPlatformActionListener(listener);
        platform.authorize();
//        platform.showUser(null);
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

    PlatformActionListener listener = new PlatformActionListener() {

        @Override
        public void onError(Platform arg0, int arg1, Throwable arg2) {
            arg2.printStackTrace();
            Logger.e("LoginActivity:"+arg2.toString());
        }

        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
            //输出所有授权信息
            platform.getDb().exportData();
            if (action == Platform.ACTION_USER_INFOR || action == Platform.ACTION_AUTHORIZING) {
                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                //通过DB获取各种数据
                platDB.getToken();
                platDB.getUserGender();
                platDB.getUserIcon();
                platDB.getUserId();
                platDB.getUserName();
                Message message = Message.obtain();
                message.what = 0;
                handler.sendMessage(message);
                Logger.i("LoginActivity:token" + platDB.getToken() + "-userGender:"+platDB.getUserGender() + "-userIcon:" + platDB.getUserIcon()
                        + "-userId:" + platDB.getUserId() + "-userName:" + platDB.getUserName());
            }
        }

        @Override
        public void onCancel(Platform arg0, int arg1) {
            Logger.e("LoginActivity:Cancel");

        }
    };

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0) {
                MyApplication.toast(LoginActivity.this, "成功");
            }else {
                MyApplication.toast(LoginActivity.this, "失败");
            }
        }

    };

}
