package org.tyj.ddz.mapper;

import org.apache.ibatis.annotations.*;
import org.tyj.ddz.bean.ShareTask;
import org.tyj.ddz.bean.UserInfo;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {


    @Select("select * from kys_user where user_id=#{userid}")
    UserInfo queryUserInfoByUserid(int userid);

    @Select("select tel from kys_user where user_id=#{userid}")
    String querytel(int userid);

    @Insert("insert into kys_user(uid,  openid, reg_time) values (#{uid}, #{openid}, #{regTime})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
//加入该注解可以保持对象后，查看对象插入id
    int insertUserInfo(UserInfo user);

    @Select("select * from kys_user where uid = #{uid}")
    UserInfo queryUserInfoByUid(String uid);

    @Update("update kys_user set openid = #{openid},reg_time = #{regTime} where user_id = #{userId}")
    int updateUserInfo(UserInfo userInfo);

    @Select("select a.user_id,a.finish_task,b.player_name,b.player_portrait " +
            "from kys_share_player a " +
            "left join kys_player b " +
            "on a.user_id = b.user_id " +
            "where upper_lv_one = #{userId}")
    List<ResultB> queryPartnerTask(int userId);

    @Select("select task_id,* from kys_share_task_conf")
    Map<Integer, ShareTask> queryShareTaskByUserId();


    void insertGameRecord();
}
