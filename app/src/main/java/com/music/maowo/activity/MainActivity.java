package com.music.maowo.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;;

import com.music.maowo.R;
import com.music.maowo.adapter.MyFragmentPagerAdapter;
import com.music.maowo.anno.Layout;

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

    @Override
    protected void initDataAndListener() {
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vp_mainactivity_content.setAdapter(mAdapter);
        vp_mainactivity_content.setCurrentItem(0);
        vp_mainactivity_content.addOnPageChangeListener(this);
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

    private void resetAllNoSelected() {
        tv_mainactivity_home.setSelected(false);
        tv_mainactivity_category.setSelected(false);
        tv_mainactivity_mine.setSelected(false);
    }
}
