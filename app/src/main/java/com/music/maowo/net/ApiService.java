package com.music.maowo.net;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;

/**
 * Created by Administrator on 2017-9-16 0016.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("zhuce")
    Observable<BaseResult<LoginAndRegisterResponse>> register(@Field("username") String username, @Field("password1") String password);

    @FormUrlEncoded
    @POST("login")
    Observable<BaseResult<LoginAndRegisterResponse>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("userupdatepass")
    Observable<BaseResult<LoginAndRegisterResponse>> updatePassword(@Field("old_password") String password
                ,  @Field("new_password") String newPassword, @Field("token") int token);

    @FormUrlEncoded
    @POST("userbindnick")
    Observable<BaseResult<LoginAndRegisterResponse>> bindNickname(@Field("nick_name") String nickName, @Field("token") int token);

    @POST("home")
    Observable<HomePageResponse> getHomePageInfo();

    @FormUrlEncoded
    @POST("private_msg")
    Observable<BaseResult> setPrivateMes(@Field("active_id") int active_id, @Field("passive_id") int passive_id, @Field("message") String message);
}
