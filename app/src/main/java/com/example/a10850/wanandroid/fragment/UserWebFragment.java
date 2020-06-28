package com.example.a10850.wanandroid.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.activity.WebActivity;
import com.example.a10850.wanandroid.adapter.CollectWebAdapter;
import com.example.a10850.wanandroid.entity.CollectBean;
import com.example.a10850.wanandroid.entity.UsedWebBean;
import com.example.a10850.wanandroid.ui.user.UserCenterContract;
import com.example.a10850.wanandroid.ui.user.UserCenterPresenter;
import com.example.common.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 创建时间：2020/3/9 22:15
 * 创建人：10850
 * 功能描述：
 * 1.注意点：如果rv要使用上下文菜单，必须得把长按事件给拦截了才行
 * 2.思考：使用mvp的时候，编辑收藏网站的时候，是在presenter里刷新数据还是view中刷新数据，那个好点
 * 3.java.lang.IllegalStateException: Fatal Exception thrown on Scheduler
 * cookie失效了，就可能会报这个错误(https://blog.csdn.net/qiaoxiaoguang/article/details/80963110)
 * 好像还有这个错：android studio报错(server)' ~ Channel is unrecoverably broken and will be disposed!
 * 4.上拉刷新的时候要注意数据集合和adapter的数据有差异的话，就会报错
 * java.lang.IndexOutOfBoundsException:Inconsistency detected. Invalid view holder adapter positionViewHolder{2064e5c6 position=2 id=-1, oldPos=2, pLpos:-1 scrap [attachedScrap] tmpDetached no parent}
 * https://www.jianshu.com/p/2eca433869e9
 * https://blog.csdn.net/u012992171/article/details/51201164
 * https://blog.csdn.net/rowland001/article/details/51442875
 *
 * 5.popupwindow变暗必须通过动画来实现window的变化
 * 6.添加收藏网站
 */
public class UserWebFragment extends BaseMvpFragment<UserCenterPresenter> implements UserCenterContract.View {


    @BindView(R.id.collectweb_rv)
    RecyclerView mCollectwebRv;
    Unbinder unbinder;

    TextView mEditTvTitle;
    EditText mEditEtName;
    EditText mEditEtLink;
    Button mEditBtn1;
    Button mEditBtn2;
    ImageView mEditIvClose;

    private CollectWebAdapter mAdapter;
    private List<UsedWebBean.DataBean> mList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        presenter.getCollectWeb();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_user_web, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new CollectWebAdapter(R.layout.collect_web_item, mList);
        mCollectwebRv.setAdapter(mAdapter);
        mCollectwebRv.setLayoutManager(new LinearLayoutManager(getActivity()));


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", mList.get(position).getLink());
                intent.putExtra("title", mList.get(position).getName());
                startActivity(intent);
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                //执行请求，删除收藏的网站
                delPop(position);
                return true;
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //弹出一个dialog，修改内容并且保存
                editDialog(position);
            }
        });

    }

    @Override
    public void requestSuccess(CollectBean bean) {

    }

    @Override
    public void requestFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestSuccessShare(Object o) {
        UsedWebBean webBean = (UsedWebBean) o;
        if (webBean.getErrorCode() == 0) {
            mList.addAll(webBean.getData());
            mAdapter.notifyDataSetChanged();
        } else if (webBean.getErrorCode() == -1001) {
            Toast.makeText(getActivity(), "请先登录!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected UserCenterPresenter createPresenter() {
        return new UserCenterPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void editDialog(final int pos) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_item, null, false);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.DialogTheme)
                .setView(view)
                .setCancelable(true);
        final AlertDialog show = dialog.show();

        mEditEtName = view.findViewById(R.id.edit_et_name);
        mEditEtLink = view.findViewById(R.id.edit_et_link);
        mEditBtn1 = view.findViewById(R.id.edit_btn1);
        mEditBtn2 = view.findViewById(R.id.edit_btn2);
        mEditIvClose = view.findViewById(R.id.edit_iv_close);
        mEditTvTitle = view.findViewById(R.id.edit_tv_title);

        mEditEtName.setText(mList.get(pos).getName());
        mEditEtLink.setText(mList.get(pos).getLink());
        mEditBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });

        mEditBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2020/3/11 执行更新操作,然后需要刷新列表(清除之前的数据，重新获取)
                presenter.editWeb(mList.get(pos).getId(), mEditEtName.getText().toString(), mEditEtLink.getText().toString());
                show.dismiss();
                mList.clear();
            }
        });

        mEditIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    public void delPop(final int pos) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.del_item, null, false);
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setContentView(view);
        //是否需要点击PopupWindow外部其他界面时候消失
//        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.pop_window_bg));
        popupWindow.setOutsideTouchable(true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        //重新设置大小
        popupWindow.setHeight(60);
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        popupWindow.setWidth(width * 3 / 4);


        popupWindow.showAtLocation(mCollectwebRv, Gravity.CENTER, 0, 0);

        dimBackground(1f, 0.6f);

        TextView tv = view.findViewById(R.id.del_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/3/11 增加一个popupwindow，用来处理删除逻辑
                presenter.delWeb(mList.get(pos).getId());
                mList.clear();
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1f);
            }
        });
    }

    /**
     * 设置窗口的背景透明度
     *
     * @param f 0.0-1.0
     */
    private void bgAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    private void dimBackground(final float from, final float to) {
        final Window window = getActivity().getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = (Float) animation.getAnimatedValue();
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setAttributes(params);
            }
        });
        valueAnimator.start();
    }
}
