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
package org.tyj.ddz.ai;


import com.kys.util.netty.proto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tyj.constant.AIModeEnum;
import org.tyj.ddz.bean.LoginInfo;
import org.tyj.ddz.bean.PlayerInfo;
import org.tyj.ddz.sender.LuckyTimeSender;
import org.tyj.ddz.service.LoginService;
import org.tyj.util.ChannelUtil;
import org.tyj.web.MessageSender;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/17
 * @since 1.0.0
 */
@Scope(value = "prototype")
@Component("AIClient")
public class AIClient extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(AIClient.class.getName());

    private AIModeEnum aiMode;

    private int userId;

    private String token;

    @Resource
    private LoginService loginService;

    @Resource
    private LuckyTimeSender luckyTimeSender;

    private ConcurrentHashMap<Long, AIMessage> msgMap = new ConcurrentHashMap<>();

    private PlayerInfo info;

    private LoginInfo login;

    private int seatId;//座位号

    public AIClient() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PlayerInfo getInfo() {
        return info;
    }

    public void setInfo(PlayerInfo info) {
        this.info = info;
    }

    public AIModeEnum getAiMode() {
        return aiMode;
    }

    public void setAiMode(AIModeEnum aiMode) {
        this.aiMode = aiMode;
    }

    private boolean isLogin = false;

    private int autoJoinCount = 0;

    private boolean isStart = false;

    public void run() {
        //todo-判断是自动模式还是手动模式

        //获取token
        login = loginService.findLoginByUserId(this.getUserId());
        super.setName("AI-Thread-" + userId);
        logger.info("start AI thread-{}", super.getName());

        synchronized (this) {
            while (true) {
                for (Long startTime : msgMap.keySet()
                ) {
                    //logger.info("Thread-" + super.getName() + "已唤醒!");
                    if (startTime < System.currentTimeMillis()) {
                        AIMessage am = msgMap.get(startTime);
                        boolean isRemove = true;
                        switch (am.getMethodId()) {
                            case "login":
                                AILogin();
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                isLogin = true;
                                break;
                            case "join":
                                AIJoin();
                                isStart = true;
                                break;
                            case "wincup":
                                AIWinCup(am.getArgs());
                                break;
                            case "exc":
                                AIExcWinCup(am.getArgs());
                                break;
                            case "StartGame":
                                isRemove = AIStartGame(am.getArgs());
                                break;
                            case "getAnnounce":
                                getAnnounce();
                                break;
                            case "getRank":
                                getRank();
                                break;
                            case "getHistoryRank":
                                getHistoryRank();
                                break;
                            case "getHead":
                                getHead();
                                break;
                            case "equipHead":
                                equipHead();
                                break;
                            case "snatch":
                                snatchFunction(am.getArgs());
                                break;
                            case "item":
                                itemFunction(am.getArgs());
                                break;
                            case "vip":
                                vipFunction(am.getArgs());
                                break;
                            case "skin":
                                skinFunction(am.getArgs());
                                break;
                            case "lucky":
                                luckyTimeSender.sendLuckyTimeQuery(userId);
                                break;
                            default:
                                break;
                        }
                        if (isRemove)
                            msgMap.remove(startTime);
                    }
                }
                if (isStart && isLogin && null == ChannelUtil.getGameChannels(userId)) {
                    autoJoinCount++;
                    if (autoJoinCount > 60) {
                        AIJoin();
                    }
                } else {
                    autoJoinCount = 0;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void skinFunction(String args) {
        logger.info("皮肤接口");
        switch (args) {
            case "info"://skin;info
                logger.info("调用查看玩家皮肤接口");
                ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetSkinInfo);
                break;
            case "equip"://skin;equip
                logger.info("装备玩家皮肤");
                ProtoMsg.ClientRequest.Builder crBuilder1 = ProtoMsg.ClientRequest.newBuilder();
                UserProto.ClientEquipSkin.Builder equipBuilder = UserProto.ClientEquipSkin.newBuilder();
                equipBuilder.setSkin(UserProto.SkinEnum.Lottery_Skin);
                crBuilder1.setClientEquipSkin(equipBuilder);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder1, ProtoMsg.MessageTypeEnum.clientEquipSkin);
            default:
                break;
        }
    }

    private void vipFunction(String args) {
        logger.info("VIP接口");
        switch (args) {
            case "mainInfo"://vip;mainInfo
                logger.info("调用查看VIP主界面接口");
                VipProto.ClientGetVipInfo.Builder builder = VipProto.ClientGetVipInfo.newBuilder();
                ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
                crBuilder.setClientGetVipInfo(builder);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetVipInfo);
                break;
            case "week"://vip;week
                logger.info("调用领取VIP每周奖励接口");
                VipProto.ClientGetVipWeek.Builder builder1 = VipProto.ClientGetVipWeek.newBuilder();
                ProtoMsg.ClientRequest.Builder crBuilder1 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder1.setClientGetVipWeek(builder1);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder1, ProtoMsg.MessageTypeEnum.clientGetVipWeek);
                break;
            default:
                break;
        }
    }

    private void itemFunction(String args) {
        logger.info("调用道具，玩家背包中的方法!");
        switch (args) {
            case "queryBag"://item;queryBag
                logger.info("调用查看玩家背包的方法！");
                PropProto.ClientQueryProp.Builder builder = PropProto.ClientQueryProp.newBuilder();
                ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
                crBuilder.setClientQueryProp(builder);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientQueryProp);
                break;
            case "useItem"://item;useItem
                logger.info("玩家使用道具！");
                PropProto.ClientUseProp.Builder item = PropProto.ClientUseProp.newBuilder();
                item.setId("1");
                ProtoMsg.ClientRequest.Builder crBuilder1 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder1.setClientUseProp(item);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder1, ProtoMsg.MessageTypeEnum.clientUseProp);
                break;
            case "queryCount"://item;queryCount
                logger.info("玩家查询道具数量！");
                PropProto.ClientQueryPropCount.Builder count = PropProto.ClientQueryPropCount.newBuilder();
                count.setType(PropProto.PropType.Diam_Card);
                ProtoMsg.ClientRequest.Builder crBuilder2 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder2.setClientQueryPropCount(count);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder2, ProtoMsg.MessageTypeEnum.clientQueryPropCount);
                break;
            default:
                break;
        }
    }

    //Snatch
    private void snatchFunction(String args) {
        logger.info("玩家{}调用夺宝中方法{}", userId, args);
        switch (args) {
            case "getAddr"://snatch;getAddr
                logger.info("调用查看玩家地址的方法!");
                SnatchProto.ClientGetReceiveAddr.Builder cgr = SnatchProto.ClientGetReceiveAddr.newBuilder();
                ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
                crBuilder.setClientGetReceiveAddr(cgr);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetReceiveAddr);
                break;
            case "alterAddr"://snatch;alterAddr
                logger.info("调用修改玩家地址的方法!");
                SnatchProto.ClientAlterReceiveAddr.Builder car = SnatchProto.ClientAlterReceiveAddr.newBuilder();
                car.setCity(5639);
                car.setFamilyName("阿迪力!");
                car.setGivenName("阿里巴巴!");
                car.setReceiveTel("");
                car.setDetailAddr("详细地址");
                ProtoMsg.ClientRequest.Builder crBuilder1 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder1.setClientAlterReceiveAddr(car);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder1, ProtoMsg.MessageTypeEnum.clientAlterReceiveAddr);
                break;
            case "getList"://snatch;getList
                logger.info("调用获取抽奖主界面方法!");
                SnatchProto.ClientSnatchList.Builder csl = SnatchProto.ClientSnatchList.newBuilder();
                ProtoMsg.ClientRequest.Builder crBuilder2 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder2.setClientSnatchList(csl);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder2, ProtoMsg.MessageTypeEnum.clientSnatchList);
                break;
            case "detail"://snatch;detail
                logger.info("调用查看抽奖物品详细界面方法!");
                SnatchProto.ClientSnatchItemDetail.Builder itemBuilder = SnatchProto.ClientSnatchItemDetail.newBuilder();
                itemBuilder.setItemId(1);
                ProtoMsg.ClientRequest.Builder crBuilder3 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder3.setClientSnatchItemDetail(itemBuilder);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder3, ProtoMsg.MessageTypeEnum.clientSnatchItemDetail);
                break;
            case "bet"://snatch;bet
                logger.info("调用夺宝商城下注方法!");
                SnatchProto.ClientSnatchBet.Builder betBuilder = SnatchProto.ClientSnatchBet.newBuilder();
                betBuilder.setBetValue(1);
                betBuilder.setItemId(3);
                ProtoMsg.ClientRequest.Builder crBuilder4 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder4.setClientSnatchBet(betBuilder.build());
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder4, ProtoMsg.MessageTypeEnum.clientSnatchBet);
                break;
            case "historyList"://snatch;historyList
                logger.info("调用夺宝商城查看历史列表方法");
                SnatchProto.ClientSnatchAwardList.Builder historyListBuilder = SnatchProto.ClientSnatchAwardList.newBuilder();
                historyListBuilder.setPage(2);
                ProtoMsg.ClientRequest.Builder crBuilder5 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder5.setClientSnatchAwardList(historyListBuilder.build());
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder5, ProtoMsg.MessageTypeEnum.clientSnatchAwardList);
                break;
            case "ownRecord"://snatch;ownRecord
                logger.info("调用夺宝商城查看自己的下注方法");
                SnatchProto.ClientOwnSnatchRecord.Builder recordBuilder = SnatchProto.ClientOwnSnatchRecord.newBuilder();
                recordBuilder.setPage(1);
                ProtoMsg.ClientRequest.Builder crBuilder6 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder6.setClientOwnSnatchRecord(recordBuilder);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder6, ProtoMsg.MessageTypeEnum.clientOwnSnatchRecord);
                break;
            case "historyDetail"://snatch;historyDetail
                logger.info("调用夺宝商城-查看单个商品的具体历史信息!");
                SnatchProto.ClientSnatchHistoryDetail.Builder b = SnatchProto.ClientSnatchHistoryDetail.newBuilder();
                b.setItemId(3);
                b.setRounds(4);
                ProtoMsg.ClientRequest.Builder crBuilder7 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder7.setClientSnatchHistoryDetail(b);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder7, ProtoMsg.MessageTypeEnum.clientSnatchHistoryDetail);
                break;
            case "singleAllHistory"://snatch;singleAllHistory
                logger.info("调用夺宝商城-分页查看单个商品所有历史信息!");
                SnatchProto.ClientSnatchItemHistory.Builder cs = SnatchProto.ClientSnatchItemHistory.newBuilder();
                cs.setItemId(2);
                cs.setPage(0);
                ProtoMsg.ClientRequest.Builder crBuilder8 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder8.setClientSnatchItemHistory(cs);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder8, ProtoMsg.MessageTypeEnum.clientSnatchItemHistory);
                break;
            case "getAward"://snatch;getAward
                logger.info("调用夺宝商城-领取奖励历史信息!");
                SnatchProto.ClientSnatchGetAward.Builder csg = SnatchProto.ClientSnatchGetAward.newBuilder();
                csg.setItemId(3);
                csg.setRounds(5);
                ProtoMsg.ClientRequest.Builder crBuilder9 = ProtoMsg.ClientRequest.newBuilder();
                crBuilder9.setClientSnatchGetAward(csg);
                MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder9, ProtoMsg.MessageTypeEnum.clientSnatchGetAward);
                break;
            default:
                logger.info("没有该方法->{}!", args);
                break;
        }
    }


    private void equipHead() {
        logger.info("玩家{}装备头像", userId);
        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
        HallProto.ClientChoosePF.Builder pfBuilder = HallProto.ClientChoosePF.newBuilder();
        pfBuilder.setId(1201);
        crBuilder.setClientChoosePF(pfBuilder);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientChoosePF);
    }

    private void getHead() {
        logger.info("玩家{}获取头像列表", userId);
        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
        HallProto.ClientGetPFList.Builder pfBuilder = HallProto.ClientGetPFList.newBuilder();
        crBuilder.setClientGetPFList(pfBuilder.build());
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetPFList);
    }

    private void getHistoryRank() {
        logger.info("玩家{}获取历史排行榜数据", userId);
        //HallProto.ClientGetHistoryRank.Builder cghBuilder = HallProto.ClientGetHistoryRank.newBuilder();
        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetHistoryRank);
    }

    private void getRank() {
        logger.info("玩家{}发送获取排行榜请求！", userId);
        HallProto.ClientGetRankList.Builder cgr = HallProto.ClientGetRankList.newBuilder();
        cgr.setType(1);
        cgr.setRange(2);
        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
        crBuilder.setClientGetRankList(cgr);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetRankList);
    }

    private void getAnnounce() {
        logger.info("玩家{}发送获取公告请求!", userId);
        HallProto.ClientGetAnnounce.Builder cgaBuilder = HallProto.ClientGetAnnounce.newBuilder();
        ProtoMsg.ClientRequest.Builder crBuilder = ProtoMsg.ClientRequest.newBuilder();
        crBuilder.setClientGetAnnounce(cgaBuilder.build());
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), crBuilder, ProtoMsg.MessageTypeEnum.clientGetAnnounce);
    }

    private boolean AIStartGame(String roomId) {
        //发送进入房间请求
        com.kys.util.netty.proto.GameProto.ClientEnterRoom.Builder cer = com.kys.util.netty.proto.GameProto.ClientEnterRoom.newBuilder();
        AIClient ac = AIManager.aiMap.get(userId);
        cer.setToken(ac.getToken());
        cer.setRoomId(Integer.parseInt(roomId));
        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
        cr.setClientEnterRoom(cer);
        if (null == ChannelUtil.getGameChannels(userId)) {
            logger.warn("玩家{}发送消息之前还没成功建立链接!", userId);
            return false;
        } else {
            MessageSender.sendSingleSuccessMsg(ChannelUtil.getGameChannels(userId), cr, ProtoMsg.MessageTypeEnum.clientEnterRoom);
        }
        return true;
    }

    private void AIExcWinCup(String itemId) {
        logger.info("玩家{}发送兑换奖杯请求！", userId);
        StoreProto.ClientConvertGoods.Builder ccg = StoreProto.ClientConvertGoods.newBuilder();
        ccg.setOrderSource(1);
        ccg.setId(Integer.parseInt(itemId));
        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
        cr.setClientConvertGoods(ccg);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, ProtoMsg.MessageTypeEnum.clientConvertGoods);
    }

    private void AIWinCup(String lv) {
        logger.info("玩家{}发送抽奖杯请求！", userId);
        //构造请求
        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
        HallProto.ClientDrawWinCup.Builder cdwc = HallProto.ClientDrawWinCup.newBuilder();
        cdwc.setRoomConfigId(Integer.parseInt(lv));
        cr.setClientDrawWinCup(cdwc);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, ProtoMsg.MessageTypeEnum.clientDrawWinCup);
    }

    private void AILogin() {
        logger.info("玩家{}，发送登录请求!", userId);
        //构建登录请求
        com.kys.util.netty.proto.ProtoMsg.ClientRequest.Builder cr = com.kys.util.netty.proto.ProtoMsg.ClientRequest.newBuilder();
        com.kys.util.netty.proto.LoginProto.ClientLogin.Builder clb = com.kys.util.netty.proto.LoginProto.ClientLogin.newBuilder();
        clb.setToken(login.getToken());
        this.token = login.getToken();
        clb.setAppVersion("");
        clb.setChannel("weChat");
        clb.setDeviceId(login.getDeviceId());
        clb.setDevicePlatform(login.getDevicePlatform());
        clb.setLoginType("weChat");
        cr.setClientLogin(clb);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, com.kys.util.netty.proto.ProtoMsg.MessageTypeEnum.clientLogin);
    }

    public void AIJoin() {
        autoJoinCount = 0;
        logger.info("玩家{},发送加入房间请求!", userId);
        com.kys.util.netty.proto.GameProto.ClientQuickJoin.Builder cqj = com.kys.util.netty.proto.GameProto.ClientQuickJoin.newBuilder();
        // 参与匹配的等级,范围为1-3，11-13
        cqj.setChipLevel(3);
        cqj.setMode(1);
        com.kys.util.netty.proto.ProtoMsg.ClientRequest.Builder cr = com.kys.util.netty.proto.ProtoMsg.ClientRequest.newBuilder();
        cr.setClientQuickJoin(cqj);
        MessageSender.sendSingleSuccessMsg(ChannelUtil.getHallChannel(userId), cr, com.kys.util.netty.proto.ProtoMsg.MessageTypeEnum.clientQuickJoin);
    }

    public void addMsg(AIMessage am) {
        msgMap.put(am.getExecuteTime(), am);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}