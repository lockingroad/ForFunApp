package com.linewow.xhyy.funlibrary.base.list;


import com.linewow.xhyy.funlibrary.base.BaseView;

import java.util.List;

/**
 * Created by LXR on 2017/1/19.
 */

public interface ListBaseView<T> extends BaseView {
    void refresh(List<T> list);
    void load(List<T> list);
    void err(String err);
    void start(List<T> list);
    void loadComplete();
    void scrollToTop();
}
