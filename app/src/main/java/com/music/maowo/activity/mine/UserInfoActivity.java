package com.music.maowo.activity.mine;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private PersonChooseImgDialog chooseHead;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    String filePath;


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
//        Glide.with(UserInfoActivity.this)
//        .load("http://image.tianjimedia.com/uploadImages/2013/235/56Y682R36Y6X.jpg")
//        .error(R.mipmap.avator_img)
//        .into(mAvatarImg);

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
    public void chooseImg() {
        chooseHead.dismiss();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTOZOOM);
    }

    @Override
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "testcapture.jpg")));
        Logger.i("=============" + Environment.getExternalStorageDirectory());
        startActivityForResult(intent, PHOTOHRAPH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver resolver = this.getContentResolver();

        if (resultCode == NONE||data == null)
            return;

        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/testcapture.jpg");
            Logger.i("------------------------" + picture.getPath());
            startPhotoZoom(Uri.fromFile(picture));
        }
        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            try {
                // 获得图片的uri
                Uri originalUri = data.getData();
                startPhotoZoom(originalUri);
                // 将图片内容解析成字节数组
//                byte[] mContent = readStream(resolver.openInputStream(Uri
//                        .parse(originalUri.toString())));
//                // 将字节数组转换为BitmapDrawable对象
//                Bitmap myBitmap = getPicFromBytes(mContent, null);
//                BitmapDrawable bd = new BitmapDrawable(myBitmap);
//                //按比例缩放图片
//              Drawable d = zoomDrawable(bd, 100, 100, true);
//                //不按比例缩放图片，指定大小
////                Drawable d = zoomDrawable(bd, 100, 100, false);
//                // //把得到的图片绑定在控件上显示
////                mAvatarImg.setImageDrawable(d);

            } catch (Exception e) {
                Logger.i("相册异常" + e.getMessage());
            }
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                filePath = FileUtils.saveFile(this, "photo.jpg", photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 - // 100)压缩文件
                mAvatarImg.setImageBitmap(photo);

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }
    private Drawable zoomDrawable(Drawable drawable, int w, int h, Boolean scale) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth;
        float scaleHeight;
        if (scale == true) {
            // 如果要保持宽高比，那说明高度跟宽度的缩放比例都是相同的
            scaleWidth = ((float) w / width);
            scaleHeight = ((float) w / width);
        } else {
            // 如果不保持缩放比，那就根据指定的宽高度进行缩放
            scaleWidth = ((float) w / width);
            scaleHeight = ((float) h / height);
        }
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        try {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
        } catch (Exception e) {
            // TODO: handle exception
            Logger.i("error:"+e.getMessage());
        }

        return bitmap;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }

    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void uploadFile(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        String descriptionString = "hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        Call<ResponseBody> call = RetrofitManager.getServices().uploadAvatar(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Logger.i("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.e("Upload error:", t.getMessage());
            }
        });

    }

}
