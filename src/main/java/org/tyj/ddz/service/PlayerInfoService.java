package org.tyj.ddz.service;

import org.tyj.ddz.bean.PlayerInfo;

import java.util.List;

public interface PlayerInfoService {

    PlayerInfo findPlayerInfoByUserId(int userid);

    void savePlayerInfo(PlayerInfo playerInfo);

    void updatePlayerInfo(PlayerInfo playerInfo);

    List<PlayerInfo> findAIMultitude(int num);
}
