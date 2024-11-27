package com.nullnull.observer;

/**
 * Subject接口
 *
 * @author nullnull
 * @since 2023/7/13
 */
public interface Subject {

    /**
     *  注册观察者
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
