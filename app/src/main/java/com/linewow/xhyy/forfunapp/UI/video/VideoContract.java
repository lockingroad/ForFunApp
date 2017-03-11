package com.linewow.xhyy.forfunapp.UI.video;

import android.os.Bundle;

import com.linewow.xhyy.funlibrary.base.BasePresenter;
import com.linewow.xhyy.funlibrary.base.list.ListBaseModel;
import com.linewow.xhyy.funlibrary.base.list.ListBaseView;
import com.linewow.xhyy.forfunapp.entity.VideoEntity;
import com.linewow.xhyy.forfunapp.entity.VideoInfo;

/**
 * Created by LXR on 2017/1/25.
 */

public interface VideoContract {
    interface View extends ListBaseView<VideoInfo>{

    }
    interface Model extends ListBaseModel<VideoEntity<VideoInfo>>{

    }
    abstract class Presenter extends BasePresenter<View,Model> {
        protected int page;
        protected boolean loadFlag;
        protected Bundle argument;

        public void setArgument(Bundle argument) {
            this.argument = argument;
        }
        protected abstract void refresh();
        protected abstract void load();
    }
}
