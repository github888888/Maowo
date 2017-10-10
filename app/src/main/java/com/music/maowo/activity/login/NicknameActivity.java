package com.music.maowo.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.music.maowo.Constants;
import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.activity.MainActivity;
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
 * Created by zhoushaopei on 2017/10/6.
 */

@Layout(R.layout.activity_nickname)
public class NicknameActivity extends BaseActivity {

    @BindView(R.id.man_btn)
    ImageButton mManBtn;
    @BindView(R.id.woman_btn)
    ImageButton mWomanBtn;
    @BindView(R.id.ok_btn)
    Button mOkBtn;

    String genderStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.iv_back, R.id.ok_btn, R.id.man_btn, R.id.woman_btn, R.id.over_read_btn, R.id.user_agreement_btn})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ok_btn:
                if (TextUtils.isEmpty(genderStr)) {
                    MyApplication.toast(this, "请选择昵称");
                } else {
                    Observable<BaseResult<LoginAndRegisterResponse>> observable =
                            RetrofitManager.getServices().bindNickname(genderStr, Constants.access_token);
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(wapper);
                }
                break;
            case R.id.man_btn:
                genderStr = "帅哥";
                mManBtn.setSelected(true);
                mWomanBtn.setSelected(false);
                break;
            case R.id.woman_btn:
                genderStr = "美女";
                mManBtn.setSelected(false);
                mWomanBtn.setSelected(true);
                break;
            case R.id.over_read_btn:

                break;
            case R.id.user_agreement_btn:

                break;
        }
    }


    ObserverWapper wapper = new ObserverWapper<BaseResult<LoginAndRegisterResponse>>() {
        @Override
        public void onNext(BaseResult<LoginAndRegisterResponse> loginAndRegisterResponseBaseResult) {
            MyApplication.toast(NicknameActivity.this, "result = " + loginAndRegisterResponseBaseResult.data.token);
            if ((loginAndRegisterResponseBaseResult == null || loginAndRegisterResponseBaseResult.data == null)) return;
            int result = loginAndRegisterResponseBaseResult.getReasult();
            MyApplication.toast(NicknameActivity.this, result == 1 ? "完善资料成功" : "完善资料失败");
            if (result == 1) {
                Intent intent = new Intent(NicknameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
