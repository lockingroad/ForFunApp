package com.linewow.xhyy.forfunapp.UI.care;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.UI.publish.PublishActivity;
import com.linewow.xhyy.forfunapp.UI.zone.ZoneActivity;
import com.linewow.xhyy.funlibrary.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;
/**
 * Created by LXR on 2017/1/19.
 */

public class CareFragment extends BaseFragment {
    @Bind(R.id.care_zone)
    Button careCircle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_care;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick({R.id.care_zone,R.id.care_publish})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.care_zone:
                Intent intent=new Intent(context, ZoneActivity.class);
                startActivity(intent);
                break;
            case R.id.care_publish:
                Intent intent1=new Intent(context, PublishActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
