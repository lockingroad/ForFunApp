package com.linewow.xhyy.forfunapp.UI.newsdetail;
import com.linewow.xhyy.funlibrary.base.BaseModel;
import com.linewow.xhyy.funlibrary.base.BasePresenter;
import com.linewow.xhyy.funlibrary.base.BaseView;
import com.linewow.xhyy.forfunapp.entity.NewsDetailEntity;

import rx.Observable;

/**
 * Created by LXR on 2017/2/5.
 */

public interface NewsDetailContract {
    interface View extends BaseView{
        void showNews(NewsDetailEntity newsDetailEntity);
    }
    interface Model extends BaseModel{
        Observable<NewsDetailEntity>getNews(String postId);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        protected String postId;

        public void setPostId(String postId) {
            this.postId = postId;
        }
    }
}
