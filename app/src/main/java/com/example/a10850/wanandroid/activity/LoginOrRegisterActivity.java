package com.example.a10850.wanandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.constant.UrlString;
import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.entity.UserBean;
import com.example.a10850.wanandroid.interfaces.ApiService;
import com.example.a10850.wanandroid.login.LoginPresenter;
import com.example.a10850.wanandroid.login.LoginView;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginOrRegisterActivity extends AppCompatActivity implements LoginView, TextWatcher {

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.iv_left_hand)
    ImageView mIvLeftHand;
    @BindView(R.id.iv_right_hand)
    ImageView mIvRightHand;
    @BindView(R.id.iv_left_arm)
    ImageView mIvLeftArm;
    @BindView(R.id.iv_right_arm)
    ImageView mIvRightArm;

    private LoginPresenter mPresenter;
    private UserBean mUserBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        ButterKnife.bind(this);
        initView();
    }

    /***
     * 初始化view
     */
    private void initView() {
        mPresenter = new LoginPresenter(this);

        mEtPassword.addTextChangedListener(this);
        mEtUsername.addTextChangedListener(this);

        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    //用手捂住眼睛
                    close();
                } else {
                    //放开手
                    open();
                }
            }
        });
    }

    @Override
    public void onRegisterSucceed() {
        Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterFaild() {
        Toast.makeText(this, "注册失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSucceed() {
        Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFaild() {
        Toast.makeText(this, "登录失败!", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forgetpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                Log.e("zxd", "onViewClicked: " + mEtUsername.getText().toString());
//                Log.e("zxd", "onViewClicked: " + mEtPassword.getText().toString());
                mUserBean = new UserBean(mEtUsername.getText().toString(), mEtPassword.getText().toString());
                mPresenter.loginPerson(mUserBean);
//                testRetrRxjava();
//                testGetBean();
                break;
            case R.id.tv_register:

                break;
            case R.id.tv_forgetpassword:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter = null;
    }


    /**
     * 当有控件获得焦点focus 自动弹出键盘
     * 1. 点击软键盘的enter键 自动收回键盘
     * 2. 代码控制 InputMethodManager
     * requestFocus
     * showSoftInput:显示键盘 必须先让这个view成为焦点requestFocus
     * <p>
     * hideSoftInputFromWindow 隐藏键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //隐藏键盘
            //1.获取系统输入的管理器
            InputMethodManager inputManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            //2.隐藏键盘
            inputManager.hideSoftInputFromWindow(mEtUsername.getWindowToken(), 0);

            //3.取消焦点
            View focusView = getCurrentFocus();
            if (focusView != null) {
                focusView.clearFocus(); //取消焦点
            }
            //getCurrentFocus().clearFocus();
            //focusView.requestFocus();//请求焦点
        }
        return true;
    }

    public void close() {
        //左边
        RotateAnimation lAnim = new RotateAnimation(0, 175, mIvLeftArm.getWidth(), 0f);
        lAnim.setDuration(500);
        lAnim.setFillAfter(true);

        mIvLeftArm.startAnimation(lAnim);

        RotateAnimation rAnim = new RotateAnimation(0, -175, 0f, 0f);
        rAnim.setDuration(500);
        rAnim.setFillAfter(true);

        mIvRightArm.startAnimation(rAnim);

        TranslateAnimation down = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.hand_down_translate);
        mIvLeftHand.startAnimation(down);
        mIvRightHand.startAnimation(down);
    }

    public void open() {
        mIvLeftArm.animate().rotation(180);
        mIvRightArm.animate().rotation(-180);
        //左边
        RotateAnimation lAnim = new RotateAnimation(175, 0, mIvLeftArm.getWidth(), 0f);
        lAnim.setDuration(500);
        lAnim.setFillAfter(true);

        mIvLeftArm.startAnimation(lAnim);

        RotateAnimation rAnim = new RotateAnimation(-175, 0, 0f, 0f);
        rAnim.setDuration(500);
        rAnim.setFillAfter(true);

        mIvRightArm.startAnimation(rAnim);

        TranslateAnimation up = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.hand_up_translate);
        mIvLeftHand.startAnimation(up);
        mIvRightHand.startAnimation(up);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //判断两个输入框是否有内容
        if (mEtUsername.getText().toString().length() > 0 &&
                mEtPassword.getText().toString().length() > 0) {
            //按钮可以点击
            mBtnLogin.setEnabled(true);
        } else {
            //按钮不能点击
            mBtnLogin.setEnabled(false);
        }
    }

    /***
     * 用于测试，得到bean
     */
    private void testGetBean() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlString.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.onLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
//                            Log.e("zxd", "onResponse: " + response.body().string());
                    PersonBean bean = new Gson().fromJson(response.body().string(), PersonBean.class);
                    if (bean.getData() != null)
                        Log.e("zxd", "Username: " + bean.getData().getUsername());
                    else
                        Log.e("zxd", ": " + bean.getErrorMsg());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("zxd", "onFailure: " + t.getMessage());
            }
        });
    }

    /***
     * rxjava+retrofit
     */
    private void testRetrRxjava() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlString.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<PersonBean> login = apiService.onLogin1(mEtUsername.getText().toString(), mEtPassword.getText().toString());
        login.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PersonBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PersonBean personBean) {
                Log.e("zxd", "onNext: " + personBean.getData().getUsername());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("zxd", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
