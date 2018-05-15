package com.yc.sandfactory.task;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author: yc
 * @date: 2018-1-13
 */
@Component
public class DataCollectionTaskStart implements ApplicationContextAware {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Value("${record.check.time}")
  private String time = "15";

  @Override
  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    Map<String, DataCollectionTask> taskMap = applicationContext.getBeansOfType(DataCollectionTask.class);

    if (taskMap.isEmpty()) {
      logger.error("未检测到采集数据接口配置，请检查配置……");
      return;
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(taskMap.size());
    for (DataCollectionTask task : taskMap.values()) {
      scheduler.scheduleAtFixedRate(task, 5, Long.valueOf(time), SECONDS);
    }
  }
}
