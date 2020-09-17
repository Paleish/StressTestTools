///**
// * Copyright (C), 2015-2019, XXX有限公司
// * FileName: HallHandler
// * Author:   Administrator
// * Date:     2019/7/2 17:28
// * Description:
// * History:
// * <author>          <time>          <version>          <desc>
// * TanYujie       修改时间           版本号              描述
// */
//package tyj.ddz.handler;
//
//import com.kys.util.netty.proto.HallProto;
//import com.kys.util.netty.proto.ProtoMsg;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.stereotype.Component;
//import tyj.util.ChannelAttributeUtil;
//
///**
// * 〈一句话功能简述〉<br>
// * 〈〉
// *
// * @author Administrator
// * @create 2019/7/2
// * @since 1.0.0
// */
//@Component
//public class HallHandler extends AbsMessageHandler {
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        int methodId = message.getMethodId();
//        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
//        switch (funcId) {
//            case serverNewDrawWinCup:
//                serverDraw(ctx, message);
//            default:
//                break;
//        }
//    }
//
//    private void serverDraw(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
//        HallProto.ServerNewDrawWinCup sdwc = message.getServerNewDrawWinCup();
//        logger.info("player{}lottery{},now win cup is{}，remain lottery times is {}", userId, sdwc.getDrawWinCup(), sdwc.getPlayerWinCup(), sdwc.getLeftCount());
//    }
//
//    @Override
//    public int getName() {
//        return ProtoMsg.ModelIdEnum.HALL_ID.getNumber();
//    }
//}