/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ServerMsgHandler
 * Author:   Administrator
 * Date:     2019/6/21 16:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.handler;


import com.kys.util.netty.proto.GameProto;
import com.kys.util.netty.proto.ProtoMsg;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import org.tyj.ddz.ai.AIManager;
import org.tyj.util.ChannelAttributeUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/21
 * @since 1.0.0
 */
@Component
public class GameHandler extends AbsMessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int methodId = message.getMethodId();
        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
        switch (funcId) {
            case serverEnterRoom:
                enterRoom(ctx, message);
                break;
            default:
                break;
        }
    }

    private void enterRoom(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());

        GameProto.ServerEnterRoom ser = message.getServerEnterRoom();
        for (GameProto.PlayerInfoInGame temp : ser.getPlayerInfoList()
        ) {
            if (temp.getUserinfo().getPlayerId() == userId) {
                AIManager.aiMap.get(userId).setSeatId(temp.getSeatID());
                logger.info("player{}receive enterRoom message,seatId is {}", userId, temp.getSeatID());
            }
        }
    }

    @Override
    public int getName() {
        return ProtoMsg.ModelIdEnum.GAME_ID.getNumber();
    }

}