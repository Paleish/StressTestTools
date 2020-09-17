//package tyj.ddz.sender.impl;
//
//import com.kys.util.netty.proto.ProtoMsg;
//import org.springframework.stereotype.Component;
//import tyj.ddz.sender.ReviveSender;
//import tyj.util.ChannelUtil;
//import tyj.web.MessageSender;
//
//@Component
//public class ReviveSenderImpl implements ReviveSender {
//
//    @Override
//    public void queryReviveInfo(int userId) {
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), ProtoMsg.ClientRequest.newBuilder(), ProtoMsg.MessageTypeEnum.clientQueryReviveInfo);
//    }
//}
