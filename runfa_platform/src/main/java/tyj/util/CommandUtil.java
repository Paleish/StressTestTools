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
import tyj.ddz.ai.AICommand;
import tyj.ddz.ai.AIManager;

import javax.annotation.Resource;
import java.io.BufferedReader;
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
                    int ainum = Integer.parseInt(cmd[1]);
                    log.info("init ai num is ->{}", ainum);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int index = 0; index < ainum; index++
                    ) {
                        aiManager.InitAndLogin(index);
                    }
                } else if ("join".equals(cmd[0])) {
                    aiManager.addNewMsg(AICommand.JOIN_LANDLORD, cmd[1]);
                }
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