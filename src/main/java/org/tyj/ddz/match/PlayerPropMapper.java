package org.tyj.ddz.match;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlayerPropMapper {

    @Select("select * from kys_player_prop where user_id = #{userId}")
    List<PlayerProp> findPlayerPropList(int userId);

    @Select("select * from kys_player_prop where user_id = #{userId} and prop_id = #{propId} limit 1")
    PlayerProp findPlayerPropById(int userId, int propId);

    @Insert("insert INTO kys_player_prop (user_id,prop_id,count) VALUES (#{userId},#{propId},#{count}) ON DUPLICATE KEY UPDATE count = count + #{count}")
    void alterPropCount(int userId, int propId, int count);

    @Insert("insert into kys_prop_log (user_id,prop_id,type,count)values(#{userId},#{propId},#{count},#{type})")
    void savePropLog(int userId, int propId, int count, int type);
}
