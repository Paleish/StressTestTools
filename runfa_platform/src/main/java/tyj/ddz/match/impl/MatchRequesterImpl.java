//package tyj.ddz.match.impl;
//
//import com.kys.util.netty.proto.MatchProto;
//import com.kys.util.netty.proto.ProtoMsg;
//import org.springframework.stereotype.Component;
//import tyj.ddz.match.MatchRequester;
//import tyj.util.ChannelUtil;
//import tyj.web.MessageSender;
//
//@Component
//public class MatchRequesterImpl implements MatchRequester {
//
//    @Override
//    public void requestMatchList(int userId) {
//        MatchProto.ClientGetMatchInfo.Builder builder = MatchProto.ClientGetMatchInfo.newBuilder();
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        crBuilder.setClientGetMatchInfo(builder);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetMatchInfo);
//    }
//
//    @Override
//    public void requestApplyMatch(int userId, int matchId) {
//        MatchProto.ClientMatchApply.Builder builder = MatchProto.ClientMatchApply.newBuilder();
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        builder.setMatchId(matchId);
//        crBuilder.setClientMatchApply(builder);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientMatchApply);
//    }
//}
