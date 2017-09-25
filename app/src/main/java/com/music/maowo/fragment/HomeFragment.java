package com.music.maowo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.music.maowo.Constants;
import com.music.maowo.R;
import com.music.maowo.adapter.HomeFragmentAdapter;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.LoginAndRegisterResponse;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.other.GlideLoader;
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
public class HomeFragment extends Fragment implements OnBannerListener, View.OnClickListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv_content)
    ListView lv_content;

    private View header;
    private ImageView iv_sort;
    private ImageView iv_article;

    private List<String> images;
    private HomeFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        header = inflater.inflate(R.layout.fragment_home_sort_and_article_layout, null);
        iv_sort = header.findViewById(R.id.iv_sort);
        iv_article = header.findViewById(R.id.iv_artical);
        iv_sort.setOnClickListener(this);
        iv_article.setOnClickListener(this);
        init();
        return view;
    }

    private void init() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Constants.screenWidth, Constants.screenWidth / 2);
        banner.setLayoutParams(params);

        lv_content.addHeaderView(header);

        images = Arrays.asList(getResources().getStringArray(R.array.urls));
        banner.setOnBannerListener(this);
        // TODO 从服务端获取数据
        banner.setImages(images).setImageLoader(new GlideLoader()).start();
        Glide.with(getContext()).load(images.get(0)).into(iv_sort);
        Glide.with(getContext()).load(images.get(1)).into(iv_article);

        List<String> list = new ArrayList<>();
        list.add("你好1");
        list.add("你好2");
        list.add("你好3");
        list.add("你好4");
        list.add("你好1");
        list.add("你好2");
        list.add("你好3");
        list.add("你好4");
        list.add("你好1");
        list.add("你好2");
        list.add("你好3");
        list.add("你好4");
        list.add("你好1");
        list.add("你好2");
        list.add("你好3");
        list.add("你好4");
        adapter = new HomeFragmentAdapter(list, getContext());
        lv_content.setAdapter(adapter);
    }

    @Override
    public void OnBannerClick(int position) {
        Observable<BaseResult<LoginAndRegisterResponse>> observable =
                RetrofitManager.getServices().login("chenlong5", "1234556");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<BaseResult<LoginAndRegisterResponse>>() {
                    @Override
                    public void onNext(BaseResult<LoginAndRegisterResponse> loginAndRegisterResponseBaseResult) {
                        Toast.makeText(getContext(), "result = " + loginAndRegisterResponseBaseResult.data.token, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == iv_sort) {
            Toast.makeText(getContext(), "iv_sort", Toast.LENGTH_LONG).show();
        } else if (view == iv_article) {
            Toast.makeText(getContext(), "iv_article", Toast.LENGTH_LONG).show();
        }
    }
}
