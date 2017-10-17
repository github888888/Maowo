package com.music.maowo.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.music.maowo.anno.Layout;
import com.music.maowo.manager.SystemBarTintManager;
import com.music.maowo.other.AppCache;
import com.music.maowo.service.PlayService;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-8-22 0022.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);
        initDataAndListener();
        // 透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            dealStatusBar();
        }
//        setSystemBarTransparent();
    }

    protected void initDataAndListener(){}

    private int getLayoutID() {
        Layout layout = this.getClass().getAnnotation(Layout.class);
        if (null == layout) {
            throw new RuntimeException("请在XXXActivity类的前面用@Layout(id = R.layout.xxxx),便于自动设置资源文件");
        }
        return layout.value();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setListener();
    }

    protected void setListener() {
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void dealStatusBar() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        mTintManager = new SystemBarTintManager(this);
//        mTintManager.setStatusBarTintEnabled(true);
        // Constants.STATUSBAR_HEIGHT = mTintManager.getConfig().getStatusBarHeight();
    }


    public PlayService getPlayService() {
        PlayService playService = AppCache.getPlayService();
        if (playService == null) {
            throw new NullPointerException("play service is null");
        }
        return playService;
    }

    protected boolean checkServiceAlive() {
        if (AppCache.getPlayService() == null) {
            AppCache.clearStack();
            return false;
        }
        return true;
    }

    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // KITKAT解决方案
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showSoftKeyboard(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 200L);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
