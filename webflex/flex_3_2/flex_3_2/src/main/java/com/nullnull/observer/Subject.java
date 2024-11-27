package com.nullnull.observer;

/**
 * @author liujun
 * @since 2023/7/13
 */
public interface Subject {


    /**
     * * 注册观察者
     * <p>
     * * @param observer
     * <p>
     * 1
     * 2
     * 3
     * 4
     * 5
     * 6
     * 7
     * <p>
     * Observer接口：
     * <p>
     * <p>
     * Subject的实现类：
     * <p>
     */

    void registerObserver(Observer observer);

    /**
     * 解绑观察者
     *
     * @param observer
     */


    void unregisterObserver(Observer observer);

    /**
     * 通知事件变更
     *
     * @param event
     */


    void notifyObservers(String event);


}
