package com.linewow.xhyy.forfunapp.UI.beauty;

import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.funlibrary.base.BaseInfo;
import com.linewow.xhyy.forfunapp.entity.BeautyInfo;

import rx.functions.Action1;

/**
 * Created by LXR on 2017/1/23.
 */

public class BeautyPresenter extends BeautyContract.Presenter {

    @Override
    protected void start() {
        page=1;
        loadFlag=true;
        model.getEntity("20",""+page)
                .subscribe(new Action1<BaseInfo<BeautyInfo, String>>() {
                    @Override
                    public void call(BaseInfo<BeautyInfo,String> info) {
                        view.start(info.results);
                        if(info.results.size()<20){
                            loadFlag=false;
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

    @Override
    protected void refresh() {
        page=1;
        loadFlag=true;
        model.getEntity("20",""+page)
                .subscribe(new Action1<BaseInfo<BeautyInfo, String>>() {
                    @Override
                    public void call(BaseInfo<BeautyInfo,String> info) {
                        view.refresh(info.results);
                        if(info.results.size()<20){
                            loadFlag=false;
                            view.loadComplete();

                        }

                    }
                });

    }

    @Override
    protected void load() {
        if(!loadFlag){
            return;
        }
        page++;
        model.getEntity("20",""+page)
                .subscribe(new Action1<BaseInfo<BeautyInfo, String>>() {
                    @Override
                    public void call(BaseInfo<BeautyInfo,String> info) {
                        view.load(info.results);
                        if(info.results.size()<20){
                            loadFlag=false;
                            view.loadComplete();
                        }

                    }
                });
    }
}
