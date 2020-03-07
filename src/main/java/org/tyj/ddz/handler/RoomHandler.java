package org.tyj.ddz.handler;

import com.kys.util.netty.proto.GameProto;
import com.kys.util.netty.proto.ProtoMsg;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tyj.util.ChannelAttributeUtil;

/**
 * 功能描述: <br>
 * 〈登录相关逻辑处理类〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
@Component
public class RoomHandler extends AbsMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(RoomHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int methodId = message.getMethodId();
        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
        switch (funcId) {
            case clientQuickJoin:
                clientQuickJoin(ctx, message);
            case serverQuickJoin:
                quickJoin(ctx, message);
                break;
            default:
                break;
        }
    }

    private void clientQuickJoin(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        if (message.getResult() != ProtoMsg.ResultCode.success) {
            logger.error("AI{}加入房间失败,错误码{}！", userId, message.getResult());
        }
    }

    private void quickJoin(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        logger.info("AI{}收到加入房间消息", userId);
        if (message.getResult() != ProtoMsg.ResultCode.success) {
            logger.error("AI{}加入房间失败,错误码{}！", userId, message.getResult());
        } else {
            GameProto.ServerQuickJoin sqj = message.getServerQuickJoin();
            logger.info("AI{}加入房间队列成功,等待时间为{}！", userId, sqj.getWaitTime());
        }
    }

    @Override
    public int getName() {
        return ProtoMsg.ModelIdEnum.ROOM_ID.getNumber();
    }
}
