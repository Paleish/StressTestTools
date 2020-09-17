///**
// * Copyright (C), 2015-2019, XXX有限公司
// * FileName: ShopHandler
// * Author:   Administrator
// * Date:     2019/7/24 17:59
// * Description:
// * History:
// * <author>          <time>          <version>          <desc>
// * TanYujie       修改时间           版本号              描述
// */
//package tyj.ddz.handler;
//
//import com.kys.util.netty.proto.ProtoMsg;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.stereotype.Component;
//
///**
// * 〈一句话功能简述〉<br>
// * 〈〉
// *
// * @author Administrator
// * @create 2019/7/24
// * @since 1.0.0
// */
//@Component
//public class ShopHandler extends AbsMessageHandler {
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        {
//            int methodId = message.getMethodId();
//            ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
//            switch (funcId) {
//                case serverConvertGoods:
//                    convertGoods(ctx, message);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private void convertGoods(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
//        com.kys.util.netty.proto.StoreProto.ServerConvertGoods scg = message.getServerConvertGoods();
//        logger.info("player convert {}，after operation money{}，diamond{}，win cup{}", scg.getResult(), scg.getPlayerGoldCoin(), scg.getPlayerDiamond(), scg.getPlayerWinCup());
//    }
//
//    @Override
//    public int getName() {
//        return ProtoMsg.ModelIdEnum.STORE_ID_VALUE;
//    }
//}