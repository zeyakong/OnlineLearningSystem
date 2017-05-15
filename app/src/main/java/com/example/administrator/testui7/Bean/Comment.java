package com.example.administrator.testui7.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class Comment extends BmobObject {
String userid;
String commentInfer;
    Integer likeNum;
    String videoid;

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCommentInfer() {
        return commentInfer;
    }

    public void setCommentInfer(String commentInfer) {
        this.commentInfer = commentInfer;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
}
