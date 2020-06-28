package com.example.a10850.wanandroid.entity;

import java.util.List;

/***
 * 创建时间：2020/3/9 20:45
 * 创建人：10850
 * 功能描述：
 */
public class SquareShareListBean {

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {

        private CoinInfoBean coinInfo;
        private ShareArticlesBean shareArticles;

        public CoinInfoBean getCoinInfo() {
            return coinInfo;
        }

        public void setCoinInfo(CoinInfoBean coinInfo) {
            this.coinInfo = coinInfo;
        }

        public ShareArticlesBean getShareArticles() {
            return shareArticles;
        }

        public void setShareArticles(ShareArticlesBean shareArticles) {
            this.shareArticles = shareArticles;
        }

        public static class CoinInfoBean {
            /**
             * coinCount : 7098
             * level : 71
             * rank : 5
             * userId : 2
             * username : x**oyang
             */

            private int coinCount;
            private int level;
            private int rank;
            private int userId;
            private String username;

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class ShareArticlesBean {
            /**
             * curPage : 1
             * datas : [{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":12248,"link":"https://juejin.im/post/5e61b803f265da57127e526c","niceDate":"21小时前","niceShareDate":"21小时前","origin":"","prefix":"","projectLink":"","publishTime":1583679981000,"selfVisible":0,"shareDate":1583679981000,"shareUser":"鸿洋","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":" flutter_boost源码浅析 ","type":0,"userId":2,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":12247,"link":"https://juejin.im/post/5e64390bf265da575f4e7de8#heading-11","niceDate":"21小时前","niceShareDate":"21小时前","origin":"","prefix":"","projectLink":"","publishTime":1583679466000,"selfVisible":0,"shareDate":1583679466000,"shareUser":"鸿洋","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":" 说说Android的UI刷新机制 ","type":0,"userId":2,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":375,"chapterName":"Flutter","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":12231,"link":"https://zhuanlan.zhihu.com/p/90836859","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1583665117000,"selfVisible":0,"shareDate":1583591298000,"shareUser":"鸿洋","superChapterId":375,"superChapterName":"跨平台","tags":[],"title":"万字长文轻松彻底入门 Flutter，秒变大前端","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":135,"chapterName":"二维码","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":12228,"link":"https://blog.csdn.net/yinhaide/article/details/104551458","niceDate":"1天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1583665110000,"selfVisible":0,"shareDate":1583574388000,"shareUser":"鸿洋","superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android二维码原理与优化方向","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":171,"chapterName":"binder","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"id":12242,"link":"https://www.jianshu.com/p/4a135838da4a","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1583665072000,"selfVisible":0,"shareDate":1583663860000,"shareUser":"鸿洋","superChapterId":173,"superChapterName":"framework","tags":[],"title":"腾讯面试题&mdash;&mdash;谈一谈Binder的原理和实现一次拷贝的流程","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":510,"chapterName":"大厂分享","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12221,"link":"https://mp.weixin.qq.com/s/xQ5I0omWC8-6W4RAYl0hkA","niceDate":"1天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1583640584000,"selfVisible":0,"shareDate":1583567191000,"shareUser":"鸿洋","superChapterId":510,"superChapterName":"大厂对外分享","tags":[],"title":"一种简单优雅的TextView行间距适配方案","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>很久以前有Activity.onResume就是界面可见的说法，这种说法毫无疑问是不准确的。<\/p>\r\n<p>问题是：<\/p>\r\n<ol>\r\n<li>Activity.onCreate 和 Activity.onResume，在调用时间上有差别么？可以从Message调度去考虑。<\/li>\r\n<li>有没有一个合理的时机，让我们认为Activity 界面可见了？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":12175,"link":"https://wanandroid.com/wenda/show/12175","niceDate":"1天前","niceShareDate":"2020-03-03 18:49","origin":"","prefix":"","projectLink":"","publishTime":1583638222000,"selfVisible":0,"shareDate":1583232546000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问 | 很久以前有Activity.onResume就是界面可见的说法，这种说法错了多少？","type":1,"userId":2,"visible":1,"zan":4},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>之前我们讨论过 <a href=\"https://wanandroid.com/wenda/show/8488\">View的onAttachedToWindow ,onDetachedFromWindow 调用时机<\/a> 。<\/p>\r\n<p>这个机制在RecyclerView卡片中还适用吗？<\/p>\r\n<p>例如我们在RecyclerView的Item的onBindViewHolder时，利用一个CountDownTimer去做一个倒计时显示 / 或者是有一个属性动画效果？<\/p>\r\n<ol>\r\n<li>到底在什么时候可以cancel掉这个倒计时/ 动画，而不影响功能了（滑动到用户可见范围内，倒计时/动画 运作正常）?<\/li>\r\n<li>有什么方法可以和onBindViewHolder 对应吗？就像onAttachedToWindow ,onDetachedFromWindow这样 。<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":12148,"link":"https://wanandroid.com/wenda/show/12148","niceDate":"1天前","niceShareDate":"2020-03-01 15:14","origin":"","prefix":"","projectLink":"","publishTime":1583638219000,"selfVisible":0,"shareDate":1583046877000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问 RecyclerView卡片中持有的资源，到底该什么时候释放？","type":1,"userId":2,"visible":1,"zan":15},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12232,"link":"https://mp.weixin.qq.com/s/mGVYkuA0DJuHR0zhGRvqyg","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1583592247000,"selfVisible":0,"shareDate":1583592247000,"shareUser":"鸿洋","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"面试题：如何理解 Linux 的零拷贝技术？","type":0,"userId":2,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>看过LifeCycle原理的同学可能都能说出来：<\/p>\r\n<p>LifeCycle 利用 Fragment，进行 Event 分发，然后通过反射执行各个 LifecycleObserver 中对应的Event 注册方法。<\/p>\r\n<p>其中还引入了一个 State 的概念，那么为什么不 Fragment dispatch Event的时候直接执行到 Observer 里面的相关事件方法， 而要经过一个 State 的概念呢？<\/p>\r\n<pre><code>#ReportFragment\r\n@Override\r\n    public void onActivityCreated(Bundle savedInstanceState) {\r\n        super.onActivityCreated(savedInstanceState);\r\n        dispatchCreate(mProcessListener);\r\n        dispatch(Lifecycle.Event.ON_CREATE);\r\n    }\r\n<\/code><\/pre>","descMd":"","envelopePic":"","fresh":false,"id":12230,"link":"https://wanandroid.com/wenda/show/12230","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1583591166000,"selfVisible":0,"shareDate":1583591166000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问 LifeCycle 对于 Lifecycle.Event 为啥不直接分发，而是通过 Lifecycle.State 中转？","type":2,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>第一个 API 是：<\/p>\r\n<pre><code>FragmentManager.executePendingTransactions()\r\n<\/code><\/pre><p>第二个 API 是：<\/p>\r\n<pre><code>FragmentTransaction.commitNow();\r\n<\/code><\/pre><p>大家都清楚 Fragment很多 API 都是\u201c异步\u201d的，即并非是立即生效的.<\/p>\r\n<p>那么这两个 API 有点\u201c同步\u201d的感觉。<\/p>\r\n<p>请问：<\/p>\r\n<ol>\r\n<li>这两个 API 有何作用？<\/li>\r\n<li>这两个 API 有啥区别？<\/li>\r\n<li>这两个 API 有什么使用场景么？<\/li>\r\n<\/ol>\r\n<p>一些提示，两个 API 的一个使用场景。<\/p>\r\n<p>你在 LifeCycle 的源码中可以看到：<\/p>\r\n<pre><code>public class ReportFragment extends Fragment {\r\n    private static final String REPORT_FRAGMENT_TAG = &quot;androidx.lifecycle&quot;\r\n            + &quot;.LifecycleDispatcher.report_fragment_tag&quot;;\r\n\r\n    public static void injectIfNeededIn(Activity activity) {\r\n        // ProcessLifecycleOwner should always correctly work and some activities may not extend\r\n        // FragmentActivity from support lib, so we use framework fragments for activities\r\n        android.app.FragmentManager manager = activity.getFragmentManager();\r\n        if (manager.findFragmentByTag(REPORT_FRAGMENT_TAG) == null) {\r\n            manager.beginTransaction().add(new ReportFragment(), REPORT_FRAGMENT_TAG).commit();\r\n            // Hopefully, we are the first to make a transaction.\r\n            manager.executePendingTransactions();\r\n        }\r\n    }\r\n<\/code><\/pre><p>你在 RxPermission 可以看到以下使用：<\/p>\r\n<pre><code>rivate RxPermissionsFragment getRxPermissionsFragment(@NonNull final FragmentManager fragmentManager) {\r\n        RxPermissionsFragment rxPermissionsFragment = findRxPermissionsFragment(fragmentManager);\r\n        boolean isNewInstance = rxPermissionsFragment == null;\r\n        if (isNewInstance) {\r\n            rxPermissionsFragment = new RxPermissionsFragment();\r\n            fragmentManager\r\n                    .beginTransaction()\r\n                    .add(rxPermissionsFragment, TAG)\r\n                    .commitNow();\r\n        }\r\n        return rxPermissionsFragment;\r\n    }\r\n<\/code><\/pre>","descMd":"","envelopePic":"","fresh":false,"id":12229,"link":"https://wanandroid.com/wenda/show/12229","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1583590788000,"selfVisible":0,"shareDate":1583590788000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问  今天考察下 Fragment 相关两个不常见 API","type":2,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12223,"link":"https://juejin.im/post/5e5f1d41518825495b29a05b","niceDate":"2天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1583568026000,"selfVisible":0,"shareDate":1583568026000,"shareUser":"鸿洋","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":" dna --- 一个 dart 到 native 的超级通道 ","type":0,"userId":2,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12222,"link":"https://juejin.im/post/5e5f4a5be51d4526cb162775","niceDate":"2天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1583567944000,"selfVisible":0,"shareDate":1583567944000,"shareUser":"鸿洋","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":" Android仿京东天猫列表页播视频看这一篇就足够了 ","type":0,"userId":2,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":510,"chapterName":"大厂分享","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12203,"link":"https://juejin.im/post/5e5e1145f265da5741120b5a","niceDate":"2020-03-06 00:29","niceShareDate":"2020-03-06 00:17","origin":"","prefix":"","projectLink":"","publishTime":1583425798000,"selfVisible":0,"shareDate":1583425041000,"shareUser":"鸿洋","superChapterId":510,"superChapterName":"大厂对外分享","tags":[],"title":"UI系列一Android多子view嵌套通用解决方案","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":126,"chapterName":"绘图相关","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12204,"link":"https://www.jianshu.com/p/af99ef0618d5?utm_source=desktop&amp;utm_medium=timeline","niceDate":"2020-03-06 00:29","niceShareDate":"2020-03-06 00:23","origin":"","prefix":"","projectLink":"","publishTime":1583425792000,"selfVisible":0,"shareDate":1583425421000,"shareUser":"鸿洋","superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"【译】一文带你了解Android中23个关于Canvas绘制的方法","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12206,"link":"https://www.jianshu.com/p/f25c6f8b1594","niceDate":"2020-03-06 00:29","niceShareDate":"2020-03-06 00:28","origin":"","prefix":"","projectLink":"","publishTime":1583425776000,"selfVisible":0,"shareDate":1583425693000,"shareUser":"鸿洋","superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"程序员常用工具总结","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12188,"link":"https://segmentfault.com/a/1190000021782703","niceDate":"2020-03-04 11:50","niceShareDate":"2020-03-04 11:50","origin":"","prefix":"","projectLink":"","publishTime":1583293826000,"selfVisible":0,"shareDate":1583293826000,"shareUser":"鸿洋","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"Flutter EasyLoading - 让全局Toast/Loading更简单 - 老黄Talk ","type":0,"userId":2,"visible":0,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>早上看到事件分发的一个讨论：<\/p>\r\n<p><img src=\"https://wanandroid.com/blogimgs/9bfb6411-6262-4d59-884f-3e868e5493cd.jpg\" alt><\/p>\r\n<p>那么事件到底是先到DecorView还是先到Window(Activity，Dialog)的。<\/p>\r\n<ol>\r\n<li>touch相关事件在DecorView，PhoneWindow，Activity/Dialog之间传递的顺序是什么样子的？<\/li>\r\n<li>为什么要按照1这么设计？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"id":12119,"link":"https://wanandroid.com/wenda/show/12119","niceDate":"2020-03-03 23:25","niceShareDate":"2020-02-27 19:07","origin":"","prefix":"","projectLink":"","publishTime":1583249132000,"selfVisible":0,"shareDate":1582801649000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"问答","url":"/article/list/0?cid=440"}],"title":"每日一问 | 事件到底是先到DecorView还是先到Window的？","type":0,"userId":2,"visible":1,"zan":15},{"apkLink":"","audit":1,"author":"","canEdit":false,"chapterId":90,"chapterName":"数据库","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"id":12165,"link":"https://juejin.im/post/5e5bdce6e51d452705318c59","niceDate":"2020-03-03 00:17","niceShareDate":"2020-03-03 00:16","origin":"","prefix":"","projectLink":"","publishTime":1583165838000,"selfVisible":0,"shareDate":1583165776000,"shareUser":"鸿洋","superChapterId":90,"superChapterName":"数据存储","tags":[],"title":"[译] 如何用 Room 处理一对一，一对多，多对多关系？","type":0,"userId":2,"visible":1,"zan":0}]
             * offset : 0
             * over : false
             * pageCount : 20
             * size : 20
             * total : 382
             */

            private int curPage;
            private int offset;
            private boolean over;
            private int pageCount;
            private int size;
            private int total;
            private List<ContentBean> datas;

            public int getCurPage() {
                return curPage;
            }

            public void setCurPage(int curPage) {
                this.curPage = curPage;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public boolean isOver() {
                return over;
            }

            public void setOver(boolean over) {
                this.over = over;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<ContentBean> getDatas() {
                return datas;
            }

            public void setDatas(List<ContentBean> datas) {
                this.datas = datas;
            }
        }
    }
}
