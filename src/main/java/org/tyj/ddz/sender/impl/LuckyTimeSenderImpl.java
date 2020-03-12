package org.tyj.ddz.sender.impl;

import com.kys.util.netty.proto.ProtoMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tyj.ddz.handler.SignHandler;
import org.tyj.ddz.sender.LuckyTimeSender;
import org.tyj.util.ChannelUtil;
import org.tyj.web.MessageSender;

@Component
public class LuckyTimeSenderImpl implements LuckyTimeSender {

    private static final Logger logger = LoggerFactory.getLogger(LuckyTimeSenderImpl.class);

    @Override
    public void sendLuckyTimeQuery(int userId) {
        logger.info("player-{} send lottery lucky time request", userId);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), ProtoMsg.ClientRequest.newBuilder(), ProtoMsg.MessageTypeEnum.clientLTLottery);
    }
    /**
     message ServerLTLottery
     {
     *      中奖时针
     *int32 index = 1;
     *     中奖道具数量
     *int32 count = 2;
     *    剩余抽奖次数
     *int32 luckTimes = 3;
     *      道具id
     *int32 propId = 4;
     *}
     */
}
