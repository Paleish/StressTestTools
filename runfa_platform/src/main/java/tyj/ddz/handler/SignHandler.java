//package tyj.ddz.handler;
//
//import com.kys.util.netty.proto.LoginProto;
//import com.kys.util.netty.proto.ProtoMsg;
//import com.kys.util.netty.proto.SignProto;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.stereotype.Component;
//import tyj.ddz.sender.LuckyTimeSender;
//import tyj.util.ChannelAttributeUtil;
//
//import javax.annotation.Resource;
//import java.util.Random;
//
///**
// * 功能描述: <br>
// * 〈登录相关逻辑处理类〉
// *
// * @return:
// * @since: 1.0.0
// * @Author:
// * @Date:
// */
//@Component
//public class SignHandler extends AbsMessageHandler {
//
//    @Resource
//    private LuckyTimeSender luckyTimeSender;
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        // 检查IP地址
//        int methodId = message.getMethodId();
//        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
//        switch (funcId) {
//            case serverLTLottery:
//                handLTLottery(ctx, message);
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void handLTLottery(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        SignProto.ServerLTLottery ltResponse = message.getServerLTLottery();
//        logger.info("");
//        if (ltResponse.getLuckTimes() > 0) {
//            int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
//            Random rd = new Random();
//            try {
//                Thread.sleep(rd.nextInt(10000) + 2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            luckyTimeSender.sendLuckyTimeQuery(userId);
//        }
//    }
//
//    @Override
//    public int getName() {
//        return ProtoMsg.ModelIdEnum.SIGNUP_ID_VALUE;
//    }
//}
