package org.tyj.ddz.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: <br>
 * 〈用户信息，用于登陆校验，以及重要信息储存〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -576051919625683123L;

    private int userId;//自增用户ID
    private String uid;//三方登录时返回的第三方平台的ID，微信(unionid)，QQ(openid)
    private  String openid;
    private String tel;//电话
    private Date regTime;//注册时间(精确到秒的时间戳)
    public UserInfo(int userId, String uid, Date regTime) {
        this.userId = userId;
        this.uid = uid;
        this.regTime = regTime;
    }

    public UserInfo(String uid, String openid, Date regTime) {
        this.openid = openid;
        this.uid = uid;
        this.regTime = regTime;
    }

    public UserInfo (){

    };

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
}
