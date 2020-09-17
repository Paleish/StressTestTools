/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AIClient
 * Author:   Administrator
 * Date:     2019/6/17 16:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package tyj.ddz.ai;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tyj.constant.AIModeEnum;
import tyj.ddz.bean.PlayerInfo;
import tyj.web.WebSocketClient;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/17
 * @since 1.0.0
 */
@Data
@Scope(value = "prototype")
@Component("AIClient")
@Slf4j
public class AIClient extends Thread {
//
//    @Resource
//    private LuckyTimeSender luckyTimeSender;
//    @Resource
//    private ReviveSender reviveSender;
//    @Resource
//    private MatchRequester matchRequester;

    private AIModeEnum aiMode;
    private int userId;
    private String token;
    private PlayerInfo info;
    private int seatId;//座位号
    private boolean isLogin = false;
    private int autoJoinCount = 0;
    private boolean isStart = false;

    private AICommand command;
    private String[] commandArgs;

    public AIClient() {
        super.setName("AI-Thread-" + userId);
        log.info("start AI thread-{}", super.getName());
    }

    public void run() {
        switch (command) {
            case LOGIN:
                login();
                break;
        }
    }

    private void login() {
        WebSocketClient client = new WebSocketClient(commandArgs[0], commandArgs[1]);
        client.run();
    }


//    private void itemFunction(String args) {
//        log.info("调用道具，玩家背包中的方法!");
//        switch (args) {
//            case "queryBag"://item;queryBag
//                log.info("调用查看玩家背包的方法！");
//                PropProto.ClientQueryProp.Builder builder = PropProto.ClientQueryProp.newBuilder();
//                ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder.setClientQueryProp(builder);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientQueryProp);
//                break;
//            case "useItem"://item;useItem
//                log.info("玩家使用道具！");
//                PropProto.ClientUseProp.Builder item = PropProto.ClientUseProp.newBuilder();
//                item.setId("1");
//                ProtoMsg.ClientRequest.Builder crBuilder1 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder1.setClientUseProp(item);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder1, ProtoMsg.MessageTypeEnum.clientUseProp);
//                break;
//            case "queryCount"://item;queryCount
//                log.info("玩家查询道具数量！");
//                PropProto.ClientQueryPropCount.Builder count = PropProto.ClientQueryPropCount.newBuilder();
//                count.setType(PropProto.PropType.Diam_Card);
//                ProtoMsg.ClientRequest.Builder crBuilder2 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder2.setClientQueryPropCount(count);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder2, ProtoMsg.MessageTypeEnum.clientQueryPropCount);
//                break;
//            default:
//                break;
//        }
//    }
//
//    //Snatch
//    private void snatchFunction(String args) {
//        log.info("玩家{}调用夺宝中方法{}", userId, args);
//        switch (args) {
//            case "getAddr"://snatch;getAddr
//                log.info("调用查看玩家地址的方法!");
//                SnatchProto.ClientGetReceiveAddr.Builder cgr = SnatchProto.ClientGetReceiveAddr.newBuilder();
//                ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder.setClientGetReceiveAddr(cgr);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetReceiveAddr);
//                break;
//            case "alterAddr"://snatch;alterAddr
//                log.info("调用修改玩家地址的方法!");
//                SnatchProto.ClientAlterReceiveAddr.Builder car = SnatchProto.ClientAlterReceiveAddr.newBuilder();
//                car.setCity(5639);
//                car.setFamilyName("阿迪力!");
//                car.setGivenName("阿里巴巴!");
//                car.setReceiveTel("");
//                car.setDetailAddr("详细地址");
//                ProtoMsg.ClientRequest.Builder crBuilder1 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder1.setClientAlterReceiveAddr(car);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder1, ProtoMsg.MessageTypeEnum.clientAlterReceiveAddr);
//                break;
//            case "getList"://snatch;getList
//                log.info("调用获取抽奖主界面方法!");
//                SnatchProto.ClientSnatchList.Builder csl = SnatchProto.ClientSnatchList.newBuilder();
//                ProtoMsg.ClientRequest.Builder crBuilder2 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder2.setClientSnatchList(csl);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder2, ProtoMsg.MessageTypeEnum.clientSnatchList);
//                break;
//            case "detail"://snatch;detail
//                log.info("调用查看抽奖物品详细界面方法!");
//                SnatchProto.ClientSnatchItemDetail.Builder itemBuilder = SnatchProto.ClientSnatchItemDetail.newBuilder();
//                itemBuilder.setItemId(1);
//                ProtoMsg.ClientRequest.Builder crBuilder3 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder3.setClientSnatchItemDetail(itemBuilder);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder3, ProtoMsg.MessageTypeEnum.clientSnatchItemDetail);
//                break;
//            case "bet"://snatch;bet
//                log.info("调用夺宝商城下注方法!");
//                SnatchProto.ClientSnatchBet.Builder betBuilder = SnatchProto.ClientSnatchBet.newBuilder();
//                betBuilder.setBetValue(1);
//                betBuilder.setItemId(3);
//                ProtoMsg.ClientRequest.Builder crBuilder4 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder4.setClientSnatchBet(betBuilder.build());
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder4, ProtoMsg.MessageTypeEnum.clientSnatchBet);
//                break;
//            case "historyList"://snatch;historyList
//                log.info("调用夺宝商城查看历史列表方法");
//                SnatchProto.ClientSnatchAwardList.Builder historyListBuilder = SnatchProto.ClientSnatchAwardList.newBuilder();
//                historyListBuilder.setPage(2);
//                ProtoMsg.ClientRequest.Builder crBuilder5 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder5.setClientSnatchAwardList(historyListBuilder.build());
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder5, ProtoMsg.MessageTypeEnum.clientSnatchAwardList);
//                break;
//            case "ownRecord"://snatch;ownRecord
//                log.info("调用夺宝商城查看自己的下注方法");
//                SnatchProto.ClientOwnSnatchRecord.Builder recordBuilder = SnatchProto.ClientOwnSnatchRecord.newBuilder();
//                recordBuilder.setPage(1);
//                ProtoMsg.ClientRequest.Builder crBuilder6 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder6.setClientOwnSnatchRecord(recordBuilder);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder6, ProtoMsg.MessageTypeEnum.clientOwnSnatchRecord);
//                break;
//            case "historyDetail"://snatch;historyDetail
//                log.info("调用夺宝商城-查看单个商品的具体历史信息!");
//                SnatchProto.ClientSnatchHistoryDetail.Builder b = SnatchProto.ClientSnatchHistoryDetail.newBuilder();
//                b.setItemId(3);
//                b.setRounds(4);
//                ProtoMsg.ClientRequest.Builder crBuilder7 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder7.setClientSnatchHistoryDetail(b);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder7, ProtoMsg.MessageTypeEnum.clientSnatchHistoryDetail);
//                break;
//            case "singleAllHistory"://snatch;singleAllHistory
//                log.info("调用夺宝商城-分页查看单个商品所有历史信息!");
//                SnatchProto.ClientSnatchItemHistory.Builder cs = SnatchProto.ClientSnatchItemHistory.newBuilder();
//                cs.setItemId(2);
//                cs.setPage(0);
//                ProtoMsg.ClientRequest.Builder crBuilder8 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder8.setClientSnatchItemHistory(cs);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder8, ProtoMsg.MessageTypeEnum.clientSnatchItemHistory);
//                break;
//            case "getAward"://snatch;getAward
//                log.info("调用夺宝商城-领取奖励历史信息!");
//                SnatchProto.ClientSnatchGetAward.Builder csg = SnatchProto.ClientSnatchGetAward.newBuilder();
//                csg.setItemId(3);
//                csg.setRounds(5);
//                ProtoMsg.ClientRequest.Builder crBuilder9 = ProtoMsg.ClientRequest.newBuilder();
//                crBuilder9.setClientSnatchGetAward(csg);
//                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder9, ProtoMsg.MessageTypeEnum.clientSnatchGetAward);
//                break;
//            default:
//                log.info("没有该方法->{}!", args);
//                break;
//        }
//    }
//
//
//    private void equipHead() {
//        log.info("玩家{}装备头像", userId);
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        HallProto.ClientChoosePF.Builder pfBuilder = HallProto.ClientChoosePF.newBuilder();
//        pfBuilder.setId(1201);
//        crBuilder.setClientChoosePF(pfBuilder);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientChoosePF);
//    }
//
//    private void getHead() {
//        log.info("玩家{}获取头像列表", userId);
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        HallProto.ClientGetPFList.Builder pfBuilder = HallProto.ClientGetPFList.newBuilder();
//        crBuilder.setClientGetPFList(pfBuilder.build());
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetPFList);
//    }
//
//    private void getHistoryRank() {
//        log.info("玩家{}获取历史排行榜数据", userId);
//        //HallProto.ClientGetHistoryRank.Builder cghBuilder = HallProto.ClientGetHistoryRank.newBuilder();
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetHistoryRank);
//    }
//
//    private void getRank() {
//        log.info("玩家{}发送获取排行榜请求！", userId);
//        HallProto.ClientGetRankList.Builder cgr = HallProto.ClientGetRankList.newBuilder();
//        cgr.setType(1);
//        cgr.setRange(2);
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        crBuilder.setClientGetRankList(cgr);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetRankList);
//    }
//
//    private void getAnnounce() {
//        log.info("玩家{}发送获取公告请求!", userId);
//        HallProto.ClientGetAnnounce.Builder cgaBuilder = HallProto.ClientGetAnnounce.newBuilder();
//        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
//        crBuilder.setClientGetAnnounce(cgaBuilder.build());
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetAnnounce);
//    }
//
//    private boolean AIStartGame(String roomId) {
//        //发送进入房间请求
//        com.kys.util.netty.proto.GameProto.ClientEnterRoom.Builder cer = com.kys.util.netty.proto.GameProto.ClientEnterRoom.newBuilder();
//        AIClient ac = AIManager.aiMap.get(userId);
//        cer.setToken(ac.getToken());
//        cer.setRoomId(Integer.parseInt(roomId));
//        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
//        cr.setClientEnterRoom(cer);
//        if (null == ChannelUtil.getGameChannels(userId)) {
//            log.warn("玩家{}发送消息之前还没成功建立链接!", userId);
//            return false;
//        } else {
//            MessageSender.sendSingleSuccessMsg(ChannelUtil.getGameChannels(userId), cr, ProtoMsg.MessageTypeEnum.clientEnterRoom);
//        }
//        return true;
//    }
//
//    private void AIExcWinCup(String itemId) {
//        log.info("玩家{}发送兑换奖杯请求！", userId);
//        StoreProto.ClientConvertGoods.Builder ccg = StoreProto.ClientConvertGoods.newBuilder();
//        ccg.setId(Integer.parseInt(itemId));
//        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
//        cr.setClientConvertGoods(ccg);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, ProtoMsg.MessageTypeEnum.clientConvertGoods);
//    }
//
//    private void AIWinCup(String lv) {
////        log.info("玩家{}发送抽奖杯请求！", userId);
////        //构造请求
////        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
////        HallProto.ClientDrawWinCup.Builder cdwc = HallProto.ClientDrawWinCup.newBuilder();
////        cdwc.setRoomConfigId(Integer.parseInt(lv));
////        cr.setClientDrawWinCup(cdwc);
////        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, ProtoMsg.MessageTypeEnum.clientDrawWinCup);
//    }
//
//    private void AILogin() {
//        log.info("玩家{}，发送登录请求!", userId);
//        //构建登录请求
//        com.kys.util.netty.proto.ProtoMsg.ClientRequest.Builder cr = com.kys.util.netty.proto.ProtoMsg.ClientRequest.newBuilder();
//        com.kys.util.netty.proto.LoginProto.ClientNewLogin.Builder clb = com.kys.util.netty.proto.LoginProto.ClientNewLogin.newBuilder();
//        clb.setToken(login.getToken());
//        this.token = login.getToken();
//        clb.setAppVersion("");
//        clb.setChannel("weChat");
//        clb.setDeviceId(login.getDeviceId());
//        clb.setDevicePlatform(login.getDevicePlatform());
//        clb.setLoginType("weChat");
//        cr.setClientNewLogin(clb);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, com.kys.util.netty.proto.ProtoMsg.MessageTypeEnum.clientNewLogin);
//    }
//
//    public void AIJoin() {
//        autoJoinCount = 0;
//        log.info("玩家{},发送加入房间请求!", userId);
//        com.kys.util.netty.proto.GameProto.ClientQuickJoin.Builder cqj = com.kys.util.netty.proto.GameProto.ClientQuickJoin.newBuilder();
//        // 参与匹配的等级,范围为1-3，11-13
//        Random rd = new Random();
//        //cqj.setChipLevel(rd.nextInt(3) * 10 + rd.nextInt(2) + 1);
//        cqj.setChipLevel(31);
//        com.kys.util.netty.proto.ProtoMsg.ClientRequest.Builder cr = com.kys.util.netty.proto.ProtoMsg.ClientRequest.newBuilder();
//        cr.setClientQuickJoin(cqj);
//        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, com.kys.util.netty.proto.ProtoMsg.MessageTypeEnum.clientQuickJoin);
//    }
}