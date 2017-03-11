package com.linewow.xhyy.forfunapp.UI.beauty;

import com.linewow.xhyy.funlibrary.base.BaseInfo;
import com.linewow.xhyy.forfunapp.entity.BeautyInfo;
import com.linewow.xhyy.forfunapp.netretrofit.Api;
import com.linewow.xhyy.forfunapp.netretrofit.HostType;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LXR on 2017/1/23.
 */

public class BeautyModel implements BeautyContract.Model {
    @Override
    public Observable<List<BaseInfo<BeautyInfo, String>>> getList(String... params) {
        return null;
    }

    @Override
    public Observable<BaseInfo<BeautyInfo, String>> getEntity(String... params) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO).getBeautyList(Api.getCacheControl(),Integer.parseInt(params[0]),Integer.parseInt(params[1]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
