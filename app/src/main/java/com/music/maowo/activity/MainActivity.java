package com.music.maowo.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;;

import com.music.maowo.Constants;
import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.adapter.MyFragmentPagerAdapter;
import com.music.maowo.anno.Layout;
import com.music.maowo.other.AppCache;
import com.music.maowo.service.PlayService;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.tv_mainactivity_home)
    TextView tv_mainactivity_home;
    @BindView(R.id.tv_mainactivity_category)
    TextView tv_mainactivity_category;
    @BindView(R.id.tv_mainactivity_mine)
    TextView tv_mainactivity_mine;
    @BindView(R.id.vp_mainactivity_content)
    ViewPager vp_mainactivity_content;

    private MyFragmentPagerAdapter mAdapter;
    private PlayServiceConnection mPlayServiceConnection;


    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            AppCache.setPlayService(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }
    @Override
    protected void initDataAndListener() {
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vp_mainactivity_content.setAdapter(mAdapter);
        vp_mainactivity_content.setCurrentItem(0);
        vp_mainactivity_content.addOnPageChangeListener(this);
        tv_mainactivity_home.setSelected(true);

         bindService();
    }

    @OnClick({R.id.tv_mainactivity_home, R.id.tv_mainactivity_category,  R.id.tv_mainactivity_mine})
    public void onClick(View view) {
        if (view == tv_mainactivity_home) {
            vp_mainactivity_content.setCurrentItem(0);
        } else if (view == tv_mainactivity_category) {
            vp_mainactivity_content.setCurrentItem(1);
        }  else if (view == tv_mainactivity_mine) {
            vp_mainactivity_content.setCurrentItem(3);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vp_mainactivity_content.getCurrentItem()) {
                case 0:
                    resetAllNoSelected();
                    tv_mainactivity_home.setSelected(true);
                    break;
                case 1:
                    resetAllNoSelected();
                    tv_mainactivity_category.setSelected(true);
                    break;
                case 2:
                    resetAllNoSelected();
                    tv_mainactivity_mine.setSelected(true);
                    break;

            }
        }
    }

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            MyApplication.toast(this, "再按一次退出被窝音乐");
            mExitTime = System.currentTimeMillis();
        } else {
            Constants.access_token = "0";
            finish();
            System.exit(0);
        }
    }

    private void resetAllNoSelected() {
        tv_mainactivity_home.setSelected(false);
        tv_mainactivity_category.setSelected(false);
        tv_mainactivity_mine.setSelected(false);
    }
}
