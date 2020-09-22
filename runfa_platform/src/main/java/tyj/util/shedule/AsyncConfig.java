/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ExecutorConfig
 * Author:   Administrator
 * Date:     2019/8/6 17:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * TanYujie       修改时间           版本号              描述
 */
package tyj.util.shedule;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import tyj.util.PoolCfgConstants;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * spring异步调用线程池配置
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Resource
    private RejectPolicy kysRejectPolicy;

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = executorBuilder("spring-async-pool");
        return executor;
    }

    public ThreadPoolTaskExecutor executorBuilder(String namePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(PoolCfgConstants.corePoolSize);
        executor.setMaxPoolSize(PoolCfgConstants.maxPoolSize);
        executor.setKeepAliveSeconds(PoolCfgConstants.keepAliveSeconds);
        executor.setQueueCapacity(PoolCfgConstants.queueCapacity);
        executor.setThreadNamePrefix(namePrefix);
        executor.setRejectedExecutionHandler(kysRejectPolicy);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}