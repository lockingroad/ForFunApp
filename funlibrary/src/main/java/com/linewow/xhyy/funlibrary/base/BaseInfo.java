package com.linewow.xhyy.funlibrary.base;

import java.util.List;

/**
 * Created by LXR on 2016/8/30.
 * 列表-实体基类
 */
public class BaseInfo<T,D> {
    public boolean error;
    public List<T> results;
    public String info;
    public D data;

}
