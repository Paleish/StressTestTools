package tyj.ddz.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: <br>
 * 〈用户登陆信息〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */

public class LoginInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4738883774076394077L;
    private String token;//token
    private int userId;//用户ID,与user表一致
    private Date loginTime;//最后登陆时间
    private Date tokenExpireTime;//token过期时间
    private String loginType;//登陆类型，前期只有微信(wx,qq,tel)
    private String deviceId;//设备唯一标示符
    private String devicePlatform;//设备类型（android: 安卓，ios:  苹果，h5: 网页版，wxApp: 微信小程序）
    private String osName;//系统名称
    private String osVersion;//系统版本

    public LoginInfo() {

    }

    public LoginInfo(String token, int userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Date tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
