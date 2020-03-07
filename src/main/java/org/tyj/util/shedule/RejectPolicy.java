/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: KysRejectPolicy
 * Author:   Administrator
 * Date:     2019/6/25 10:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package org.tyj.util.shedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/6/25
 * @since 1.0.0
 */
public class RejectPolicy implements RejectedExecutionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RejectPolicy.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()) {
            logger.error("ThreadPool is overload,Enter rejectPolicy!");
            r.run();
        }
    }
}