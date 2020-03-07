package org.tyj.ddz.service;

import org.tyj.ddz.bean.LoginInfo;
import org.tyj.ddz.bean.PlayerInfo;

public interface LoginService {

    LoginInfo findLoginInfoByToken(String token, com.kys.util.netty.proto.LoginProto.ClientLogin clientLogin);

    PlayerInfo findPlayerInfoByUserId(int userid);

    LoginInfo findLoginByUserId(int userId);

    void updateLoginInfo();

}
