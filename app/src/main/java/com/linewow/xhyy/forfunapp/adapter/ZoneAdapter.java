package com.linewow.xhyy.forfunapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.linewow.xhyy.forfunapp.UI.zone.ZonePresenter;
import com.linewow.xhyy.forfunapp.base.BaseReclyerViewAdapter;
import com.linewow.xhyy.forfunapp.entity.CircleItem;
import com.linewow.xhyy.forfunapp.viewholder.ZoneViewHolder;

/**
 * Created by LXR on 2017/3/6.
 */

public class ZoneAdapter extends BaseReclyerViewAdapter<CircleItem> {
    private ZonePresenter zonePresenter;
    private Context context;
    public ZoneAdapter(Context context,ZonePresenter zonePresenter) {
        super(context);
        this.context=context;
        this.zonePresenter=zonePresenter;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ZoneViewHolder){
            ((ZoneViewHolder)holder).setData(zonePresenter,data.get(position),position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ZoneViewHolder.createView(context,viewType);
    }
}
