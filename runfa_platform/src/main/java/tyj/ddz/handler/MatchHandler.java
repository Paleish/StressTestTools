//package tyj.ddz.handler;
//
//import com.kys.util.netty.proto.MatchProto;
//import com.kys.util.netty.proto.ProtoMsg;
//import com.kys.util.netty.proto.SignProto;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.stereotype.Component;
//import tyj.ddz.match.MatchRequester;
//import tyj.ddz.match.PlayerPropService;
//import tyj.util.ChannelAttributeUtil;
//
//import javax.annotation.Resource;
//import java.util.List;
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
//public class MatchHandler extends AbsMessageHandler {
//    @Resource
//    private MatchRequester matchRequester;
//    @Resource
//    private PlayerPropService playerPropService;
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        int methodId = message.getMethodId();
//        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
//        switch (funcId) {
//            case serverGetMatchInfo:
//                handleMatchList(ctx, message);
//                break;
//            case serverMatchApply:
//                handleMatchApplyRequest(ctx, message);
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void handleMatchApplyRequest(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
//        if (message.getResult() == ProtoMsg.ResultCode.success) {
//            MatchProto.ServerMatchApply apply = message.getServerMatchApply();
//            logger.info("玩家{}成功加入比赛.", userId);
//        } else {
//            logger.info("玩家{}加入比赛失败，重新获取比赛列表", userId);
//            try {
//                playerPropService.changePlayerProp(userId, 101, 50, 3);
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            matchRequester.requestMatchList(userId);
//        }
//    }
//
//    private void handleMatchList(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        MatchProto.ServerGetMatchInfo matchList = message.getServerGetMatchInfo();
//        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
//        logger.info("玩家{}获取比赛场列表", userId);
//        List<MatchProto.MatchInfo> list = matchList.getMatchListList();
////        for (MatchProto.MatchInfo info : list
////        ) {
////            if (info.getJoin() < info.getTotal()) {
////                matchRequester.requestApplyMatch(userId, info.getMatchId());
////                logger.info("玩家{}申请进入比赛场{}", userId, info.getMatchId());
////                return;
////            }
////        }
//        matchRequester.requestApplyMatch(userId, 1);
//    }
//
//    @Override
//    public int getName() {
//        return ProtoMsg.ModelIdEnum.MATCH_ID_VALUE;
//    }
//}
