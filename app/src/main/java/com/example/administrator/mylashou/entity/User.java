package com.example.administrator.mylashou.entity;

public class User {
    private String id;
    private String name;
    private String logPwd;
    private String payPwd;
    private String tel;
    private String count_money;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int  getCount_money() {
        if(count_money!=null){
            return Integer.getInteger(count_money);
        }else {
            return 0 ;
        }

    }

    public void setCount_money(String count_money) {
        this.count_money = count_money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogPwd() {
        return logPwd;
    }

    public void setLogPwd(String logPwd) {
        this.logPwd = logPwd;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
