package com.linewow.xhyy.forfunapp.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.app.ForFunApp;


/**
 * des:名字点击监听
 * Created by xsf
 * on 2016.07.11:14
 */
public class NameClickable extends ClickableSpan implements View.OnClickListener {
    private final ISpanClick mListener;
    private int mPosition;

    public NameClickable(ISpanClick l, int position) {
        mListener = l;
        mPosition = position;
    }

    @Override
    public void onClick(View widget) {
        mListener.onClick(mPosition);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        int colorValue = ForFunApp.getAppResources().getColor(
                R.color.main_color);
        ds.setColor(colorValue);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
