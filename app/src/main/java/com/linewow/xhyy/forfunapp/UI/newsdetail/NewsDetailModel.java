package com.linewow.xhyy.forfunapp.UI.newsdetail;

import com.linewow.xhyy.forfunapp.entity.NewsDetailEntity;
import com.linewow.xhyy.forfunapp.netretrofit.Api;
import com.linewow.xhyy.forfunapp.netretrofit.HostType;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LXR on 2017/2/5.
 */

public class NewsDetailModel implements NewsDetailContract.Model{
    @Override
    public Observable<NewsDetailEntity> getNews(final String postId) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewDetail(Api.getCacheControl(),postId)
                .map(new Func1<Map<String,NewsDetailEntity>, NewsDetailEntity>() {
                    @Override
                    public NewsDetailEntity call(Map<String, NewsDetailEntity> stringNewsDetailEntityMap) {
                        NewsDetailEntity detailEntity=stringNewsDetailEntityMap.get(postId);
                        changeNewsDetail(detailEntity);
                        return detailEntity;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void changeNewsDetail(NewsDetailEntity newsDetail) {
        List<NewsDetailEntity.ImgEntity> imgSrcs = newsDetail.getImg();
        if (isChange(imgSrcs)) {
            String newsBody = newsDetail.getBody();
            newsBody = changeNewsBody(imgSrcs, newsBody);
            newsDetail.setBody(newsBody);
        }
    }

    private boolean isChange(List<NewsDetailEntity.ImgEntity> imgSrcs) {
        return imgSrcs != null && imgSrcs.size() >= 2;
    }

    private String changeNewsBody(List<NewsDetailEntity.ImgEntity> imgSrcs, String newsBody) {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).src + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);

        }
        return newsBody;
    }
}
