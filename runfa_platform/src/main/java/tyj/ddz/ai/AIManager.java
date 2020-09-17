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
package tyj.ddz.ai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bro.util.HttpSendUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tyj.Initializer;
import tyj.PressInstrumentApplication;
import tyj.constant.NetConstants;

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

    //public static List<PlayerInfo> AIPlayerList = new ArrayList<>();

    public static Map<Integer, AIClient> aiMap = new ConcurrentHashMap<>();

//    private void ReplenishAI(int ainum) {
//        try {
//            AIPlayerList = playerInfoService.findAIMultitude(ainum);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 功能描述: <br>
     * 〈初始化AI，将是否自动化放到这里处理〉
     */
    public void InitAndLogin(String AINum) {
        int ainum = Integer.parseInt(AINum);
        logger.info("init ai num is ->{}", ainum);
        for (int index = 0; index < ainum; index++
        ) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AIClient ai = (AIClient) PressInstrumentApplication.getContext().getBean("AIClient");
            ai.setAiMode(Initializer.AI_MODE);
            aiMap.put(ai.getUserId(), ai);
            //请求http服务器获取登陆串
            String result = HttpSendUtil.wxHttpGetRequest(NetConstants.HttpUrl);
            JSONObject object = JSON.parseObject(result);
            String url = object.getString("url");
            String token = object.getString("token");
            addNewMsg(AICommand.INIT_WS, url, token);
        }
        logger.info("AI init finished！");
    }

    public void addNewMsg(AICommand command, String... args) {
        for (AIClient client : aiMap.values()
        ) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.setCommand(command);
            client.setCommandArgs(args);
            client.run();
        }
    }
}