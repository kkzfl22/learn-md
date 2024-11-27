package com.nullnull.flex.self;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nullnull
 * @since 2024/11/27
 */
public class TckTest extends PublisherVerification<String> {


    public TckTest()
    {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<String> createPublisher(long l) {

        Set<String> elements = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            elements.add(String.valueOf(i));
        }
        final ExecutorService executorService =
                Executors.newFixedThreadPool(5);
        AsyncIterablePublisher<String> publisher
                = new AsyncIterablePublisher<>(elements, executorService);
        return publisher;
    }

    @Override
    public Publisher<String> createFailedPublisher() {

        Set elements = new HashSet<>();
        elements.add(new RuntimeException("手动异常"));

        final ExecutorService executorService =
                Executors.newFixedThreadPool(5);

        return new AsyncIterablePublisher<>(elements,executorService);
    }

    @Override
    public long maxElementsFromPublisher() {
        //return super.maxElementsFromPublisher();
        return 10;
    }
}
