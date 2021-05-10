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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kys.pb.*;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bro.util.HttpSendUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tyj.constant.AIModeEnum;
import tyj.constant.NetConstants;
import tyj.ddz.bean.PlayerInfo;
import tyj.util.shedule.ScheduledThreadPoolUtil;
import tyj.web.MessageSender;
import tyj.web.WebSocketClient;

import java.util.concurrent.TimeUnit;

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

    private AIModeEnum aiMode;
    private int userId;
    private String token;
    private PlayerInfo info;
    private int seatId;//座位号
    private boolean isLogin = false;
    private int autoJoinCount = 0;
    private boolean isStart = false;
    private Channel channel;

    private AICommand command;
    private String[] commandArgs;

    public AIClient() {
        super.setName("AI-Thread-" + userId);
        log.info("start AI thread-{}", super.getName());
    }

    public void run() {
        switch (command) {
//            case INIT_WS:
//                initWebSocket();
//                break;
            case JOIN_LANDLORD:
                joinLandLord();
                break;
            case JOIN_GF:
                joinGoldenFlower();
                break;
            default:
                break;
        }
    }

    private void joinGoldenFlower() {
        //发送加入金花请求
        log.info("发送加入梦金花游戏请求");
        GoldenCli.C_JoinRoom.Builder joinBuilder = GoldenCli.C_JoinRoom.newBuilder();
        joinBuilder.setLevel(1);
        MessageSender.sendSingleSuccessMsg(channel, PbGate.ServiceType.GOLDENFLOWER_VALUE, GoldenProto.MSG.JoinRoom_VALUE, joinBuilder.build().toByteString());
    }

    private void joinLandLord() {
        //发送加入游戏请求
        log.info("发送加入房间请求!");
        LandlordCli.C_QuickJoin.Builder builder = LandlordCli.C_QuickJoin.newBuilder();
        builder.setChipLevel(1);
        builder.setIsContinue(true);
        MessageSender.sendSingleSuccessMsg(channel, PbGate.ServiceType.LANDLORD_VALUE, LandlordBase.MSG.QuickJoin_VALUE, builder.build().toByteString());
    }

    public void initWebSocket() {
        //请求http服务器获取登陆串
        String result = HttpSendUtil.wxHttpGetRequest(NetConstants.HttpUrl);
        JSONObject object = JSON.parseObject(result);
        String url = object.getString("url");
        String token = object.getString("token");
        log.info("玩家与{}建立连接！", url);
        WebSocketClient client = new WebSocketClient(url, token);
        ScheduledThreadPoolUtil.getInstance().addRun(client, 0, TimeUnit.MICROSECONDS);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送登陆请求
        HallCli.C_Login.Builder loginBuilder = HallCli.C_Login.newBuilder();
        loginBuilder.setChannel("kys_test");
        loginBuilder.setIsReconnect(false);
        loginBuilder.setToken(token);
        MessageSender.sendSingleSuccessMsg(client.getChannel(), PbGate.ServiceType.PLATFORM_VALUE, HallBase.MSG.Login_VALUE, loginBuilder.build().toByteString());

        channel = client.getChannel();
    }
}