package com.linewow.xhyy.forfunapp.UI.newslist;

import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.forfunapp.entity.NewsSummary;
import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by LXR on 2017/1/17.
 */

public class NewsListPresenter extends NewsListContract.Presenter {
    private int page;
    private boolean loadFlag;
    private ChannelEntity entity;
    @Override
    void refresh() {
        page=0;
        loadFlag=true;
        model.getNewsSummary(page,entity).subscribe(new Action1<List<NewsSummary>>() {
            @Override
            public void call(List<NewsSummary> list) {
                view.refresh(list);
                if(list.size()<20){
                    loadFlag=false;
                    view.loadComplete();
                }
            }
        });
    }

    @Override
    void load() {
        if(loadFlag){
            return;
        }
        page++;
        model.getNewsSummary(page,entity).subscribe(new Action1<List<NewsSummary>>() {
            @Override
            public void call(List<NewsSummary> list) {
                view.load(list);
                if(list.size()<20){
                    loadFlag=false;
                    view.loadComplete();
                }
            }
        });

    }


    @Override
    protected void start() {
        page=0;
        loadFlag=true;
        model.getNewsSummary(page,entity).subscribe(new Action1<List<NewsSummary>>() {
            @Override
            public void call(List<NewsSummary> list) {
                view.start(list);
                if(list.size()<20){
                    loadFlag=false;
                    view.loadComplete();
                }
            }
        });
        rxManager.on(AppConstant.NEWS_LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                view.scrollToTop();
            }
        });
    }

    public void setEntity(ChannelEntity entity) {
        this.entity = entity;
    }
}
