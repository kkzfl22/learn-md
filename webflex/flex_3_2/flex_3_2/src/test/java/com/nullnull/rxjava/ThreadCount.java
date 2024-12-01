package com.nullnull.rxjava;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author nullnull
 * @since 2024/11/30
 */
public class ThreadCount {

    public static void main(String[] args) {

        int randNum = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                int i1 = ThreadLocalRandom.current().nextInt(100, 499);
                int i2 = ThreadLocalRandom.current().nextInt(100, 499);

                System.out.println(i1 + " + " + i2 + " = " );
            }

        }

    }

}
