package com.example.a10850.wanandroid.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.common.base.BaseActivity;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 问题1：点击销毁按钮崩溃
 *     android.view.WindowManager$BadTokenException: Unable to add window -- token android.os.BinderProxy@8652a83 is not valid; is your activity running?
 *      弹窗问题是因为在加载webview的时候，弹出一个loading，加载完成，就应该把他置为空
 *      https://blog.csdn.net/w_s_x_b/article/details/94555715
 *
 *          java.lang.NullPointerException: Attempt to invoke virtual method 'boolean android.view.KeyEvent.isCtrlPressed()' on a null object reference
 *
 * 问题2：不能点击js中的页面:不能点击打开的界面中的按钮
 * 问题3：进度条加载
 * 问题4: toolbar左边有固定的边距 https://blog.csdn.net/musiclife123/article/details/80417825
 *
 * 注意:1.setWebChromeClient方法必须放在setWebViewClient的前面
 *
 * 2.跳转到第三方应用,可能存在的问题是打开好多链接,可能是涉及重定向的问题
 * https://www.jianshu.com/p/1e6a7750bfa1
 * https://blog.csdn.net/qq_19404969/article/details/78780418
 * 下载下来的apk有时候还安装不上,问题挺多.暂时不研究
 *
 * 3.setDomStorageEnabled这个属性设置的理解
 * android webview setDomStorageEnabled
 * https://www.cnblogs.com/ourLifes/p/7992648.html
 *
 * 问题5:打开的页面中不显示内容,如极客的那个链接
 * Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored
 * 重定向的问题
 * https://blog.csdn.net/qq_34584049/article/details/78280815
 */
public class WebActivity extends BaseActivity {

    @BindView(R.id.webcontainer)
    LinearLayout mWebcontainer;

    @BindView(R.id.web_title)
    TextView mWebTitle;

    @BindView(R.id.web)
    WebView mWeb;
    @BindView(R.id.web_pg)
    ProgressBar mWebPg;

    @BindView(R.id.web_tbar)
    Toolbar mToolbar;

    private WebView mWebView;

    private boolean mIsDownload = false;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_web;
    }

    //xian
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        setSupportActionBar(mToolbar);
        intiWebSettings(mWeb.getSettings());

        mWeb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//解决白屏问题
        mWeb.setNetworkAvailable(true);

        if (getIntent().getStringExtra("url").equals("http://gk.link/a/103Ei"))
            mWeb.loadUrl("https://market.geekbang.org/activity/channelcoupon/16?utm_source=web&utm_medium=wananzhuo&utm_campaign=changweiliuliang&utm_term=zhanghongyang003&utm_content=0530");
        else
            mWeb.loadUrl(getIntent().getStringExtra("url"));

        //设置WebChromeClient类
        mWeb.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title != null)
                    mWebTitle.setText(title);
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //因为关掉activity的时候进度变成了10,所以报空指针的错误
                if (mWebPg != null) {
                    if (newProgress < 100) {
                        mWebPg.setVisibility(View.VISIBLE);
                        mWebPg.setProgress(newProgress);
                    } else if (newProgress == 100) {
                        mWebPg.setVisibility(View.GONE);
                    }

                }

            }

        });
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //打开第三方应用
                Log.e("should", "shouldOverrideUrlLoading: " + url);
                try {
                    //处理intent协议
                    if (url.startsWith("intent://")) {
                        Intent intent;
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            intent.addCategory("android.intent.category.BROWSABLE");
                            intent.setComponent(null);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                intent.setSelector(null);
                            }
                            List<ResolveInfo> resolves = getApplication().getPackageManager().queryIntentActivities(intent, 0);
                            if (resolves.size() > 0) {
                                startActivityIfNeeded(intent, -1);
                            } else {
                                mIsDownload = true;
                                Toast.makeText(WebActivity.this, "intent未安装", Toast.LENGTH_SHORT).show();
                                Log.e("should", "intent");
                            }
                            return true;
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    // 处理自定义scheme协议
                    else if (!url.startsWith("http")) {
//                        MyLogUtil.LogI("yxx","处理自定义scheme-->" + url);
                        try {
                            // 以下固定写法
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e("should", "http");
                            mIsDownload = true;
                            // 防止没有安装的情况
                            e.printStackTrace();
                            Toast.makeText(WebActivity.this, "您所打开的第三方App未安装！", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    } else {
                        Log.e("should", "shouldOverrideUrlLoading: " + url.indexOf("redirect"));
                        if (url.indexOf("redirect") == -1) {
                            view.loadUrl(url);
                            return true;
                        } else
                            return false;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                view.loadUrl(url);
////                return true;
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
//                showLoading();
                if (mWebTitle != null)
                    mWebTitle.setText(getIntent().getStringExtra("url"));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
//                dismissLoading();
//                loadingDialog = null;

                //得到标题是链接,是英文不是中文,不能仅仅判断空,还要判断一致否.与其这样,不如直接赋值传过来的标题
//                String title = view.getTitle();
//                Toast.makeText(WebActivity.this, "errorCode:" + title, Toast.LENGTH_SHORT).show();
//                if (!TextUtils.isEmpty(title)) {
//                    mWebTitle.setText(title);
//                } else {
//                    if (mWebTitle != null)
//                        mWebTitle.setText(getIntent().getStringExtra("title"));
//                }

                if (mWebTitle != null && !view.getTitle().equals(getIntent().getStringExtra("title")))
                    mWebTitle.setText(getIntent().getStringExtra("title"));

                if (!view.getSettings().getLoadsImagesAutomatically()) {
                    view.getSettings().setLoadsImagesAutomatically(true);
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebActivity.this, "errorCode:" + errorCode, Toast.LENGTH_SHORT).show();
//                switch (errorCode) {
//                    case HttpStatus.SC_NOT_FOUND:
//                        view.loadUrl("file:///android_assets/error_handle.html");
//                        break;
//                }
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);注意一定要去除这行代码，否则设置无效。
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }

        });


        mWeb.canGoBack();
        mWeb.canGoForward();
        mWeb.requestFocus();

        mWeb.clearCache(true);
        mWeb.clearHistory();


        mWeb.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                        String mimetype, long contentLength) {
                if (mIsDownload) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(url);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(uri);
                    startActivity(intent);
                }
                mIsDownload = true;//重置为初始状态

            }

        });


//        mToolbar.inflateMenu(R.menu.web_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.web_item1:
                        //复制 https://blog.csdn.net/seashine_yan/article/details/75426007
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                        ClipData clipData = ClipData.newPlainText(null, mWeb.getUrl());
                        // 把数据集设置（复制）到剪贴板
                        cm.setPrimaryClip(clipData);
//                        cm.setText(mWeb.getUrl());
                        Toast.makeText(WebActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.web_item2:
                        mWeb.reload();
                        break;
                    case R.id.web_item3:
                        //https://blog.csdn.net/bzlj2912009596/article/details/80673555
                        Uri uri = Uri.parse(mWeb.getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }

    //hou
    @Override
    protected void initView() {
        /*mWebView = new WebView(this);
//        mWebcontainer.addView(mWebView);
        //声明WebSettings子类 ********************start******************
        // TODO: 2020/2/8 插件设置无效
        WebSettings webSettings = mWebView.getSettings();
        intiWebSettings(webSettings);


        //优先使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        //不使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

//        if (NetStatusUtil.isConnected(getApplicationContext())) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
//
//        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
//        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
//        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

//        getIntent().getStringExtra("url")
        mWebView.loadUrl(getIntent().getStringExtra("url"));
        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                dismissLoading();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebActivity.this, "errorCode:" + errorCode, Toast.LENGTH_SHORT).show();
//                switch (errorCode) {
//                    case HttpStatus.SC_NOT_FOUND:
//                        view.loadUrl("file:///android_assets/error_handle.html");
//                        break;
//                }
            }


        });


        //设置WebChromeClient类
        mWebView.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.i("zxd", "onReceivedTitle: " + title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                Log.i("zxd", "onProgressChanged: " + newProgress);
//                if (newProgress < 100) {
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                } else if (newProgress == 100) {
//                    String progress = newProgress + "%";
//                    loading.setText(progress);
//                }
            }
        });*/


    }

    /***
     * 初始化webSettings
     * @param webSettings
     */
    private void intiWebSettings(WebSettings webSettings) {
        webSettings.setAppCacheEnabled(false);

        webSettings.setAllowContentAccess(true);
        webSettings.setNeedInitialFocus(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 用户是否需要通过手势播放媒体(不会自动播放)，默认值 true
            webSettings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 是否在离开屏幕时光栅化(会增加内存消耗)，默认值 false
            webSettings.setOffscreenPreRaster(false);
        }

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(false); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //声明WebSettings子类 ********************end******************
        //不能点击"csdn中的阅读全文"http://www.shuchengxian.com/article/DGjJRCnMhL.html
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);//不能点击"csdn中的阅读全文
//        webSettings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage
        webSettings.setGeolocationDatabasePath(getApplication().getFilesDir().getPath());
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWeb.canGoBack()) {
            mWeb.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWeb != null) {
            mWeb.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWeb.clearHistory();

            ((ViewGroup) mWeb.getParent()).removeView(mWeb);
            mWeb.destroy();
            mWeb = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_arrow, R.id.web_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow:
                //当我们打开了多个web(也就是多个url),finish时直接都关闭了,没有一个页面页面的回退
                //有可能打开的url和之前的参数url不一致
//                if (mWeb.getUrl().equals(getIntent().getStringExtra("url"))) {
//                    Log.e("ivfinish", "finish: " + 1);
//                    finish();
//                } else
//                    onKeyDown(KeyEvent.KEYCODE_BACK, null);

                if (!mWeb.canGoBack() || mWeb.getUrl().equals("https://market.geekbang.org/activity/channelcoupon/16?utm_source=web&utm_medium=wananzhuo&utm_campaign=changweiliuliang&utm_term=zhanghongyang003&utm_content=0530")) {
                    mWeb.pauseTimers();
                    finish();
                } else
                    mWeb.goBack();
                break;
            case R.id.web_collect:
                //收藏
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);//加载menu布局
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }
}
