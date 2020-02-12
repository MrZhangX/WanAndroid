package com.example.a10850.wanandroid.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a10850.wanandroid.MainActivity;
import com.example.a10850.wanandroid.R;
import com.example.a10850.wanandroid.entity.PersonBean;
import com.example.a10850.wanandroid.utils.SPUtils;
import com.example.common.base.BaseMvpActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***
 * 创建时间：2020/2/10 20:46
 * 创建人：10850
 * 功能描述：
 */
public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.register_user)
    EditText mRegisterUser;
    @BindView(R.id.register_user_l)
    TextInputLayout mRegisterUserL;
    @BindView(R.id.register_pwd)
    EditText mRegisterPwd;
    @BindView(R.id.register_pwd_l)
    TextInputLayout mRegisterPwdL;
    @BindView(R.id.register_repwd)
    EditText mRegisterRepwd;
    @BindView(R.id.register_repwd_l)
    TextInputLayout mRegisterRepwdL;

    private Map<String, String> mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mMap = new HashMap<>();
        //用户名长度不能超过15，密码不能少于6，再次输入密码必须和密码一致
        mRegisterUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 15) {
                    mRegisterUserL.setError("用户名长度不能超过15!");
                    mRegisterUserL.setErrorEnabled(true);
                } else
                    mRegisterUserL.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mRegisterPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    mRegisterPwdL.setError("密码长度不能小于6!");
                    mRegisterPwdL.setErrorEnabled(true);
                } else
                    mRegisterPwdL.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mRegisterRepwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRepwdL.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public Map getUserInfo() {
        return mMap;
    }

    private Map checkRegister() {
        if (TextUtils.isEmpty(mRegisterUser.getText().toString())) {
            Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (TextUtils.isEmpty(mRegisterPwd.getText().toString())) {
            Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (TextUtils.isEmpty(mRegisterRepwd.getText().toString())) {
            Toast.makeText(this, "再次输入密码不能为空!", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (!mRegisterRepwd.getText().toString().equals(mRegisterPwd.getText().toString())) {
            mRegisterRepwdL.setError("密码输入不正确!");
            mRegisterRepwdL.setErrorEnabled(true);
            return null;
        }

        mMap.put("username", mRegisterUser.getText().toString());
        mMap.put("password", mRegisterPwd.getText().toString());
        mMap.put("repassword", mRegisterRepwd.getText().toString());
        return mMap;
    }

    /***
     * 注册成功->跳转到主界面就行了
     * @param user
     */
    @Override
    public void registerSuccess(PersonBean user) {
        //Log.i("zxd", "register: " + user.getErrorMsg());
        //Log.i("zxd", "register: " + user.getErrorCode());
        if (user.getErrorMsg().equals("用户名已经被注册！") || user.getErrorCode() == -1) {
            Toast.makeText(this, "" + user.getErrorMsg(), Toast.LENGTH_SHORT).show();
        } else {
            //Log.i("zxd", "registerSuccess: " + user.getData().getUsername());
            Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
            SPUtils.put(this, "user", user.getData());
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void registerFailure(String msg) {
        showLoading(msg);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @OnClick(R.id.register_btn)
    public void onViewClicked() {
        Map map = checkRegister();
        if (map != null)
            presenter.register();
    }
}
