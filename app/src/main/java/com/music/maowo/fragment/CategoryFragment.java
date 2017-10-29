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
import com.music.maowo.bean.CategoryResponse;
import com.music.maowo.bean.TopicSummaryInfo;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.net.response.HomePageResponse;
import com.music.maowo.other.GlideLoader;
import com.music.maowo.view.CustomListView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class CategoryFragment extends Fragment implements OnBannerListener {
    @BindView(R.id.lv_content)
    CustomListView lv_content;
    @BindView(R.id.banner)
    Banner banner;
    private CategoryFragmentAdapter adapter;
    private List<CategoryResponse.DataBean.CateListBean> list;

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
        list = new ArrayList<>();
        getCategoryData();
    }

    private void getCategoryData() {
        Observable<CategoryResponse> observable = RetrofitManager.getServices().getCategoryListInfo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<CategoryResponse>(){
                    @Override
                    public void onNext(CategoryResponse o) {
                        processData(o);
                    }
                });
    }

    private void processData(CategoryResponse response) {
        if (null == response || null == response.getData()) return;
        if (null == response.getData().getRoll_list() || null == response.getData().getCate_list()) return;

        List<String> images = new ArrayList<>();
        for(CategoryResponse.DataBean.RollListBean item :  response.getData().getRoll_list()) {
            images.add(item.getPicture());
        }
        banner.setImages(images).setImageLoader(new GlideLoader()).start();

        list.clear();
        list.addAll(response.getData().getCate_list());
        adapter = new CategoryFragmentAdapter(list, getContext());
        lv_content.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_LONG).show();
    }
}
