package com.music.maowo.net;

import com.music.maowo.bean.SetListResponse;
import com.music.maowo.net.response.ArticleDetailResponse;
import com.music.maowo.net.response.HomePageResponse;
import com.music.maowo.net.response.LoginAndRegisterResponse;
import com.music.maowo.net.response.SubmitArticleResponse;
import com.music.maowo.net.response.UserInfoResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

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

    @FormUrlEncoded
    @POST("comment_msg")
    Observable<BaseResult> sendComment(@Field("token") int token, @Field("article_id") int article_id, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("comment_msg")
    Observable<BaseResult> private_roll(@Field("token") int token);

    @FormUrlEncoded
    @POST("user_add_file")
    Observable<BaseResult> submitArticle(@Field("file_name") String fileName, @Field("file_body") String fileBody, @Field("file_type") String fileType
            , @Field("subject_music") String subjectMusic, @Field("token") int token);

    //文章详情
    @FormUrlEncoded
    @POST("file")
    Observable<ArticleDetailResponse> getArticleDetail(@Field("article_id") int articleid);//article = 32

    @FormUrlEncoded
    @POST("set_article_list")
    Observable<SetListResponse> getSetArticleList(@Field("set_id") int set_id);;

    //提交个人信息
    @FormUrlEncoded
    @POST("submit_my_information")
    Observable<BaseResult> submitUserInfo(@Field("avater") String avatar, @Field("gender") String gender
            , @Field("nickname") String nickName, @Field("age") String age, @Field("token") int token);

    //获取个人信息
    @FormUrlEncoded
    @POST("view_my_information")
    Observable<UserInfoResponse> getUserInfo(@Field("token") int token);

    //获取提交的文章
    @FormUrlEncoded
    @POST("get_article_submit")
    Observable<SubmitArticleResponse> getArticleSubmit(@Field("token") int token);

    @FormUrlEncoded
    @POST("get_article_submit")
    String articleSubmit(@Field("token") int token);
}
