package com.nullnull.observer;

/**
 * @author liujun
 * @since 2023/7/13
 */
public class ConcreteObserverA implements Observer {

    @Override
    public void observe(String event) {
        System.out.println(getClass().getCanonicalName() + " --- " + event);
    }


}
