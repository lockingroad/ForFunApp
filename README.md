# ForFunApp
## 整理项目  增加用到的东西


## 结构
### MVP

BaseModel BaseView BasePresenter
列表类的操作都一样
ListBaseModel
```android
public interface ListBaseModel<T> extends BaseModel {
    Observable<List<T>>getList(String... params);
    Observable<T>getEntity(String... params);
}
```
ListBaseView
```android
public interface ListBaseView<T> extends BaseView {
    void refresh(List<T> list);
    void load(List<T> list);
    void err(String err);
    void start(List<T> list);
    void loadComplete();
    void scrollToTop();
}
```
##  框架
### 网络方面


![net.PNG](http://upload-images.jianshu.io/upload_images/2482523-c66cf2cc3269ced9.PNG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```android
    @GET("data/福利/{size}/{page}")
    Observable<BaseInfo<BeautyInfo,String>>getBeautyList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page
    );
```
retrofit请求首部字段设置缓存

### 通信（观察者）
```android
public class RxManager {
    public RxBus mRxBus = RxBus.getInstance();
    //管理rxbus订阅
    private Map<String, Observable<?>> mObservables = new HashMap<>();
    /*管理Observables 和 Subscribers订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    /**
     * RxBus注入监听
     * @param eventName
     * @param action1
     */
    public <T>void on(String eventName, Action1<T> action1) {
        Observable<T> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        /*订阅管理*/
        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param m
     */
    public void add(Subscription m) {
        /*订阅管理*/
        mCompositeSubscription.add(m);
    }
    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消所有订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除rxbus观察
        }
    }
    //发送rxbus
    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
```

## 功能

### MD样式导航栏
```android
compile 'com.github.RoyWallace:BottomNavigationBar:v0.1'
```
### 详情大图的查看
BeautyPhotoActivity
### textview显示html内容
NewsDetailActivity
### 视频播放控件
JCVideoPlayerStandard
### 朋友圈模块
#### recyclerview坐标的监听、偏移等。
#### 点赞、评论、发布的数据处理逻辑
#### 点赞FavortListView
#### 评论CommentListView
