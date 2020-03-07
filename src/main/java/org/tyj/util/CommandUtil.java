/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CommandUtil
 * Author:   Administrator
 * Date:     2019/6/17 15:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tyj.Initializer;
import org.tyj.ddz.ai.AIAsync;
import org.tyj.ddz.ai.AIManager;
import org.tyj.ddz.bean.LoginInfo;
import org.tyj.ddz.bean.PlayerInfo;
import org.tyj.ddz.bean.UserInfo;
import org.tyj.ddz.mapper.PlayerInfoMaper;
import org.tyj.ddz.service.LoginInfoService;
import org.tyj.ddz.service.PlayerInfoService;
import org.tyj.ddz.service.UserInfoService;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.SocketException;
import java.util.Date;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/17
 * @since 1.0.0
 */
@Component
public class CommandUtil extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class.getName());

    @Resource
    private AIManager aiManager;

    public void run() {
        modeCommand();
    }

    private void modeAuto() {
        try {
            logger.info("进入自动模式，实例化AI!");
            aiManager.InitAI(Initializer.AI_NUM);
            Thread.sleep(1000 * 120);
            logger.info("完成实例化，!start login!");
            aiManager.addNewMsg("login", null);
            Thread.sleep(1000 * 120);
            logger.info("login finish!start join game!");
            aiManager.addNewMsg("join", null);
            AIAsync.randomSwitch = 2;
            AIAsync.sleepTime = 3000;
        } catch (Exception e) {
            logger.error("start auto mode err!", e);
        }
    }

    private void modeCommand() {
        logger.info("enter command mode...");
        BufferedReader console = null;
        try {
            logger.info("start receive command...");
            console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = console.readLine();
                logger.info("receive order ->{}", msg);
                String[] cmd = msg.split(";");
                if (msg == null) {
                    break;
                } else if ("init".equals(cmd[0])) {
                    aiManager.InitAI(cmd[1]);
                } else if ("login".equals(cmd[0])) {
                    aiManager.addNewMsg("login", null);
                } else if ("join".equals(cmd[0])) {
                    aiManager.addNewMsg("join", null);
                    AIAsync.randomSwitch = 2;
                    AIAsync.sleepTime = 3000;
                } else if ("getAnnounce".equals(cmd[0])) {//
                    aiManager.addNewMsg("getAnnounce", cmd[0]);
                } else if ("wincup".equals(cmd[0])) {//
                    aiManager.addNewMsg("wincup", cmd[1]);
                } else if ("exc".equals(cmd[0])) {//默认18
                    aiManager.addNewMsg("exc", cmd[1]);
                } else if ("sh".equals(cmd[0])) {
                    setEmojiName(cmd[1]);//测试往数据库插入带emoji的头像
                } else if ("gh".equals(cmd[0])) {
                    getEmojiName(cmd[1]);
                } else if ("create".equals(cmd[0])) {
                    createNewAI(cmd[1]);
                } else if ("ip".equals(cmd[0])) {//查看测试工具所在环境ip的方法
                    getIp();
                } else if ("getRank".equals(cmd[0])) {
                    aiManager.addNewMsg("getRank", null);
                } else if ("getHistoryRank".equals(cmd[0])) {
                    aiManager.addNewMsg("getHistoryRank", null);
                } else if ("getHead".equals(cmd[0])) {
                    aiManager.addNewMsg("getHead", null);
                } else if ("equipHead".equals(cmd[0])) {
                    aiManager.addNewMsg("equipHead", null);
                } else if ("snatch".equals(cmd[0])) {
                    aiManager.addNewMsg("snatch", cmd[1]);
                } else if ("item".equals(cmd[0])) {
                    aiManager.addNewMsg("item", cmd[1]);
                } else if ("vip".equals(cmd[0])) {
                    aiManager.addNewMsg("vip", cmd[1]);
                } else if ("skin".equals(cmd[0])) {
                    aiManager.addNewMsg("skin", cmd[1]);
                }
            }
        } catch (IOException e) {
            logger.info("consolo receive order err!", e);
        } finally {
            if (null != console) {
                try {
                    console.close();
                } catch (Exception e) {
                    logger.info("consolo receive order err!", e);
                }
            }
        }
    }

    private void getIp() {
        try {
            logger.info("内网ip为{}，外网ip为{},RealIp为{}", IPUtil.INTRANET_IP, IPUtil.INTERNET_IP, IPUtil.getRealIp());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private PlayerInfoMaper playerInfoMaper;

    private void setEmojiName(String uid) {
        try {
            logger.info("进入测试数据库插入emoji方法!");
            playerInfoMaper.updatePlayerName(Integer.parseInt(uid), "eMOJI" + "\uD83D\uDC2C\n");
            logger.info("成功向数据库里插入emoji方法!", uid);
        } catch (Exception e) {
            logger.error("插入头像报错！", e);
        }
    }

    private void getEmojiName(String uid) {
        try {
            PlayerInfo ui = playerInfoMaper.queryPlayerInfoByUserid(Integer.parseInt(uid));
            logger.info("查询玩家{}的昵称为{}", uid, ui.getPlayerName());
        } catch (Exception e) {
            logger.error("获取头像报错！", e);
        }
    }

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private LoginInfoService loginInfoService;

    @Resource
    private PlayerInfoService playerInfoService;

    public void createNewAI(String aiNum) {
        try {
            logger.info("enter random create ai method!");
            int createAiCount = Integer.parseInt(aiNum);
            for (int i = 0; i < createAiCount; i++) {
                //创造玩家openid和name
                String openid = UUID.randomUUID().toString();
                UserInfo userInfo = new UserInfo(0, openid, new Date());
                userInfoService.saveUserInfo(userInfo);
                int userId = userInfo.getUserId();
                String name = "AI_User" + userId;
                PlayerInfo playerInfo = new PlayerInfo(userInfo.getUserId(), name);
                playerInfo.setPlayerPortrait("");
                playerInfo.setIsAI((byte) Initializer.AI_SIGN);
                playerInfo.setGoldcoin(50000000);
                playerInfo.setDiamond(500000);
                playerInfo.setWincup(new BigDecimal(999));
                playerInfo.setWinTimes(0);
                playerInfo.setSignupDays(0);
                playerInfo.setPlayerSex((byte) 1);
                playerInfo.setPlayerExp(0);
                playerInfo.setPlayTimes(0);
                playerInfo.setPlayerSign("Happy tree friend！");
                playerInfoService.savePlayerInfo(playerInfo);
                //MD5散列userid
                String token = userId + openid.substring(10);
                LoginInfo loginInfo = new LoginInfo();
                //更新玩家登陆信息
                loginInfo.setUserId(userId);
                loginInfo.setToken(token);
                loginInfoService.saveLoginInfo(loginInfo);
            }
        } catch (NumberFormatException e) {
            logger.error("create ai err!", e);
        }
    }
}