package com.linewow.xhyy.forfunapp.UI.zone;
import android.util.Log;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.app.AppCache;
import com.linewow.xhyy.forfunapp.entity.CircleItem;
import com.linewow.xhyy.forfunapp.entity.CommentConfig;
import com.linewow.xhyy.forfunapp.entity.CommentItem;
import com.linewow.xhyy.forfunapp.entity.FavortItem;
import com.linewow.xhyy.forfunapp.entity.PageBean;
import com.linewow.xhyy.forfunapp.entity.Result;
import com.linewow.xhyy.forfunapp.util.DatasUtil;
import com.linewow.xhyy.forfunapp.util.JsonUtils;
import com.linewow.xhyy.forfunapp.view.GoodView;

import java.util.List;
import java.util.Random;

import rx.functions.Action1;

/**
 * Created by LXR on 2017/3/6.
 */

public class ZonePresenter extends ZoneContract.Presenter{




    private GoodView goodView;
    private String TAG="ZonePresenter";

    @Override
    void getData() {
        model.getData().subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                List<CircleItem>circleItems= JSON.parseArray(JsonUtils.getValue(result.getMsg(),"list"),CircleItem.class);
                for(int i=0;i<circleItems.size();i++){
                    String newPath= DatasUtil.getRandomPhotoUrlString(new Random().nextInt(9));
                    circleItems.get(i).setPictures(newPath);
                }
                PageBean pageBean=JSON.parseObject(JsonUtils.getValue(result.getMsg(),"page"),PageBean.class);
                view.getData(circleItems,pageBean);
            }
        });
    }

    @Override
    public void like(final String id, final String userId, final int position, final View likeView) {
        if(goodView==null){
            goodView=new GoodView(context);
        }
        Log.e(TAG,"like");
        model.like(id,userId).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                goodView.setImage(R.mipmap.dianzan);
                goodView.show(likeView);
                Log.e(TAG,"开始点赞动画了");
                FavortItem favortItem=new FavortItem(id, AppCache.getInstance().getUserId(),"nlnl");
                view.like(position,favortItem);
            }
        });
    }

    @Override
    public void unLike(String id, String userId, final int position) {
        Log.e(TAG,"unlike");
        model.unLike(id,userId)
                .subscribe(new Action1<Result>() {
                    @Override
                    public void call(Result result) {
                        view.unLike(position,AppCache.getInstance().getUserId());
                    }
                });
    }

    @Override
    public void updateEdit(CommentConfig config) {
        view.updateEdit(View.VISIBLE,config);
    }

    @Override
    void addComment(final String content, final CommentConfig config) {
        model.addComment(config.getPublishUserId(),new CommentItem(config.getName(),config.getId(),content
                                        ,config.getPublishId(),AppCache.getInstance().getUserId(),"jayden"))
        .subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                view.addComment(config.circlePosition,new CommentItem(config.getName(),config.getId(),content
                        ,config.getPublishId(),AppCache.getInstance().getUserId(),"jayden"));
            }
        });


    }


    @Override
    protected void start() {

    }
}
