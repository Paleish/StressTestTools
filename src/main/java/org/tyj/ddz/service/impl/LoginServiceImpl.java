package org.tyj.ddz.service.impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tyj.ddz.bean.LoginInfo;
import org.tyj.ddz.bean.PlayerInfo;
import org.tyj.ddz.mapper.LoginInfoMapper;
import org.tyj.ddz.mapper.PlayerInfoMaper;
import org.tyj.ddz.mapper.UserInfoMapper;
import org.tyj.ddz.service.LoginService;

import javax.annotation.Resource;

@Service("LoginServiceImpl")
@Component
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginInfoMapper loginInfoMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private PlayerInfoMaper playerInfoMaper;

    public LoginInfo findLoginInfoByToken(String token, com.kys.util.netty.proto.LoginProto.ClientNewLogin clientLogin) {

        LoginInfo loginInfo = loginInfoMapper.queryLoginInfoByToken(token);
        return loginInfo;
    }

    public PlayerInfo findPlayerInfoByUserId(int userid) {
        PlayerInfo playerInfo = playerInfoMaper.queryPlayerInfoByUserid(userid);
        return playerInfo;
    }

    @Override
    public LoginInfo findLoginByUserId(int userId) {
        LoginInfo loginInfo = loginInfoMapper.queryLoginByUserId(userId);
        return loginInfo;
    }

    public void updateLoginInfo() {

    }

}
