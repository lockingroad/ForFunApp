package com.linewow.xhyy.forfunapp.UI.publish;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.adapter.NinePicturesAdapter;
import com.linewow.xhyy.funlibrary.base.BaseActivity;
import com.linewow.xhyy.forfunapp.util.GlideImageLoader;
import com.linewow.xhyy.forfunapp.view.NoScrollGridView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LXR on 2017/3/9.
 */

public class PublishActivity extends BaseActivity {
    @Bind(R.id.publish_edit)
    EditText publishEdit;
    @Bind(R.id.publish_view)
    View publishView;
    @Bind(R.id.publish_grid)
    NoScrollGridView publishGrid;
    @Bind(R.id.publish_do)
    TextView publishDo;


    private NinePicturesAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        adapter=new NinePicturesAdapter(context, 9, new NinePicturesAdapter.OnClickAddListener() {
            @Override
            public void onClickAdd(int positin) {
                choosePhoto();
            }
        });

        publishGrid.setAdapter(adapter);
    }

    private void choosePhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);   //多选true
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(600);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }


    @OnClick(R.id.publish_do)
    public void onClick() {


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(adapter!=null){
                    adapter.addAll(getPaths(images));
                }
            }
        }
    }

    private List<String> getPaths(List<ImageItem> images) {
        List<String>list=new ArrayList<String>();
        for(ImageItem temp:images){
            list.add(temp.path);
        }
        return list;
    }
}
