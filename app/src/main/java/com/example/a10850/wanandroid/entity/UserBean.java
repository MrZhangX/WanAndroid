package com.example.a10850.wanandroid.entity;

/***
 * 创建时间：2019/12/6 10:01
 * 创建人：10850
 * 功能描述：(1)首先我们需要一个UserBean，用来保存用户信息
 * 功能：登录时将请求参数保存成一个bean对象
 */
public class UserBean {
    private String user;
    private String password;
    private String repassword;

    public UserBean(String user, String password) {
        this.user = user;
        this.password = password;
    }


    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getRepassword() {
        return repassword;
    }
}
