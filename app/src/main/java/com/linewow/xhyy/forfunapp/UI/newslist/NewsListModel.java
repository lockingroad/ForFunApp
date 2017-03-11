package com.linewow.xhyy.forfunapp.UI.newslist;

import com.linewow.xhyy.forfunapp.entity.NewsSummary;
import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;
import com.linewow.xhyy.forfunapp.netretrofit.Api;
import com.linewow.xhyy.forfunapp.netretrofit.HostType;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;



/**
 * Created by LXR on 2017/1/17.
 */

public class NewsListModel implements NewsListContract.Model {
    @Override
    public Observable<List<NewsSummary>> getNewsSummary(int page, final ChannelEntity entity) {

        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewsList(Api.getCacheControl(),entity.getType(),entity.getId(),page*5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Map<String, List<NewsSummary>>, List<NewsSummary>>() {
                    @Override
                    public List<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        return stringListMap.get(entity.getId());
                    }
                });
    }
}
