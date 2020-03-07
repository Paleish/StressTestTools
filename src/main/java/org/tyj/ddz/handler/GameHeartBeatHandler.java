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

import com.kys.util.netty.proto.LoginProto;
import com.kys.util.netty.proto.ProtoMsg;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import org.tyj.web.MessageSender;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/21
 * @since 1.0.0
 */
@Component
public class GameHeartBeatHandler extends AbsMessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int methodId = message.getMethodId();
        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
        switch (funcId) {
            case serverSendGameHeartBeat:
                gameHeartBeat(ctx, message);
                break;
            default:
                break;
        }
    }

    private void gameHeartBeat(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
        LoginProto.ClientReturnGameHeartBeat.Builder chh = LoginProto.ClientReturnGameHeartBeat.newBuilder();
        chh.setServiceTime(message.getServerSendGameHeartBeat().getServiceTime());
        cr.setClientReturnGameHeartBeat(chh);
        MessageSender.sendSingleSuccessMsg(ctx.channel(), cr, ProtoMsg.MessageTypeEnum.clientReturnGameHeartBeat);
    }

    @Override
    public int getName() {
        return ProtoMsg.ModelIdEnum.GAME_HEART.getNumber();
    }

}