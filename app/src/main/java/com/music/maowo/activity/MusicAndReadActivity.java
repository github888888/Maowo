package com.music.maowo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.music.maowo.Constants;
import com.music.maowo.PreferenceConfig;
import com.music.maowo.R;
import com.music.maowo.Utils.SharedPreferencesUtils;
import com.music.maowo.Utils.ToastUtils;
import com.music.maowo.activity.login.LoginActivity;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.ArticleDetail;
import com.music.maowo.bean.CommentInfo;
import com.music.maowo.bean.Music;
import com.music.maowo.bean.OnlineMusic;
import com.music.maowo.fragment.PlayFragment;
import com.music.maowo.manager.OnPlayerEventListener;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.CoverLoader;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.net.response.ArticleDetailResponse;
import com.music.maowo.view.CircleImageView;
import com.music.maowo.view.CustomListView;
import com.music.maowo.view.KeyboardRelativeLayout;
import com.music.maowo.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017-9-3 0003.
 */
@Layout(R.layout.activity_music_and_read_layout)
public class MusicAndReadActivity extends BaseActivity implements OnPlayerEventListener, View.OnClickListener {
    @BindView(R.id.krl_root_view)
    KeyboardRelativeLayout krl_root_view;
    @BindView(R.id.iv_favour)
    ImageView iv_favour;
    @BindView(R.id.iv_praise)
    ImageView iv_praise;
    @BindView(R.id.iv_msg)
    ImageView iv_msg;
    @BindView(R.id.iv_share)
    ImageView iv_share;

    @BindView(R.id.sv_content)
    ObservableScrollView sv_content;
    @BindView(R.id.iv_background)
    ImageView iv_background;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.civ_header)
    CircleImageView civ_header;
    @BindView(R.id.tv_content)
    TextView tv_content;

    @BindView(R.id.v_background)
    View v_background;
    @BindView(R.id.iv_play_bar_cover)
    ImageView iv_play_bar_cover;
    @BindView(R.id.iv_play_bar_play)
    ImageView iv_play_bar_play;
    @BindView(R.id.tv_play_bar_title)
    TextView tv_play_bar_title;
    @BindView(R.id.rl_music_container)
    RelativeLayout rl_music_container;

    @BindView(R.id.lv_content)
    CustomListView lv_content;

    @BindView(R.id.rl_send_message)
    RelativeLayout rl_send_message;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.cb_private_msg)
    CheckBox cb_private_msg;
    @BindView(R.id.tv_send)
    TextView tv_send;

    @BindView(R.id.iv_add_article)
    ImageView iv_add_article;


    private int position;
    private OnlineMusic onlineMusic;
    private int iv_backgroundHeight = 0;
    private int startColor = 0xffffffff;
    private int endColor = 0xff333333;
    private boolean isFavor = false;
    private boolean isPraise = false;
    private List<CommentInfo> commentList;

    private CommentAdapter adapter;

    private boolean isPrivate = false;

    private int articleId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleId = getIntent().getIntExtra("article_id", -1);
        if (articleId <= 0) {
            finish();
            return;
        }

        getPlayService().setOnPlayEventListener(this);
        onChange(getPlayService().getPlayingMusic());
        v_background.setAlpha(0);
        tv_play_bar_title.setTextColor(startColor);
        sv_content.setScrollViewListener(new ObservableScrollView.ScrollViewListener(){
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                float alpha = y  * 1.0f / iv_backgroundHeight >= 1 ? 1.0f : y * 1.0f  / iv_backgroundHeight;
                v_background.setAlpha(alpha);
                tv_play_bar_title.setTextColor(getColorFrom(startColor, endColor, alpha));
            }
        });
        krl_root_view.setOnkbdStateListener(new KeyboardRelativeLayout.onKybdsChangeListener() {
            @Override
            public void onKeyBoardStateChange(int state) {
                if (state == KeyboardRelativeLayout.KEYBOARD_STATE_SHOW) {
                    rl_send_message.setVisibility(View.VISIBLE);
                } else {
                    rl_send_message.setVisibility(View.GONE);
                }
            }
        });

        Observable<ArticleDetailResponse> observable =
                RetrofitManager.getServices().getArticleDetail(articleId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wapper);

        commentList = new ArrayList<>();
        commentList.add(new CommentInfo("http://123.59.214.241:8000/static/images/11.jpg", "author1", "2017-08-09", "你好"));
        commentList.add(new CommentInfo("http://123.59.214.241:8000/static/images/12.jpg", "author2", "2017-08-10", "你好1"));
        commentList.add(new CommentInfo("http://123.59.214.241:8000/static/images/13.jpg", "author3", "2017-08-11", "你好2"));
        commentList.add(new CommentInfo("http://123.59.214.241:8000/static/images/14.jpg", "author4", "2017-08-12", "你好3"));
        adapter = new CommentAdapter();
        lv_content.setAdapter(adapter);
    }

    public int getColorFrom(int startColor, int endColor, float radio) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);

        int red = (int) (redStart + ((redEnd - redStart) * radio + 0.5));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * radio + 0.5));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * radio + 0.5));
        return Color.argb(255, red, greed, blue);
    }

    ObserverWapper wapper = new ObserverWapper<ArticleDetailResponse>() {
        @Override
        public void onNext(ArticleDetailResponse response) {
            if(response == null || response.getData() == null || response.getReasult() != 1) return;
            ArticleDetail detail = response.getData();
            tv_title.setText(detail.getTitle());
            tv_content.setText(detail.getContent());
            Glide.with(getApplicationContext()).load(detail.getAvatar())
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    civ_header.setImageBitmap(resource);
                }
            });
            Glide.with(getApplicationContext())
                    .load(detail.getImage_url())
                    .placeholder(R.mipmap.default_cover)
                    .error(R.mipmap.default_cover)
                    .into(iv_background);

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && iv_backgroundHeight == 0) {
            iv_backgroundHeight = iv_background.getMeasuredHeight();
        }
    }

    @Override
    protected void setListener() {
        iv_play_bar_play.setOnClickListener(this);
        rl_music_container.setOnClickListener(this);
        iv_favour.setOnClickListener(this);
        iv_praise.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_add_article.setOnClickListener(this);
    }

    @Override
    public void onChange(Music music) {
        if (music == null) {
            return;
        }

        Bitmap cover = CoverLoader.getInstance().loadThumbnail(music);
        iv_play_bar_cover.setImageBitmap(cover);
        tv_play_bar_title.setText(music.getTitle() + "-" + music.getArtist());
        iv_play_bar_play.setSelected(getPlayService().isPlaying() || getPlayService().isPreparing());

        if (mPlayFragment != null) {
            mPlayFragment.onChange(music);
        }
    }

    @Override
    public void onPlayerStart() {
        iv_play_bar_play.setSelected(true);
        if (mPlayFragment != null) {
            mPlayFragment.onPlayerStart();
        }
    }

    @Override
    public void onPlayerPause() {
        iv_play_bar_play.setSelected(false);
        if (mPlayFragment != null) {
            mPlayFragment.onPlayerPause();
        }
    }

    @Override
    public void onPublish(int progress) {
        if (mPlayFragment != null) {
            mPlayFragment.onPublish(progress);
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        if (mPlayFragment != null) {
            mPlayFragment.onBufferingUpdate(percent);
        }
    }

    @Override
    public void onTimer(long remain) {

    }

    @Override
    public void onMusicListUpdate() {

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        boolean isLogin = SharedPreferencesUtils.getIsLogin(this, PreferenceConfig.LOGIN, false);
        if (v == iv_play_bar_play) {
            getPlayService().playPause();
        } else if (v == rl_music_container) {
            showPlayingFragment();
        } else if (v == iv_share) {
            if (isLogin) {
                showShare();
            } else {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (v == iv_favour) {
            if (isLogin) {
                isFavor ^= true;
                iv_favour.setSelected(isFavor);
            } else {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (v == iv_praise) {
            if (isLogin) {
                isPraise ^= true;
                iv_praise.setSelected(isPraise);
            } else {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (v == iv_msg) {
            if (isLogin) {
                rl_send_message.setVisibility(View.VISIBLE);
                showKeyboard();
            } else {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (v == tv_send) {
            rl_send_message.setVisibility(View.GONE);
            hideKeyboard();

            isPraise = cb_private_msg.isChecked();
            if (isPraise) {
                sendPrivateMsg(et_content.getText().toString());
            } else {
                sendCommentMessage(et_content.getText().toString());
            }
        } else if (v == iv_add_article) {
            Intent intent = new Intent(this, AddArticleActivity.class);
            startActivity(intent);
        }
    }

    private void sendCommentMessage(final String s) {
        Observable<BaseResult> observable = RetrofitManager.getServices().sendComment(9, 32, s);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<BaseResult>() {
                    @Override
                    public void onNext(BaseResult response) {
                        processDataAndShow(response);
                        commentList.add(new CommentInfo("http://123.59.214.241:8000/static/images/12.jpg", "author2", "2017-08-10", s));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void sendPrivateMsg(String s) {
        Observable<BaseResult> observable = RetrofitManager.getServices().setPrivateMes(Constants.access_token, 9, s);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverWapper<BaseResult>() {
                    @Override
                    public void onNext(BaseResult response) {
                        processDataAndShow(response);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void processDataAndShow(BaseResult response) {
        if (null == response || response.getReasult() != 1) {
            ToastUtils.show(isPrivate ? "私信失败" : "评论失败");
        } else {
            ToastUtils.show(isPrivate ? "私信成功" : "评论成功");
        }
    }

    private void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            et_content.requestFocus();
            imm.showSoftInput(et_content, 0);
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
        }
    }

    private boolean isPlayFragmentShow = false;
    private PlayFragment mPlayFragment;




    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        if (rl_send_message.isShown()) {
            rl_send_message.setVisibility(View.GONE);
            hideKeyboard();
            return;
        }
        super.onBackPressed();
    }

    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new PlayFragment();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }

    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }

    public class CommentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return commentList.size();
        }

        @Override
        public CommentInfo getItem(int i) {
            return commentList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i,View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (null == view) {
                view = View.inflate(getApplicationContext(), R.layout.activity_music_and_read_comment_item_layout, null);
                holder = new ViewHolder();
                holder.civ_header = view.findViewById(R.id.civ_author_show);
                holder.tv_author_name = view.findViewById(R.id.tv_author_name);
                holder.tv_post_time = view.findViewById(R.id.tv_post_time);
                holder.tv_post_content = view.findViewById(R.id.tv_post_content);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            CommentInfo info = getItem(i);
            final CircleImageView temp_civ = holder.civ_header;
            temp_civ.setTag(info);
            Glide.with(getApplicationContext()).load(info.authorUrl)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    CommentInfo temp = (CommentInfo) temp_civ.getTag();
                    if (holder.info !=  temp) return;
                    temp_civ.setImageBitmap(resource);
                }
            });
            holder.tv_author_name.setText(info.authorName);
            holder.tv_post_time.setText(info.postTime);
            holder.tv_post_content.setText(info.postContent);
            holder.info = info;
            return view;
        }
    }

    public class ViewHolder {
        public CircleImageView civ_header;
        public TextView tv_author_name;
        public TextView tv_post_time;
        public TextView tv_post_content;
        public CommentInfo info;
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImageUrl("http://pic55.nipic.com/file/20141208/19462408_171130083000_2.jpg");
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
