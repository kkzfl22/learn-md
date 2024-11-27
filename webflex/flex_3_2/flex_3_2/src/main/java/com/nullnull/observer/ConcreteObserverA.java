package com.nullnull.observer;

/**
 * @author nullnull
 * @since 2023/7/13
 */
public class ConcreteObserverA implements Observer {

    @Override
    public void observe(String event) {
        System.out.println(getClass().getCanonicalName() + " --- " + event);
    }


}
