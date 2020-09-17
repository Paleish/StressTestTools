/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HallHandler
 * Author:   Administrator
 * Date:     2019/7/2 17:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package tyj.ddz.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.kys.pb.HallBase;
import com.kys.pb.HallSrv;
import com.kys.pb.PbGate;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tyj.util.ChannelAttributeUtil;
import tyj.util.ChannelUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/7/2
 * @since 1.0.0
 */
@Slf4j
@Component
public class HallHandler extends AbsMessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, PbGate.S2C message) {
        try {
            int methodId = message.getCid();
            switch (HallBase.MSG.forNumber(methodId)) {
                case Login:
                    handleLogin(ctx, message);
                default:
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void handleLogin(ChannelHandlerContext ctx, PbGate.S2C message) throws InvalidProtocolBufferException {
        HallSrv.S_Login loginMsg = HallSrv.S_Login.parseFrom(message.getBody());
        log.info("玩家{}登陆成功!", loginMsg.getUserInfo().getPlayerId());
        //设置上uid等属性
        ChannelAttributeUtil.setPlayerId(ctx.channel(), loginMsg.getUserInfo().getPlayerId());
        ChannelUtil.setHallChannel(loginMsg.getUserInfo().getPlayerId(), ctx.channel());
    }

    @Override
    public int getName() {
        return PbGate.ServiceType.PLATFORM_VALUE;
    }
}