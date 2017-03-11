package com.linewow.xhyy.forfunapp.UI.beauty;

import android.os.Bundle;

import com.linewow.xhyy.funlibrary.base.BaseInfo;
import com.linewow.xhyy.funlibrary.base.BasePresenter;
import com.linewow.xhyy.funlibrary.base.list.ListBaseModel;
import com.linewow.xhyy.funlibrary.base.list.ListBaseView;
import com.linewow.xhyy.forfunapp.entity.BeautyInfo;

/**
 * Created by LXR on 2017/1/23.
 */

public interface BeautyContract {
    interface View extends ListBaseView<BeautyInfo>{

    }

    interface Model extends ListBaseModel<BaseInfo<BeautyInfo,String>>{

    }

    abstract class Presenter extends BasePresenter<View,Model>{
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
