package com.linewow.xhyy.forfunapp.UI.zone;
import com.linewow.xhyy.funlibrary.base.BaseModel;
import com.linewow.xhyy.funlibrary.base.BasePresenter;
import com.linewow.xhyy.funlibrary.base.BaseView;
import com.linewow.xhyy.forfunapp.entity.CircleItem;
import com.linewow.xhyy.forfunapp.entity.CommentConfig;
import com.linewow.xhyy.forfunapp.entity.CommentItem;
import com.linewow.xhyy.forfunapp.entity.FavortItem;
import com.linewow.xhyy.forfunapp.entity.PageBean;
import com.linewow.xhyy.forfunapp.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Created by LXR on 2017/3/6.
 */

public interface ZoneContract {

    interface Model extends BaseModel{
        Observable<Result>getData();
        Observable<Result>like(String id,String userID);
        Observable<Result>unLike(String id,String userID);
        Observable<Result>addComment(String userID, CommentItem item);
        Observable<Result>del(String id);

    }
    interface View extends BaseView{
        void getData(List<CircleItem>circleItems, PageBean pageBean);
        void unLike(int position,String userId);
        void like(int position, FavortItem item);
        void updateEdit(int visible,CommentConfig config);
        void addComment(int position,CommentItem commentItem);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        abstract void getData();
        abstract void like(String id,String userId,int position,android.view.View likeView);
        abstract void unLike(String id,String userId,int position);

        abstract void updateEdit(CommentConfig config);
        abstract void addComment(String content,CommentConfig config);
    }
}
