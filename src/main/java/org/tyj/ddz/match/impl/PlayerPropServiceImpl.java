package org.tyj.ddz.match.impl;

import org.springframework.stereotype.Component;
import org.tyj.ddz.match.PlayerProp;
import org.tyj.ddz.match.PlayerPropMapper;
import org.tyj.ddz.match.PlayerPropService;

import javax.annotation.Resource;
import java.util.List;

@Component
public class PlayerPropServiceImpl implements PlayerPropService {
    @Resource
    private PlayerPropMapper playerPropMapper;

    @Override
    public List<PlayerProp> queryPlayerPropList(int userId) {
        return playerPropMapper.findPlayerPropList(userId);
    }

    @Override
    public PlayerProp queryPlayerProp(int userId, int propId) {
        return playerPropMapper.findPlayerPropById(userId, propId);
    }

    @Override
    public int changePlayerProp(int userId, int propId, int count, int type) {
//        PlayerProp pInfo = queryPlayerProp(userId, propId);
//        if (count < 0 && pInfo.getCount() < count) {
//            return -1;
//        }
        playerPropMapper.alterPropCount(userId, propId, count);
        //记录日志
        //playerPropMapper.savePropLog(userId, propId, count, type);
        return 0;
    }
}
