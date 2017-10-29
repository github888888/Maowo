package com.music.maowo.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.music.maowo.Constants;
import com.music.maowo.MyApplication;
import com.music.maowo.PreferenceConfig;
import com.music.maowo.R;
import com.music.maowo.Utils.DevicesUtils;
import com.music.maowo.Utils.FileUtils;
import com.music.maowo.Utils.Logger;
import com.music.maowo.Utils.SharedPreferencesUtils;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.activity.login.LoginActivity;
import com.music.maowo.activity.login.NicknameActivity;
import com.music.maowo.anno.Layout;
import com.music.maowo.bean.UserInfo;
import com.music.maowo.net.BaseResult;
import com.music.maowo.net.ObserverWapper;
import com.music.maowo.net.RetrofitManager;
import com.music.maowo.net.response.LoginAndRegisterResponse;
import com.music.maowo.net.response.UserInfoResponse;
import com.music.maowo.view.CircleImageView;
import com.music.maowo.view.PersonChooseImgDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhoushaopei on 2017/10/17.
 */

@Layout(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity implements PersonChooseImgDialog.chooseHeadImg {

    private static final String INFO = "info";

    @BindView(R.id.nick_name)
    EditText mNickName;
    @BindView(R.id.iv_age)
    EditText mAge;
    @BindView(R.id.man_btn)
    Button mManBtn;
    @BindView(R.id.woman_btn)
    Button mWomanBtn;
    @BindView(R.id.avatar_img)
    CircleImageView mAvatarImg;

    String gender = "";
    private File photoFile;
    private PersonChooseImgDialog chooseHead;
    static final int REQUESTCODE_PICK = 0; //从相册选取图片
    static final int REQUESTCODE_CUTTING = 2;// 裁剪图片

    public static void actionInstance(Activity activity, UserInfo info) {
        Intent intent = new Intent();
        intent.putExtra(INFO, info);
        intent.setClass(activity, UserInfoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserInfo info = (UserInfo) getIntent().getSerializableExtra(INFO);
        if (info == null) {
            Observable<UserInfoResponse> observable =
                    RetrofitManager.getServices().getUserInfo(Constants.access_token);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(wapperGetUser);
        } else {
            initData(info);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_save, R.id.man_btn, R.id.woman_btn, R.id.avatar_rl})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_save:
                String nickName = mNickName.getText().toString().trim();
                String age = mAge.getText().toString().trim();
                String avatar = "";
                if (TextUtils.isEmpty(nickName)) {
                    MyApplication.toast(this, "昵称不能为空");
                } else if (TextUtils.isEmpty(age)){
                    MyApplication.toast(this, "年龄不能为空");
                } else if (TextUtils.isEmpty(gender)) {
                    MyApplication.toast(this, "请选填性别");
                } else {
                    Observable<BaseResult> observable =
                            RetrofitManager.getServices().submitUserInfo(avatar, gender, nickName, age, Constants.access_token);
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(wapperSubmit);
                }
                break;
            case R.id.man_btn:
                gender = getString(R.string.mine_user_info_man);
                mManBtn.setSelected(true);
                mWomanBtn.setSelected(false);
                break;
            case R.id.woman_btn:
                gender = getString(R.string.mine_user_info_woman);
                mManBtn.setSelected(false);
                mWomanBtn.setSelected(true);
                break;
            case R.id.avatar_rl:
                chooseHead = new PersonChooseImgDialog(this, R.style.load_dialog,false);
                Window window = chooseHead.getWindow();
                window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                window.setWindowAnimations(R.style.dialogAnim_style);  //添加动画
                window.setBackgroundDrawableResource(R.drawable.custom_bg);
                chooseHead.show();
                chooseHead.setOnChooseListener(this);
                break;
        }
    }

    private void initData(UserInfo info) {
        if (info == null) return;
        mNickName.setText(info.getNickname());
        mAge.setText(info.getAge());
        mManBtn.setSelected(gender.equals("男") ? true : false);
        mManBtn.setSelected(gender.equals("男") ? false : true);
        gender = gender.equals("男") ? "男" : "女";
                Glide.with(UserInfoActivity.this)
                .load("http://image.tianjimedia.com/uploadImages/2013/235/56Y682R36Y6X.jpg")
                .error(R.mipmap.avator_img)
                .into(mAvatarImg);

    }

    ObserverWapper wapperGetUser = new ObserverWapper<UserInfoResponse>() {
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onNext(UserInfoResponse response) {
            if(response == null || response.getInfo() == null || response.getReasult() != 1) return;
            UserInfo info = response.getInfo();

            initData(info);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }
    };

    ObserverWapper wapperSubmit = new ObserverWapper<BaseResult>() {
        @Override
        public void onNext(BaseResult result) {
            if (result == null) return;
            if (result.getReasult() == 1) {
                MyApplication.toast(UserInfoActivity.this, "成功");
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data == null) return;
        switch (requestCode) {
            case REQUESTCODE_PICK:
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                break;
            case REQUESTCODE_CUTTING:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            setPicToView(data);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }, 500);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        int px = DevicesUtils.dip2px(this, 90);
        this.photoFile = FileUtils.createAvatarEmptyFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);//宽高的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", px);//裁剪的图片的宽高
        intent.putExtra("outputY", px);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUESTCODE_CUTTING);

        //此处注释掉的部分是针对android 4.4路径修改的一个测试
        //有兴趣的读者可以自己调试看看
    }

    /**
     * 设置修剪好的图片
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) throws FileNotFoundException {
        Logger.i("setPicToView +++++++++1");
        Bundle extras = picdata.getExtras();
        Bitmap photo = null;
        if (extras != null && extras.getParcelable("data") != null) {
            photo = extras.getParcelable("data");
        } else if (picdata.getData() != null) {
            Uri data = picdata.getData();
            InputStream inputStream = this.getContentResolver().openInputStream(data);
            photo = BitmapFactory.decodeStream(inputStream);
        } else if (photoFile != null && photoFile.exists()) {
            Logger.i("setPicToView +++++++++2");
            photo = FileUtils.decodeFileBitmap(photoFile);
        }
        if (photo == null) return;
        final Drawable drawable = new BitmapDrawable(null, photo);
        final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        final Bitmap bitmapSmall = FileUtils.compressImage(bitmap, 20, 700);//kb
        File cropPhoto = FileUtils.saveAvatarBitmap(bitmapSmall);
        uploadAvatar(cropPhoto, bitmapSmall);
    }


    private void uploadAvatar(File cropPhoto, Bitmap bitmapSmall) {
        MyApplication.toast(this, "1111");
        mAvatarImg.setImageBitmap(bitmapSmall);
    }


    @Override
    public void chooseImg() {
        chooseHead.dismiss();
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
    }

    @Override
    public void takePhoto() {

    }
}
