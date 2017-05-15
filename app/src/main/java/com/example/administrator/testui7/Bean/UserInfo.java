package com.example.administrator.testui7.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by kongzeya on 2017/3/25.
 * the information about user.
 * include webuserid(userid) score deposit(money) userpic(pic) and so on.
 */

public class UserInfo extends BmobObject {

    private String pic;
    private String userid;
    private Double money;
    private Integer stid;

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

    public Integer getStid() {
        return stid;
    }

    public void setStid(Integer stid) {
        this.stid = stid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}