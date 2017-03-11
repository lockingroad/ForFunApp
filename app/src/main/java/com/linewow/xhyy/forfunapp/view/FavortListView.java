package com.linewow.xhyy.forfunapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.linewow.xhyy.forfunapp.adapter.FavortListAdapter;
import com.linewow.xhyy.forfunapp.spannable.ISpanClick;


/**
 * des:点赞列表
 * Created by xsf
 * on 2016.07.11:11
 */
public class FavortListView extends TextView {
    private ISpanClick mSpanClickListener;

    public void setSpanClickListener(ISpanClick listener){
        mSpanClickListener = listener;
    }
    public ISpanClick getSpanClickListener(){
        return  mSpanClickListener;
    }

    public FavortListView(Context context) {
        super(context);
    }

    public FavortListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavortListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(FavortListAdapter adapter){
        adapter.bindListView(this);
    }

}
