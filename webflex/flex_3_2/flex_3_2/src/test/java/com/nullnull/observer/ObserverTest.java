package com.nullnull.observer;

import org.junit.Test;

/**
 * @author nullnull
 * @since 2023/7/13
 */
public class ObserverTest {

    @Test
    public void dataTest() {
        Subject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserverA();
        Observer observer2 = new ConcreteObserverB();
        subject.registerObserver(observer1);
        subject.registerObserver(observer2);
        subject.notifyObservers("hello null null");
        System.out.println("==================================");
        subject.unregisterObserver(observer1);
        subject.notifyObservers("great null null");

    }

    @Test
    public void dataTestLambda() {
        Subject subject = new ConcreteSubject();
        subject.registerObserver(e -> System.out.println("A: " + e));
        subject.registerObserver(e -> System.out.println("B: " + e));
        subject.notifyObservers("This message will receive A & B");

        subject.notifyObservers("hello null null");
        System.out.println("==================================");

    }

}
