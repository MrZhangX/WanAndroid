package com.example.a10850.wanandroid.entity;

import java.util.List;

/***
 * 创建时间：2020/2/3 18:40
 * 创建人：10850
 * 功能描述：首页-置顶
 */
public class ContentZDBean {

    private int errorCode;
    private String errorMsg;
    private List<ContentBean> data;

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

    public List<ContentBean> getData() {
        return data;
    }

    public void setData(List<ContentBean> data) {
        this.data = data;
    }
}
