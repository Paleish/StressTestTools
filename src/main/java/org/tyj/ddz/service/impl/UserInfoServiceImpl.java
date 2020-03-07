package org.tyj.ddz.service.impl;

import org.springframework.stereotype.Service;
import org.tyj.ddz.bean.UserInfo;
import org.tyj.ddz.mapper.UserInfoMapper;
import org.tyj.ddz.service.UserInfoService;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfo saveUserInfo(UserInfo userInfo) {
        userInfoMapper.insertUserInfo(userInfo);
        return userInfo;
    }

    public UserInfo findUserInfoByUid(String uid) {
        UserInfo userInfo = userInfoMapper.queryUserInfoByUid(uid);
        return userInfo;

    }

    public  void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateUserInfo(userInfo);
    }

    @Override
    public UserInfo getUserInfoByUserId(int userId) {
        UserInfo userInfo = userInfoMapper.queryUserInfoByUserid(userId);
        return userInfo;
    }


}
