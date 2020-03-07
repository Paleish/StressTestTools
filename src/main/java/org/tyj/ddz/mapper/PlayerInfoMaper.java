package org.tyj.ddz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.tyj.ddz.bean.PlayerInfo;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PlayerInfoMaper {

    @Select("select * from kys_player where user_id=#{userId}")
    PlayerInfo queryPlayerInfoByUserid(int userId);

    @Insert("insert into kys_player(user_id, player_name, player_portrait, goldcoin, " +
            "is_AI, diamond, wincup, win_times, player_sex, player_sign, player_exp, play_times, signup_days, last_signup) " +
            "values " +
            "(#{userId}, #{playerName}, #{playerPortrait}, #{goldcoin}, " +
            "#{isAI}, #{diamond}, #{wincup}, #{winTimes}, #{playerSex}, #{playerSign}, #{playerExp}, #{playTimes}, #{signupDays}, #{lastSignup})")
    int insertLoginInfo(PlayerInfo playerInfo);

    @Update("update kys_player set player_portrait = #{playerPortrait} where user_id = #{userId}")
    int updatePlayerPortrait(PlayerInfo playerInfo);

    @Update("update kys_player set goldcoin = #{goldcoin}, diamond = #{diamond}, " +
            "record_end_time = #{recordEndTime}, signup_days = #{signupDays}, " +
            "last_signup = #{lastSignup} " +
            "where " +
            "user_id = #{userId}")
    int updateSignupInfo(PlayerInfo playerInfo);

    @Select("select * from kys_player where is_AI = #{param1} limit #{param2}")
    List<PlayerInfo> findAIMultitude(int aiSign, int num);

    @Select("select wincup from kys_player where user_id = #{userId}")
    BigDecimal queryWinCupByUserId(long userId);

    @Update("update kys_player set wincup = #{wincup} where user_id = #{userId}")
    int updateWinCupByUserId(BigDecimal wincup, int userId);

    @Select("select player_name from kys_player where user_id = #{userId}")
    String queryPlayerNameByUserId(int userId);

    @Update("update kys_player set player_name = #{param2} where user_id = #{param1}")
    void updatePlayerName(int userId, String name);
}
