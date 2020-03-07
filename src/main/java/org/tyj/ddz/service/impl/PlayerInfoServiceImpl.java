package org.tyj.ddz.service.impl;

import org.springframework.stereotype.Service;
import org.tyj.Initializer;
import org.tyj.ddz.bean.PlayerInfo;
import org.tyj.ddz.mapper.PlayerInfoMaper;
import org.tyj.ddz.service.PlayerInfoService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlayerInfoServiceImpl implements PlayerInfoService {

    @Resource
    private PlayerInfoMaper playerInfoMaper;

    public PlayerInfo findPlayerInfoByUserId(int userid) {
        PlayerInfo playerInfo = playerInfoMaper.queryPlayerInfoByUserid(userid);
        return playerInfo;
    }

    public void savePlayerInfo(PlayerInfo playerInfo) {
        playerInfoMaper.insertLoginInfo(playerInfo);
    }

    public void updatePlayerInfo(PlayerInfo playerInfo) {
        playerInfoMaper.updatePlayerPortrait(playerInfo);
    }

    @Override
    public List<PlayerInfo> findAIMultitude(int num) {
        return playerInfoMaper.findAIMultitude(Initializer.AI_SIGN, num);
    }

}
