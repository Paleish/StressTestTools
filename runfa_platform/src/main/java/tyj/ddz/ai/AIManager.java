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

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tyj.Initializer;
import tyj.PressInstrumentApplication;

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
@Slf4j
@Service
public class AIManager {

    public static Map<Integer, AIClient> aiMap = new ConcurrentHashMap<>();

    /**
     * 功能描述: <br>
     * 〈初始化AI，将是否自动化放到这里处理〉
     */
    @Async
    public void InitAndLogin(int index) {
        log.info("start init ai{}!", index);
        AIClient ai = (AIClient) PressInstrumentApplication.getContext().getBean("AIClient");
        ai.setAiMode(Initializer.AI_MODE);
        aiMap.put(index, ai);
        ai.initWebSocket();
    }

    public void addNewMsg(AICommand command, String... args) {
        for (AIClient client : aiMap.values()
        ) {
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            client.setCommand(command);
            client.setCommandArgs(args);
            client.run();
        }
    }
}