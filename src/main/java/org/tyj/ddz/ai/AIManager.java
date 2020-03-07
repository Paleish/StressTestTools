/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AIManagement
 * Author:   Administrator
 * Date:     2019/6/17 10:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tyj.Initializer;
import org.tyj.PressInstrumentApplication;
import org.tyj.ddz.bean.PlayerInfo;
import org.tyj.ddz.service.PlayerInfoService;
import org.tyj.util.shedule.ScheduledThreadPoolUtil;
import org.tyj.web.WebSocketClient;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈AI Init manager〉
 *
 * @author Administrator
 * @create 2019/6/17
 * @since 1.0.0
 */
@Service
public class AIManager {

    private static final Logger logger = LoggerFactory.getLogger(AIManager.class.getName());

    @Resource
    private PlayerInfoService playerInfoService;

    public static List<PlayerInfo> AIPlayerList;

    public static Map<Integer, AIClient> aiMap = new ConcurrentHashMap<>();

    private void ReplenishAI(int ainum) {
        try {
            AIPlayerList = playerInfoService.findAIMultitude(ainum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: <br>
     * 〈初始化AI，将是否自动化放到这里处理〉
     */
    public void InitAI(String AINum) {
        int ainum = Integer.parseInt(AINum);
        logger.info("init ai num is ->{}", ainum);
        if (null == AIPlayerList || AIPlayerList.size() == 0) {
            ReplenishAI(ainum);
        }
        for (PlayerInfo p : AIPlayerList
        ) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AIClient ai = (AIClient) PressInstrumentApplication.getContext().getBean("AIClient");
            ai.setInfo(p);
            ai.setUserId(p.getUserId());
            ai.setAiMode(Initializer.AI_MODE);
            aiMap.put(ai.getUserId(), ai);
            ai.start();
            WebSocketClient client = new WebSocketClient(ai.getUserId(), 0, null);
            try {
                ScheduledThreadPoolUtil.getInstance().execute(() -> client.run());
            } catch (Exception e) {
                logger.error("AI create connection error!", e);
            }
        }
        logger.info("AI init finished！");
    }

    public synchronized void addNewMsg(String method, String args) {
        AIMessage am = new AIMessage(System.currentTimeMillis() + 1000, method, args);
        for (AIClient client : aiMap.values()
        ) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.addMsg(am);
        }
    }
}