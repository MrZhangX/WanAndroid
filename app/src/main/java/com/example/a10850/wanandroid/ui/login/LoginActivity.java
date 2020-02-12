package com.example.a10850.wanandroid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.ui.register.RegisterActivity;
import com.example.common.base.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 创建时间：2020/2/10 17:53
 * 创建人：10850
 * 功能描述：
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

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

    private PersonBean mPersonBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mPersonBean = new PersonBean();

        mEtUsername.setFocusable(true);
        mEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtPassword.getText().toString().trim().length() > 0 && s.length() > 0) {
                    mBtnLogin.setEnabled(true);
                } else {
                    mBtnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtUsername.getText().toString().trim().length() > 0 && s.length() > 0) {
                    mBtnLogin.setEnabled(true);
                } else {
                    mBtnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    open();
            }
        });

        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    close();
            }
        });

    }

    @Override
    public PersonBean getUserInfo() {
        return mPersonBean;
    }

    @Override
    public void loginSuccess(PersonBean user) {
        if (user.getData() == null && !TextUtils.isEmpty(user.getErrorMsg()))
            Toast.makeText(this, "" + user.getErrorMsg(), Toast.LENGTH_SHORT).show();
        else {
            //Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
            // TODO: 2020/2/10 保存用户信息，界面finish
            finish();
        }
    }

    @Override
    public void loginFailure(String msg) {
        showLoading(msg);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forgetpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkLogin();
                presenter.login();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forgetpassword:
                break;
        }
    }

    //检查输入
    private void checkLogin() {
        if (TextUtils.isEmpty(mEtUsername.getText().toString())) {
            Toast.makeText(this, "请输入账号!", Toast.LENGTH_SHORT).show();
            mEtUsername.setFocusable(true);
            return;
        }

        if (TextUtils.isEmpty(mEtPassword.getText().toString())) {
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
            mEtPassword.setFocusable(true);
        } else if (mEtPassword.getText().toString().length() < 6) {
            Toast.makeText(LoginActivity.this, "密码长度必须大于6位！", Toast.LENGTH_SHORT).show();
            return;
        }

        PersonBean.DataBean data = new PersonBean.DataBean();

        data.setUsername(mEtUsername.getText().toString());
        data.setPassword(mEtPassword.getText().toString());
        mPersonBean.setData(data);
    }


    public void close() {
        //左边
        RotateAnimation lAnim = new RotateAnimation(0,
                170,
                mIvLeftArm.getWidth(),
                0f);
        lAnim.setDuration(500);
        lAnim.setFillAfter(true);

        mIvLeftArm.startAnimation(lAnim);

        RotateAnimation rAnim = new RotateAnimation(0,
                -170,
                0f,
                0f);
        rAnim.setDuration(500);
        rAnim.setFillAfter(true);

        mIvRightArm.startAnimation(rAnim);

        TranslateAnimation down = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.hand_down_translate);
        mIvLeftHand.startAnimation(down);
        mIvRightHand.startAnimation(down);
    }


    public void open() {
        //左边
        RotateAnimation lAnim = new RotateAnimation(170,
                0,
                mIvLeftArm.getWidth(),
                0f);
        lAnim.setDuration(500);
        lAnim.setFillAfter(true);

        mIvLeftArm.startAnimation(lAnim);

        RotateAnimation rAnim = new RotateAnimation(-170,
                0,
                0f,
                0f);
        rAnim.setDuration(500);
        rAnim.setFillAfter(true);

        mIvRightArm.startAnimation(rAnim);

        TranslateAnimation down = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.hand_up_translate);
        mIvLeftHand.startAnimation(down);
        mIvRightHand.startAnimation(down);
    }
}
