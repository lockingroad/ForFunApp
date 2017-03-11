package com.linewow.xhyy.forfunapp.UI.video;

import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.forfunapp.entity.VideoEntity;
import com.linewow.xhyy.forfunapp.entity.VideoInfo;

import java.util.List;

import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by LXR on 2017/1/25.
 */

public class VideoPresenter extends VideoContract.Presenter {

    @Override
    protected void refresh() {

        page=0;
        loadFlag=true;
        model.getEntity("2",""+page).map(new Func1<VideoEntity<VideoInfo>, List<VideoInfo>>() {
            @Override
            public List<VideoInfo> call(VideoEntity<VideoInfo> videoInfoVideoEntity) {
                return videoInfoVideoEntity.data.list;
            }
        }).subscribe(new Action1<List<VideoInfo>>() {
            @Override
            public void call(List<VideoInfo> videoInfos) {
                view.refresh(videoInfos);
                if(videoInfos.size()<8){
                    view.loadComplete();
                    loadFlag=false;
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
        model.getEntity("2",""+page).map(new Func1<VideoEntity<VideoInfo>, List<VideoInfo>>() {
            @Override
            public List<VideoInfo> call(VideoEntity<VideoInfo> videoInfoVideoEntity) {
                return videoInfoVideoEntity.data.list;
            }
        }).subscribe(new Action1<List<VideoInfo>>() {
            @Override
            public void call(List<VideoInfo> videoInfos) {
                view.load(videoInfos);
                if(videoInfos.size()<8){
                    view.loadComplete();
                    loadFlag=false;
                }
            }
        });
    }

    @Override
    protected void start() {
        page=0;
        loadFlag=true;
        model.getEntity("2",""+page).map(new Func1<VideoEntity<VideoInfo>, List<VideoInfo>>() {
            @Override
            public List<VideoInfo> call(VideoEntity<VideoInfo> videoInfoVideoEntity) {
                return videoInfoVideoEntity.data.list;
            }
        }).subscribe(new Action1<List<VideoInfo>>() {
            @Override
            public void call(List<VideoInfo> videoInfos) {
                view.start(videoInfos);
                if(videoInfos.size()<8){
                    view.loadComplete();
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
}
