/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: AIAsc
 * Author:   Administrator
 * Date:     2019/7/29 15:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.ddz.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br>
 * 〈AI async method〉
 *
 * @author Administrator
 * @create 2019/7/29
 * @since 1.0.0
 */
@Component
@Scope("prototype")
public class AIAsync {

    private static final Logger logger = LoggerFactory.getLogger(AIAsync.class.getName());

    public static int randomSwitch = -1;
    public static int sleepTime = 10000;//线程执行等待时间

    @Async
    public void aiOper(AIClient ac) {

    }
}