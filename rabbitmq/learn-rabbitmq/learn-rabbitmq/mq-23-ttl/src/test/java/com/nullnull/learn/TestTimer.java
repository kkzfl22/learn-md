package com.nullnull.learn;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author liujun
 * @since 2023/8/28
 */
public class TestTimer {

  @Test
  public void timerTask() throws Exception {
    Timer timer = new Timer();

    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            LocalDateTime outTime = LocalDateTime.now();
            System.out.println("用户没有付款，交易取消:" + outTime);
            timer.cancel();
          }
        };

    System.out.println("等待用户付款:" + LocalDateTime.now());
    // 在20秒后执行定时调用
    timer.schedule(task, 5 * 1000);

    Thread.sleep(7000);
  }

  @Test
  public void scheduleExecute() throws Exception {
    ThreadFactory factory = Executors.defaultThreadFactory();
    ScheduledExecutorService service = new ScheduledThreadPoolExecutor(10, factory);

    System.out.println("等待用户付款:" + LocalDateTime.now());

    service.schedule(
        new Runnable() {
          @Override
          public void run() {
            LocalDateTime outTime = LocalDateTime.now();
            System.out.println("用户没有付款，交易取消:" + outTime);
          }
        },
        // 等待5秒
        5,
        TimeUnit.SECONDS);

    Thread.sleep(7000);
  }
}
