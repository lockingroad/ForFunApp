package com.linewow.xhyy.funlibrary.base.list;


import com.linewow.xhyy.funlibrary.base.BaseModel;

import java.util.List;

import rx.Observable;

/**
 * Created by LXR on 2017/1/19.
 */

public interface ListBaseModel<T> extends BaseModel {
    Observable<List<T>>getList(String... params);
    Observable<T>getEntity(String... params);
}
