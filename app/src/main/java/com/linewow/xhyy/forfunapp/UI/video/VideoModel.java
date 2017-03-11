package com.linewow.xhyy.forfunapp.UI.video;

import com.linewow.xhyy.forfunapp.entity.VideoEntity;
import com.linewow.xhyy.forfunapp.entity.VideoInfo;
import com.linewow.xhyy.forfunapp.netretrofit.Api;
import com.linewow.xhyy.forfunapp.netretrofit.HostType;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LXR on 2017/1/25.
 */

public class VideoModel implements VideoContract.Model {
    @Override
    public Observable<List<VideoEntity<VideoInfo>>> getList(String... params) {
        return null;
    }

    @Override
    public Observable<VideoEntity<VideoInfo>> getEntity(String... params) {
        return Api.getDefault(HostType.VIDEO).getVideoListEntity(Api.getCacheControl(),params[0],params[1])
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
