package com.linewow.xhyy.forfunapp.UI.channel;

import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by LXR on 2017/1/17.
 */

public class ChannelPresenter extends ChannelContract.Presenter {


    @Override
    protected void start() {
        model.start().subscribe(new Action1<List<ChannelEntity>>() {
            @Override
            public void call(List<ChannelEntity> list) {
                view.start(list);
            }
        });
    }
}
