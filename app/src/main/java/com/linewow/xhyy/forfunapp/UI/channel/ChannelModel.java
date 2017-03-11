package com.linewow.xhyy.forfunapp.UI.channel;


import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LXR on 2017/1/9.
 */

public class ChannelModel implements ChannelContract.Model {
    @Override
    public Observable<List<ChannelEntity>> start() {
        return Observable.create(new Observable.OnSubscribe<List<ChannelEntity>>() {
            @Override
            public void call(Subscriber<? super List<ChannelEntity>> subscriber) {
                List<ChannelEntity>list=new ArrayList<>();
                ChannelEntity entity1=new ChannelEntity("headline","T1348647909107","头条");
                ChannelEntity entity2=new ChannelEntity("list","T1348649580692","科技");
                ChannelEntity entity3=new ChannelEntity("list","T1348648756099","财经");
                ChannelEntity entity4=new ChannelEntity("list","T1348648141035","军事");
                ChannelEntity entity5=new ChannelEntity("list","T1348649079062","体育");
                list.add(entity1);
                list.add(entity2);
                list.add(entity3);
                list.add(entity4);
                list.add(entity5);
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
