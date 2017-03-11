package com.linewow.xhyy.forfunapp.netretrofit;



import com.linewow.xhyy.funlibrary.base.BaseInfo;
import com.linewow.xhyy.forfunapp.entity.BeautyInfo;
import com.linewow.xhyy.forfunapp.entity.NewsDetailEntity;
import com.linewow.xhyy.forfunapp.entity.NewsSummary;
import com.linewow.xhyy.forfunapp.entity.VideoEntity;
import com.linewow.xhyy.forfunapp.entity.VideoInfo;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);


    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetailEntity>> getNewDetail(
            @Header("Cache-Control") String cacheControl,
            @Path("postId") String postId);

    @GET("fenzhongkeji/video/getVideoTypeList.json")
    Observable<VideoEntity<VideoInfo>>getVideoListEntity(
            @Header("Cache-Control") String cacheControl,
            @Query("typeid") String id,
            @Query("page") String page
    );

    @GET("data/福利/{size}/{page}")
    Observable<BaseInfo<BeautyInfo,String>>getBeautyList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page
    );



    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Header("Cache-Control") String cacheControl,
            @Url String photoPath);
    //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
    // baseUrl 需要符合标准，为空、""、或不合法将会报错


}
