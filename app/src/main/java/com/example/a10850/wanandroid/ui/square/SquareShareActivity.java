package com.example.a10850.wanandroid.ui.square;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10850.wanandroid.R;
import com.example.common.base.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SquareShareActivity extends BaseMvpActivity<SquarePresenter> implements SquareContract.View {

    @BindView(R.id.share)
    TextView mShare;
    @BindView(R.id.et_share_title)
    EditText mEtShareTitle;
    @BindView(R.id.et_share_url)
    EditText mEtShareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_share);
        ButterKnife.bind(this);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEtShareTitle.getText().toString()) &&
                        !TextUtils.isEmpty(mEtShareUrl.getText().toString()))
                    presenter.shareArticle(mEtShareTitle.getText().toString(),
                            mEtShareUrl.getText().toString());
                else
                    Toast.makeText(SquareShareActivity.this, "请正确填写!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected SquarePresenter createPresenter() {
        return new SquarePresenter();
    }

    @Override
    public void requestSuccess(Object o) {
        Log.i("zxd", "requestSuccess: " + o.toString());
        if (o.toString().equals("请先登录！")) {
            //{"errorCode":-1001,"errorMsg":"请先登录！"}
            Toast.makeText(this, o.toString(), Toast.LENGTH_SHORT).show();
        } else {
            //{"data":null,"errorCode":0,"errorMsg":""}
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void requestFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
