package com.example.administrator.testui7.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by kongzeya on 2017/4/6.
 * take notes of recharging.
 */

public class AddHistory extends BmobObject{
    private String userid;
    private Double money;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
