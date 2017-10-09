package com.music.maowo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.music.maowo.anno.Layout;
import com.music.maowo.manager.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-8-22 0022.
 */

public abstract class BaseActivity extends FragmentActivity {
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        initDataAndListener();
        // 透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            dealStatusBar();
        }
    }

    protected void initDataAndListener(){}

    private int getLayoutID() {
        Layout layout = this.getClass().getAnnotation(Layout.class);
        if (null == layout) {
            throw new RuntimeException("请在XXXActivity类的前面用@Layout(id = R.layout.xxxx),便于自动设置资源文件");
        }
        return layout.value();
    }


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void dealStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        // Constants.STATUSBAR_HEIGHT = mTintManager.getConfig().getStatusBarHeight();
    }

}
