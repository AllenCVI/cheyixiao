#### 1. 字符资源：统一放置在strings.xml中，避免代码中硬编码


#### 2. 权限申请
AutoForcePermission.requestPermissions()

#### 3. WebView的使用：
使用自定义WebView：com.autoforce.cheyixiao.common.view.AutoForceWebView

#### 4. 图片加载：
使用二次封装后的工具类：ImageLoaderUtils.loadImage()

#### 5. 圆形ImageView
com.autoforce.cheyixiao.common.view.roundedimageview.RoundedImageView

#### 6. 定位工具类
com.autoforce.cheyixiao.common.utils.LocationUtil

#### 7. Gson提供
GsonProvider.gson()

#### 8. assets目录文件操作工具类：
com.autoforce.cheyixiao.common.utils.AssetFileUtils


#### 9. 屏幕底部弹出PopupWindow：
com.autoforce.cheyixiao.common.view.BottomPopupWindowManager

#### 10. mvp包下提供了一些base类，可根据实际情况自由实现mvp模式（或者简单页面可以不用），不用受这个限制
#### 11. 将通用的工具类统一放置到common包中

#### 12. 日志输出

  - Logger.w()  : warning log
  - Logger.d()  : debug log
  - Logger.e()  : error log
  - Logger.json()/Logger.xml()

  统一TAG为cheyixiao，已经在App中全局设置了，如果需要自定义TAG

  - Logger.t("").w()
  - Logger.t("").d(）
  - Logger.t("").e()
  - Logger.t("").json() / Logger.t("").xml()


#### 13. 网络判断工具类
com.autoforce.cheyixiao.common.utils.NetUtils


#### 14. dp2px px2dp等相关
com.autoforce.cheyixiao.common.utils.DeviceUtil

#### 15. SpUtils
- 方式一：SpUtils.getInstance().put() /SpUtils.getInstance().get()
- 方式二：SpUtils.getInstance("xxx")        // xxx为对应xml文件名

#### 16. 加粗TextView和Button：加粗程度要比在xml中设置textStyle="bold"要浅
com.autoforce.cheyixiao.common.view.bold.BoldTextView
com.autoforce.cheyixiao.common.view.bold.BoldButton

#### 17.所有二级页面继承BaseToolbarActivity 标题头在其中


### 18.二级双标题页面可继承BaseDoubleTitleFragment 也可自己实现

#### 19. 网络请求返回实体类可继承SimpleResult，对于Retrofit返回的Flowable对象的订阅，可以使用DefaultDisposableSubscriber（封装了错误Toast以及等待Loading）

#### 21.集成腾讯x5webView  使用时直接继承BaseX5WebViewActivity  实现必要的方法  （getTitle（）设置标题   setOther()实现js交互或者设置WebChromeClient等）

#### 22.URL Scheme
- url: `cyx://com.autoforce.net`
- 默认是打开 MainActivity


