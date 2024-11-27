package com.nullnull.observer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author liujun
 * @since 2023/7/13
 */
public class ConcreteSubject implements Subject {

    /**
     * 保证Set是线程安全的
     */
    private Set<Observer> observers = new CopyOnWriteArraySet<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        observers.forEach(observer -> observer.observe(event));
    }

}
