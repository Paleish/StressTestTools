package org.tyj.ddz.service;

import org.tyj.ddz.bean.LoginInfo;

public interface LoginInfoService {


    LoginInfo findLoginInfoByToken(String token);

    void updateLoginInfo(com.kys.util.netty.proto.LoginProto.ClientNewLogin clientLogin, LoginInfo loginInfo);

    void saveLoginInfo(LoginInfo loginInfo);

    LoginInfo findLoginInfoByUserId(int userid);


}
