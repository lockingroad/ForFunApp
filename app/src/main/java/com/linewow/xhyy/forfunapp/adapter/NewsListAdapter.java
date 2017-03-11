package com.linewow.xhyy.forfunapp.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.entity.NewsSummary;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Created by LXR on 2017/1/18.
 */
public class NewsListAdapter extends BaseQuickAdapter<NewsSummary>{
    private Context context;
    public NewsListAdapter(List<NewsSummary> data) {
        super(R.layout.item_newslist,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsSummary newsSummary) {
        if(context==null){
            context=baseViewHolder.getConvertView().getContext();
        }
        ImageView imageView=baseViewHolder.getView(R.id.item_news_list_img);
        Picasso.with(context).load(newsSummary.getImgsrc()).fit().centerCrop().into(imageView);
        baseViewHolder.setText(R.id.item_news_list_tv,newsSummary.getTitle());
    }
}
