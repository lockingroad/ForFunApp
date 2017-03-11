package com.linewow.xhyy.funlibrary.base;

import android.content.Context;

import com.linewow.xhyy.funlibrary.baserx.RxManager;


/**
 * Created by LXR on 2017/1/16.
 */

public abstract class BasePresenter <V,M>{
    protected V view;
    protected M model;
    public Context context;
    public RxManager rxManager=new RxManager();
    public void setVM(V view,M model){
        this.view=view;
        this.model=model;
        start();

    }
    protected abstract void start();
}
