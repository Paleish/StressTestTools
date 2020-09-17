//package tyj.ddz.handler;
//
//import com.kys.util.netty.proto.LoginProto;
//import com.kys.util.netty.proto.ProtoMsg;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import tyj.ddz.ai.AIAsync;
//import tyj.util.ChannelAttributeUtil;
//import tyj.web.MessageSender;
//
//import javax.annotation.Resource;
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
//public class UserHandler extends AbsMessageHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
//
//    @Resource
//    private AIAsync aiAsync;
//
//    static {
//        ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        // 检查IP地址
//        int methodId = message.getMethodId();
//        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
//        switch (funcId) {
//            case serverSendHallHeartBeat:
//                receiveHeart(ctx, message);
//                break;
//            case serverLogin:
//                dealLoginInfo(ctx, message);
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void dealLoginInfo(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
//        logger.info("AI{} receive login msg response!", userId);
//        if (message.getResult() != ProtoMsg.ResultCode.success) {
//            logger.error("AI{}login failed！", userId);
//        } else {
//            logger.info("AI{}login success！", userId);
//        }
//        LoginProto.ServerLogin sl = message.getServerLogin();
//        if (sl.getUserId() != userId) {
//            logger.error("AI login token expired,please check your database!");
//        }
//    }
//
//    @Override
//    public int getName() {
//        return ProtoMsg.ModelIdEnum.LOGIN_ID.getNumber();
//    }
//
//    /**
//     * 查询数据库比较耗时，采用多线程
//     */
//    @Override
//    public boolean newThread() {
//        return true;
//    }
//
//    /**
//     * 处理心跳消息:
//     * 心跳时间不超过30s的继续加入connectedPlayers，进行下一次心跳
//     *
//     * @param ctx
//     * @param message
//     */
//    public void receiveHeart(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
//        //logger.info("player{}send heart beat message", userId);
//        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
//        LoginProto.ClientReturnHallHeartBeat.Builder chh = LoginProto.ClientReturnHallHeartBeat.newBuilder();
//        chh.setServiceTime(message.getServerSendHallHeartBeat().getServiceTime());
//        cr.setClientReturnHallHeartBeat(chh);
//        MessageSender.sendSingleSuccessMsg(ctx.channel(), cr, ProtoMsg.MessageTypeEnum.clientReturnHallHeartBeat);
//    }
//}
