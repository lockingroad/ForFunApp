package com.linewow.xhyy.funlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.linewow.xhyy.funlibrary.baserx.RxManager;
import com.linewow.xhyy.funlibrary.util.TUtil;
import com.linewow.xhyy.funlibrary.util.TransitionHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by LXR on 2017/1/16.
 */

public abstract class BaseFragment<P extends BasePresenter,M extends BaseModel> extends Fragment{
    protected P presenter;
    protected M model;
    protected View contentView;
    protected RxManager rxManager;
    protected Context context;
    private String analysisName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        analysisName=this.getClass().getCanonicalName();
        contentView =View.inflate(getActivity(),getLayoutId(),null);
        ButterKnife.bind(this,contentView);
        presenter= TUtil.getT(BaseFragment.this,0);
        model=TUtil.getT(BaseFragment.this,1);
        context=getActivity();
        if(presenter!=null){
            presenter.context=context;
        }
        rxManager=new RxManager();
        return contentView;
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initPresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initPresenter();
    }


    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void showLoading() {
        ((BaseCompatActivity)getActivity()).showLoading();
    }

    public void dismissLoading() {
        ((BaseCompatActivity)getActivity()).dismissLoading();
    }

    protected void hideSoftKeyboard() {
        ((BaseCompatActivity)getActivity()).hideKeyBoard();
    }

    public void startActivityShare(Class<?>cls,View...view){
        startActivityShare(null,cls,view);
    }
    public void startActivityShare (Bundle bundle,Class<?>cls,View...view){
        Intent intent=new Intent(context,cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            List<Pair> pairList=new ArrayList<Pair>();
            for(View temp:view){
                Pair tvPair=new Pair<>(temp, ViewCompat.getTransitionName(temp));
                pairList.add(tvPair);
            }
            Pair[]pairArr=new Pair[]{};
            pairArr=pairList.toArray(pairArr);
            final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants((Activity) context, false, pairArr);
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,pairs);
            startActivity(intent,optionsCompat.toBundle());
        }else{
            startActivity(intent);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
