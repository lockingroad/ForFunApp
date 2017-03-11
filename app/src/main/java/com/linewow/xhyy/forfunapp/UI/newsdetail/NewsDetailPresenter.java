package com.linewow.xhyy.forfunapp.UI.newsdetail;

import android.util.Log;

import com.linewow.xhyy.forfunapp.entity.NewsDetailEntity;

import rx.functions.Action1;

/**
 * Created by LXR on 2017/2/5.
 */

public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    private String TAG="NewsDetailPresenter";
    @Override
    protected void start() {
        model.getNews(postId).subscribe(new Action1<NewsDetailEntity>() {
            @Override
            public void call(NewsDetailEntity newsDetailEntity) {
                view.showNews(newsDetailEntity);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG,"抛出异常了");
            }
        });
    }
}
