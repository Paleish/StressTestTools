package org.tyj.ddz.mapper;

import org.apache.ibatis.annotations.*;
import org.tyj.ddz.bean.LoginInfo;

@Mapper
public interface LoginInfoMapper {

    @Select("select * from kys_login where token=#{token}")
    LoginInfo queryLoginInfoByToken(String token);

    @Select("select * from kys_login where user_id=#{userid}")
    LoginInfo queryLoginByUserId(int userid);

    @Insert("insert into kys_login(token, user_id, login_time, token_expire_time, login_type, device_id, device_platform, os_name, os_version) " +
            "values (#{token}, #{userId}, #{loginTime}, #{tokenExpireTime}, #{loginType}, #{deviceId}, #{devicePlatform}, #{osName}, #{osVersion})")
    @Options(useGeneratedKeys = true,keyProperty = "userId", keyColumn = "user_id")//加入该注解可以保持对象后，查看对象插入id
    int insertLoginInfo(LoginInfo loginInfo);

    @Update("update kys_login set token = #{token}, login_time = #{loginTime}, login_type = #{loginType}, device_id = #{deviceId}," +
            " device_platform = #{devicePlatform}, os_name = #{osName}, os_version = #{osVersion}, token_expire_time = #{tokenExpireTime} where user_id = #{userId}")
    int updateLonginInfo(LoginInfo loginInfo);
}
