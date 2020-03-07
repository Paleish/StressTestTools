/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ServerMsgHandler
 * Author:   Administrator
 * Date:     2019/6/21 16:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.handler;

import com.kys.util.netty.proto.GameProto;
import com.kys.util.netty.proto.ProtoMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import org.tyj.ddz.ai.AIClient;
import org.tyj.ddz.ai.AIManager;
import org.tyj.ddz.ai.AIMessage;
import org.tyj.util.ChannelAttributeUtil;
import org.tyj.util.ChannelUtil;
import org.tyj.web.MessageSender;
import org.tyj.web.WebSocketClient;

import java.util.List;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/21
 * @since 1.0.0
 */
@Component
public class ServerMsgHandler extends AbsMessageHandler {

    private static ThreadPoolExecutor service;

    static {
        service = new ThreadPoolExecutor(1000, Integer.MAX_VALUE, 30l, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        service.prestartAllCoreThreads();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int methodId = message.getMethodId();
        ProtoMsg.MessageTypeEnum funcId = ProtoMsg.MessageTypeEnum.forNumber(methodId);
        switch (funcId) {
            case b_RoomIdUrl:
                getUrlAndEnter(ctx, message);
                break;
            case b_GameStart:
                gameStart(ctx, message);
                break;
            case b_DoCatch:
                doCatch(ctx, message);
                break;
            case b_CatchResult:
                catchResult(ctx, message);
                break;
            case b_GameSettle:
                gameSettle(ctx, message);
                break;
            case b_SendDiZhuCard:
                //startCards(ctx, message);
            case b_PlayCards:
                playerCards(ctx, message);
            default:
                break;
        }
    }

    private void playerCards(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        //GameProto.B_PlayCards bp = message.getBPlayCards();
        //logger.info("压测AI获取到出牌信息!出牌座位号{},具体牌型{}!", bp.getDoPlaySeatId(), bp.getCardsList().toArray());
    }

    private void startCards(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        Random rd = new Random();
        try {
            Thread.sleep(2000 + rd.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送进入托管请求
        GameProto.ClientAutoPlay.Builder cdc = GameProto.ClientAutoPlay.newBuilder();
        cdc.setStatus(1);
        ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
        cr.setClientAutoPlay(cdc);
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        MessageSender.sendSingleSuccessMsg(ctx.channel(), cr, ProtoMsg.MessageTypeEnum.clientDoCatch);
        logger.info("玩家{}叫分完毕，进入托管！", userId);
    }

    private void gameSettle(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        try {
            //断开之前的连接
            Channel ch = ChannelUtil.getGameChannels(userId);
            ch.close();
            Thread.sleep(5000);

        } catch (Exception e) {
            logger.error("压测AI结算报错！", e);
        }
        //重新自动加入匹配
        AIClient ac = AIManager.aiMap.get(userId);
        ac.AIJoin();
        logger.info("打完一局，AI{}重新加入匹配!", userId);
    }

    private void catchResult(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        GameProto.B_CatchResult bcr = message.getBCatchResult();
        //logger.info("收到座位号{}叫分{}结果!", bcr.getCatchSeatId(), bcr.getCatchScore());
    }

    private void doCatch(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        AIClient ac = AIManager.aiMap.get(userId);

        GameProto.B_DoCatch bdc = message.getBDoCatch();
        if (ac.getSeatId() == bdc.getDoCatchSeatId()) {
            int tempMax = bdc.getHascatchScore();
            Random rd = new Random();
            int callPoint;
            if (3 - tempMax == 1) {
                callPoint = 3;
            } else {
                callPoint = tempMax + rd.nextInt(3 - tempMax) + 1;
            }
            GameProto.ClientDoCatch.Builder cdc = GameProto.ClientDoCatch.newBuilder();
            cdc.setCatchScore(callPoint);
            ProtoMsg.ClientRequest.Builder cr = ProtoMsg.ClientRequest.newBuilder();
            cr.setClientDoCatch(cdc);
            MessageSender.sendSingleSuccessMsg(ctx.channel(), cr, ProtoMsg.MessageTypeEnum.clientDoCatch);
        }
    }

    private void gameStart(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        GameProto.B_GameStart bgs = message.getBGameStart();
        List<Integer> list = bgs.getHandsCardsList();
        logger.info("玩家{}在gamestart接口中获取到手牌为{}", userId, list.toArray());
    }

    private synchronized void getUrlAndEnter(ChannelHandlerContext ctx, ProtoMsg.ServerResponse message) {
        int userId = ChannelAttributeUtil.getPlayerId(ctx.channel());
        logger.info("AI{}收到房间消息", userId);
        com.kys.util.netty.proto.GameProto.B_RoomIdUrl biu = message.getBRoomIdUrl();
        logger.info("AI{}加入房间成功,房间号为{},连接地址为{}！", userId, biu.getRoomId(), biu.getUrl());
        AIClient ac = AIManager.aiMap.get(userId);
        WebSocketClient client = new WebSocketClient(userId, biu.getRoomId(), biu.getUrl() + "/" + ac.getToken());
        service.execute(() -> client.run());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AIMessage am = new AIMessage(System.currentTimeMillis() + 1000, "StartGame", "" + biu.getRoomId());
        ac.addMsg(am);
    }

    @Override
    public int getName() {
        return ProtoMsg.ModelIdEnum.BROADCAST_ID.getNumber();
    }

}