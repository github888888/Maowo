package com.music.maowo.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.music.maowo.R;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.Music;
import com.music.maowo.bean.OnlineMusic;
import com.music.maowo.fragment.PlayFragment;
import com.music.maowo.manager.OnPlayerEventListener;
import com.music.maowo.net.CoverLoader;
import com.music.maowo.view.CircleImageView;
import com.music.maowo.view.ObservableScrollView;

import butterknife.BindView;


/**
 * Created by Administrator on 2017-9-3 0003.
 */
@Layout(R.layout.activity_music_and_read_layout)
public class MusicAndReadActivity extends BaseActivity implements OnPlayerEventListener, View.OnClickListener {
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

    private int position;
    private OnlineMusic onlineMusic;
    private int iv_backgroundHeight = 0;
    private int startColor = 0xffffffff;
    private int endColor = 0xff333333;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;

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

        Glide.with(getApplicationContext())
                .load("http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg")
                .placeholder(R.mipmap.default_cover)
                .error(R.mipmap.default_cover)
                .into(iv_background);
        Glide.with(getApplicationContext()).load("http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg")
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                civ_header.setImageBitmap(resource);
            }
        });

        tv_title.setText("你好明天");
        tv_content.setText("原标题：重磅！中国发现超级金属！飞机火箭上天全靠它！一克价值300元！\n" +
                "\n" +
                "　　长期以来航空发动机一直都是我国航空工业中的一个短板。现在，我国部分军用飞机依然使用国外发动机，而在商业航空领域，C-919大型喷气式客机使用的发动机也产自一家美法合资企业。\n" +
                "\n" +
                "　　为了尽快补足这块短板，国家一直在加大航空发动机的研发力度。国务院印发的《中国制造2025》明确提出要“建立发动机自主发展工业体系”，在十三五期间，我国又启动了航空发动机和燃气轮机重大专项。如今，航空工业持续发力，正不断缩小着与国际一流发动机的差距。\n" +
                "\n" +
                "　　航空发动机每秒散热1700多度，唯有“铼”金属不被熔化\n" +
                "\n" +
                "　　在河北廊坊科技园，一款为无人机和商务机而设计的航空发动机正在进行150小时试车，考核发动机在各种状态下技术性能和可靠性及寿命等综合指标。\n" +
                "\n" +
                "　　中国科学院工程热物理所所长朱俊强：150小时做完了，首飞保证就没问题了，可以到不同高度进行试飞了，这个发动机基本定型。\n" +
                "\n" +
                "\n" +
                "　　航空发动机是当今世界上最复杂的、多学科集成的工程机械系统之一，需要在高温、高压、高转速和高载荷的严酷条件下工作，并满足功率大、重量轻、可靠性高、安全性好、寿命长等众多十分苛刻甚至相互矛盾的要求，因此航空发动机被誉为现代工业的“皇冠”。我国虽然已经是国际上为数不多的几个可以独立研制航空发动机的国家之一，但是航空发动机性能不达标，一直都是中国航空业的一块心病。\n" +
                "\n" +
                "　　十三五期间，我国启动了航空发动机和燃气轮机重大专项，航空工业持续发力，不断缩小与国际一流发动机生产企业的差距。\n" +
                "\n" +
                "\n" +
                "　　中国科学院工程热物理所轻型动力实验室实任徐纲：这一款涡扇发动机它的耗油率，它的寿命，这些指标都达到了国际先进水平，国内也是个空白，所有的零件都是自主设计，然后自主生产，尤其是像里面的高温的单晶涡轮叶片，实际上就是可以说发动机里面，加工的难点中的难点。\n" +
                "\n" +
                "\n" +
                "　　单晶叶片处于航空发动机中温度最高、应力最复杂、环境最恶劣的部位，是航空产品第一关键零件，它的铸造工艺直接决定了航空发动机的性能。\n" +
                "\n" +
                "　　在这台1000公斤推力的发动机中心，核心部件就是眼前这60片单晶叶片。发动机将空气进行压缩之后压入燃烧室，在有限的空间内和燃料发生剧烈燃烧，产生猛烈的燃气喷射流，推动这些叶片高速旋转，让看似单薄的零件迸发出惊人的动力，每一片叶片输出的马力都相当于一台2.0排量的SUV汽车。\n" +
                "\n" +
                "　　朱俊强：我们一千公斤级的发动机，它的高压转速都在三万多转接近四万转，大概的切向速度就是每秒种450米左右，温度大概是1720多度。");
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
        if (v == iv_play_bar_play) {
            getPlayService().playPause();
        } else if (v == rl_music_container) {
            showPlayingFragment();
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
}
