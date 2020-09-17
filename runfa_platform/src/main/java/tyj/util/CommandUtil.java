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
package tyj.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tyj.ddz.ai.AIManager;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/17
 * @since 1.0.0
 */
@Component
@Slf4j
public class CommandUtil extends Thread {

    @Resource
    private AIManager aiManager;

    public void run() {
        modeCommand();
    }

    private void modeCommand() {
        log.info("enter command mode...");
        BufferedReader console = null;
        try {
            log.info("start receive command...");
            console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = console.readLine();
                log.info("receive order ->{}", msg);
                String[] cmd = msg.split(";");
                if (msg == null) {
                    break;
                } else if ("init".equals(cmd[0])) {
                    aiManager.InitAndLogin(cmd[1]);
                }
//                else if ("join".equals(cmd[0])) {
//                    aiManager.addNewMsg("join", null);
//                    AIAsync.randomSwitch = 2;
//                    AIAsync.sleepTime = 3000;
//                } else if ("getAnnounce".equals(cmd[0])) {//
//                    aiManager.addNewMsg("getAnnounce", cmd[0]);
//                } else if ("wincup".equals(cmd[0])) {//
//                    aiManager.addNewMsg("wincup", cmd[1]);
//                } else if ("exc".equals(cmd[0])) {//默认18
//                    aiManager.addNewMsg("exc", cmd[1]);
//                } else if ("sh".equals(cmd[0])) {
//                    setEmojiName(cmd[1]);//测试往数据库插入带emoji的头像
//                } else if ("gh".equals(cmd[0])) {
//                    getEmojiName(cmd[1]);
//                } else if ("create".equals(cmd[0])) {
//                    createNewAI(cmd[1]);
//                } else if ("ip".equals(cmd[0])) {//查看测试工具所在环境ip的方法
//                    getIp();
//                } else if ("getRank".equals(cmd[0])) {
//                    aiManager.addNewMsg("getRank", null);
//                } else if ("getHistoryRank".equals(cmd[0])) {
//                    aiManager.addNewMsg("getHistoryRank", null);
//                } else if ("getHead".equals(cmd[0])) {
//                    aiManager.addNewMsg("getHead", null);
//                } else if ("equipHead".equals(cmd[0])) {
//                    aiManager.addNewMsg("equipHead", null);
//                } else if ("snatch".equals(cmd[0])) {
//                    aiManager.addNewMsg("snatch", cmd[1]);
//                } else if ("item".equals(cmd[0])) {
//                    aiManager.addNewMsg("item", cmd[1]);
//                } else if ("vip".equals(cmd[0])) {
//                    aiManager.addNewMsg("vip", cmd[1]);
//                } else if ("skin".equals(cmd[0])) {
//                    aiManager.addNewMsg("skin", cmd[1]);
//                } else if ("lucky".equals(cmd[0])) {
//                    aiManager.addNewMsg("lucky", null);
//                } else if ("revive".equals(cmd[0])) {
//                    aiManager.addNewMsg("revive", null);
//                } else if ("joinMatch".equals(cmd[0])) {
//                    aiManager.addNewMsg("joinMatch", null);
//                }
            }
        } catch (Exception e) {
            log.info("consolo receive order err!", e);
        } finally {
            if (null != console) {
                try {
                    console.close();
                } catch (Exception e) {
                    log.info("consolo receive order err!", e);
                }
            }
        }
    }

    private void getIp() {
        try {
            log.info("内网ip为{}，外网ip为{},RealIp为{}", IPUtil.INTRANET_IP, IPUtil.INTERNET_IP, IPUtil.getRealIp());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}