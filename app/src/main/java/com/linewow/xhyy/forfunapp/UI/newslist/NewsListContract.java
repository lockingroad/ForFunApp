package com.linewow.xhyy.forfunapp.UI.newslist;

import com.linewow.xhyy.funlibrary.base.BaseModel;
import com.linewow.xhyy.funlibrary.base.BasePresenter;
import com.linewow.xhyy.funlibrary.base.BaseView;
import com.linewow.xhyy.forfunapp.entity.NewsSummary;
import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by LXR on 2017/1/17.
 */

public interface NewsListContract {
    interface View extends BaseView{
        void start(List<NewsSummary> list);
        void refresh(List<NewsSummary>list);
        void load(List<NewsSummary> list);
        void loadComplete();
        void scrollToTop();
    }

    interface Model extends BaseModel{
        Observable<List<NewsSummary>>getNewsSummary(int page, ChannelEntity entity);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        abstract void refresh();
        abstract void load();
    }
}
