package com.music.maowo.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.Utils.DevicesUtils;
import com.music.maowo.Utils.GlideCacheUtil;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.anno.Layout;
import com.music.maowo.view.SwitchButtonNew;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhoushaopei on 2017/10/18.
 */

@Layout(R.layout.activity_setting)
public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.sleep_mode)
    SwitchButtonNew mSleepMode;
    @BindView(R.id.shake_change)
    SwitchButtonNew mShakeChange;
    @BindView(R.id.iv_cache_size)
    TextView mCacheSize;
    @BindView(R.id.iv_version)
    TextView mVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSleepMode.setOnCheckedChangeListener(this);
        mShakeChange.setOnCheckedChangeListener(this);
        String size = GlideCacheUtil.getInstance().getCacheSize(this);
        String version = DevicesUtils.getVersionName(this);
        mCacheSize.setText(size);
        mVersion.setText(version);
    }

    @OnClick({R.id.iv_back, R.id.iv_feedback, R.id.iv_upgrade, R.id.iv_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_feedback:
                FeedbackActivity.actionInstance(this);
                break;
            case R.id.iv_clear_cache:
                GlideCacheUtil.getInstance().clearImageAllCache(this);
                MyApplication.toast(this, "缓存清理中....请稍候");
                break;
            case R.id.iv_upgrade:

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.sleep_mode:

                break;
            case R.id.shake_change:

                break;
        }
    }
}
