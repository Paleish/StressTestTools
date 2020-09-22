package tyj.ddz.handler;

import com.kys.pb.PbGate;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tyj.web.MessageSender;

/**
 * 功能描述: <br>
 * 〈登录相关逻辑处理类〉
 *
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
@Slf4j
@Component
public class GateHandler extends AbsMessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, PbGate.S2C message) {
        try {
            //处理心跳
            PbGate.Heartbeat.Builder builder1 = PbGate.Heartbeat.newBuilder();
            builder1.setTime(System.currentTimeMillis());
            MessageSender.sendSingleSuccessMsg(ctx.channel(), PbGate.ServiceType.GATEWAY_VALUE, PbGate.MSG.HEARTBEAT_VALUE, builder1.build().toByteString());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    public int getName() {
        return PbGate.ServiceType.GATEWAY_VALUE;
    }
}
