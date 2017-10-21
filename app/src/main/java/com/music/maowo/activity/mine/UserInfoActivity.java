package com.music.maowo.activity.mine;

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

import com.music.maowo.MyApplication;
import com.music.maowo.R;
import com.music.maowo.Utils.DevicesUtils;
import com.music.maowo.Utils.FileUtils;
import com.music.maowo.Utils.Logger;
import com.music.maowo.activity.BaseActivity;
import com.music.maowo.anno.Layout;
import com.music.maowo.view.PersonChooseImgDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhoushaopei on 2017/10/17.
 */

@Layout(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity implements PersonChooseImgDialog.chooseHeadImg {

    @BindView(R.id.nick_name)
    EditText mNickName;
    @BindView(R.id.iv_age)
    EditText mAge;
    @BindView(R.id.man_btn)
    Button mManBtn;
    @BindView(R.id.woman_btn)
    Button mWomanBtn;
    @BindView(R.id.avatar_img)
    ImageView mAvatarImg;

    String gender;
    private File photoFile;
    private PersonChooseImgDialog chooseHead;
    static final int REQUESTCODE_PICK = 0; //从相册选取图片
    static final int REQUESTCODE_CUTTING = 2;// 裁剪图片

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManBtn.setSelected(true);

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
                if (TextUtils.isEmpty(nickName)) {
                    MyApplication.toast(this, "昵称不能为空");
                } else if (TextUtils.isEmpty(age)){
                    MyApplication.toast(this, "年龄不能为空");
                } else {
                    //网络请求
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
