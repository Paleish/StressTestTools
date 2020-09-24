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

import com.kys.pb.LandlordBase;
import com.kys.pb.LandlordCli;
import com.kys.pb.LandlordPush;
import com.kys.pb.PbGate;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tyj.util.ChannelAttributeUtil;
import tyj.web.MessageSender;

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
public class LandLordHandler extends AbsMessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, PbGate.S2C message) {
        try {
            int methodId = message.getCid();
            switch (LandlordBase.MSG.forNumber(methodId)) {
                case B_RoomIdUrl:
                    handleRoomId(ctx, message);
                    break;
                case B_GameSettle:
                    handleGameSettle(ctx, message);
                    break;
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void handleGameSettle(ChannelHandlerContext ctx, PbGate.S2C message) throws Exception {
        Thread.sleep(10000);
        //再次加入游戏
        LandlordCli.C_QuickJoin.Builder builder = LandlordCli.C_QuickJoin.newBuilder();
        builder.setChipLevel(1);
        builder.setIsContinue(true);
        MessageSender.sendSingleSuccessMsg(ctx.channel(), PbGate.ServiceType.LANDLORD_VALUE, LandlordBase.MSG.QuickJoin_VALUE, builder.build().toByteString());
    }

    private void handleRoomId(ChannelHandlerContext ctx, PbGate.S2C message) throws Exception {
        LandlordPush.P_RoomIdUrl resp = LandlordPush.P_RoomIdUrl.parseFrom(message.getBody());
        log.info("玩家{}加入房间{}进行游戏", ChannelAttributeUtil.getPlayerId(ctx.channel()), resp.getRoomId());
    }

    @Override
    public int getName() {
        return PbGate.ServiceType.LANDLORD_VALUE;
    }
}