package com.mbw.commons.web.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 多线程执行定时任务
 *
 * @author Mabowen
 * @date 2020-05-29 09:39
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度10的定时任务线程池
        ScheduledThreadPoolExecutor poolExecutor
                = new ScheduledThreadPoolExecutor(10, new BasicThreadFactory.Builder()
                .namingPattern("sync-schedule-pool-%d")
                .daemon(true)
                .build());
        taskRegistrar.setScheduler(poolExecutor);
    }
}
