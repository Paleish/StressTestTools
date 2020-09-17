/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Initializer
 * Author:   Administrator
 * Date:     2020/2/10 10:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package tyj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tyj.constant.AIModeEnum;
import tyj.util.CommandUtil;
import tyj.util.shedule.ScheduledThreadPoolUtil;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020/2/10
 * @since 1.0.0
 */
@Component
public class Initializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Initializer.class.getName());

    public static int AI_SIGN;//ai标记

    public static AIModeEnum AI_MODE;//测试模式，0自动，1手动

    public static String AI_NUM;//自动模式下开启AI个数

    @Resource
    private CommandUtil commandUtil;

    @Override
    public void run(String... args) {
        logger.info("Initializes based on input parameters!AI tag number is :{},Test mode is:{}(0-auto，1-command)", args[0], args[1]);
        AI_SIGN = Integer.parseInt(args[0]);
        AI_MODE = AIModeEnum.getPropOrigin(Integer.parseInt(args[1]));
        if (AI_MODE == AIModeEnum.AUTO_MODE) {
            logger.info("This automatic mode enables ai{}.", args[2]);
            AI_NUM = args[2];
        }
        //开启控制台命令
        ScheduledThreadPoolUtil.getInstance().execute(commandUtil);
        logger.info("Start server complete!");
    }
}