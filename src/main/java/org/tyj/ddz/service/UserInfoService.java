package org.tyj.ddz.service;

import org.tyj.ddz.bean.UserInfo;

public interface UserInfoService {

    UserInfo saveUserInfo(UserInfo userInfo);

    UserInfo findUserInfoByUid(String uid);

    void updateUserInfo(UserInfo userInfo);

    UserInfo getUserInfoByUserId(int userId);

}
