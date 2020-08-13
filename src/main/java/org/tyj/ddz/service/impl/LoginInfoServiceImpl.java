package org.tyj.ddz.service.impl;


import org.springframework.stereotype.Service;
import org.tyj.ddz.bean.LoginInfo;
import org.tyj.ddz.mapper.LoginInfoMapper;
import org.tyj.ddz.service.LoginInfoService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    @Resource
    private LoginInfoMapper loginInfoMapper;

    public LoginInfo findLoginInfoByToken(String token) {

        LoginInfo loginInfo = loginInfoMapper.queryLoginInfoByToken(token);
        return loginInfo;
    }

    public void updateLoginInfo(com.kys.util.netty.proto.LoginProto.ClientNewLogin clientLogin, LoginInfo loginInfo) {
        loginInfo.setLoginTime(new Date());
        loginInfo.setDeviceId(UUID.randomUUID().toString().substring(0, 16));
        loginInfo.setDevicePlatform("ios");
        loginInfo.setLoginType("wechat");
        loginInfo.setOsName("ios");
        loginInfo.setOsVersion("11.0");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 30);
        Date expireTime = calendar.getTime();
        loginInfo.setTokenExpireTime(expireTime);
        loginInfoMapper.updateLonginInfo(loginInfo);
    }

    public void saveLoginInfo(LoginInfo loginInfo) {
        loginInfo.setLoginTime(new Date());
        loginInfo.setDeviceId(UUID.randomUUID().toString().substring(0, 16));
        loginInfo.setDevicePlatform("ios");
        loginInfo.setLoginType("wechat");
        loginInfo.setOsName("ios");
        loginInfo.setOsVersion("11.0");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 30);
        Date expireTime = calendar.getTime();
        loginInfo.setTokenExpireTime(expireTime);

        loginInfoMapper.insertLoginInfo(loginInfo);
    }

    public LoginInfo findLoginInfoByUserId(int userid) {
        LoginInfo loginInfo = loginInfoMapper.queryLoginByUserId(userid);
        return loginInfo;
    }

}
