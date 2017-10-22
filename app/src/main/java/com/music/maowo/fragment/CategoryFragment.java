package com.music.maowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.music.maowo.Constants;
import com.music.maowo.R;
import com.music.maowo.adapter.CategoryFragmentAdapter;
import com.music.maowo.bean.TopicSummaryInfo;
import com.music.maowo.other.GlideLoader;
import com.music.maowo.view.CustomListView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class CategoryFragment extends Fragment implements OnBannerListener {
    @BindView(R.id.lv_content)
    CustomListView lv_content;
    @BindView(R.id.banner)
    Banner banner;

    private List<String> images;
    private CategoryFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Constants.screenWidth, Constants.screenWidth / 2);
        banner.setLayoutParams(params);

        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        banner.setOnBannerListener(this);
        // TODO 从服务端获取数据
        banner.setImages(images).setImageLoader(new GlideLoader()).start();

        List<TopicSummaryInfo> list = new ArrayList<>();
        list.add(new TopicSummaryInfo("nusicUrl", images.get(0), "title1", "description1", 1, 2, true));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(1), "title2", "description2", 2, 4, false));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(2), "title3", "description3", 3, 6, false));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(3), "title4", "description4", 4, 8, true));
        list.add(new TopicSummaryInfo("nusicUrl", images.get(4), "title5", "description5", 5, 10, false));
        adapter = new CategoryFragmentAdapter(list, getContext());
        lv_content.setAdapter(adapter);
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_LONG).show();
    }
}
