package com.linewow.xhyy.forfunapp.UI.channel;

import com.linewow.xhyy.funlibrary.base.BaseModel;
import com.linewow.xhyy.funlibrary.base.BasePresenter;
import com.linewow.xhyy.funlibrary.base.BaseView;
import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by LXR on 2017/1/17.
 */

public interface ChannelContract {
    interface View extends BaseView{
        void start(List<ChannelEntity>list);

    }
    interface Model extends BaseModel{
        Observable<List<ChannelEntity>>start();
    }

    abstract class Presenter extends BasePresenter<View,Model>{

    }
}
