package org.nullnull.demo;

import java.time.Duration;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.nullnull.demo.entity.Wallet;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;
import reactor.test.scheduler.VirtualTimeScheduler;
import reactor.util.context.Context;
import reactor.util.function.Tuple2;

/**
 * @author nullnull
 * @since 2024/12/20
 */
public class TestStepVerifier2 {

  /** 在一个很长流或者无限流的情况下，可以使用cancel方法 */
  @Test
  public void cancel() {
    Flux<String> longTimePublish =
        Flux.range(1, 1000000).delayElements(Duration.ofMillis(500)).map(item -> item + "");
    StepVerifier.create(longTimePublish)
        .expectSubscription()
        .expectNext("1")
        .expectNext("2")
        .thenCancel()
        .verify();
  }

  /** 使用背压控制，并检查出错误 */
  @Test
  public void BackPressureBuffer() {
    Flux<String> longTimePublish =
        Flux.create(
            s -> {
              s.next("1");
              s.next("2");
              s.error(new IllegalStateException());
            });

    StepVerifier
        // 使用背压控制
        .create(longTimePublish.onBackpressureBuffer(5), 0)
        .expectSubscription()
        .thenRequest(1)
        .expectNext("1")
        .thenRequest(1)
        .expectNext("2")
        .expectError()
        .verify();
  }

  @Test
  public void BackPressureBufferMap() {
    Flux<String> longTimePublish =
        Flux.range(1, 1000000)
            .delayElements(Duration.ofMillis(100))
            .map(
                item -> {
                  if (item <= 2) {
                    return item + "";
                  }
                  throw new IllegalStateException("error");
                });

    StepVerifier
        // 使用背压控制
        .create(longTimePublish.onBackpressureBuffer(5), 0)
        .expectSubscription()
        .thenRequest(1)
        .expectNext("1")
        .thenRequest(1)
        .expectNext("2")
        .expectError(IllegalStateException.class)
        .verify();
  }

  /** 如何在测试执行期间触发新事件 */
  @Test
  public void testNewEvent() {
    TestPublisher<String> testPublisher = TestPublisher.create();
    StepVerifier.create(findAllById(testPublisher))
        .expectSubscription()
        // 发送1
        .then(() -> testPublisher.next("1"))
        .assertNext(w -> Assert.assertThat(w, Matchers.hasProperty("id", Matchers.equalTo("1"))))
        .then(() -> testPublisher.next("2"))
        .assertNext(w -> Assert.assertThat(w, Matchers.hasProperty("id", Matchers.equalTo("2"))))
        .then(testPublisher::complete)
        .expectComplete()
        .verify();
  }

  private Publisher<Wallet> findAllById(Publisher<String> publisher) {
    return Flux.from(publisher).map(item -> Wallet.wallet(item, "name", 10, "usd"));
  }

  /** StepVerifier 验证 */
  @Test
  public void timeCheck() {
    StepVerifier.create(sendWithInterval())
        .expectSubscription()
        .expectNext("a", "b", "c")
        .expectComplete()
        .verify();
  }

  /** 虚拟时间 验证 */
  @Test
  public void virtualTimeCheck() {
    StepVerifier.withVirtualTime(() -> sendWithInterval())
        .expectSubscription()
        .then(() -> VirtualTimeScheduler.get().advanceTimeBy(Duration.ofMinutes(3)))
        .expectNext("a", "b", "c")
        .expectComplete()
        .verify();
  }

  /** 限制方案上花费的时间 */
  @Test
  public void timeLimit() {
    Duration verify =
        StepVerifier.withVirtualTime(() -> sendWithInterval())
            .expectSubscription()
            .thenAwait(Duration.ofMinutes(3))
            .expectNext("a", "b", "c")
            .expectComplete()
            .verify();
    System.out.println("Verification took:" + verify);
  }

  public Flux<String> sendWithInterval() {
    return Flux.interval(Duration.ofMinutes(1))
        .zipWith(Flux.just("a", "b", "c"))
        .map(Tuple2::getT2);
  }

  /** 检查指定的时间收到指定事件 */
  @Test
  public void timeCheckEvent() {
    Duration verify =
        StepVerifier.withVirtualTime(() -> sendWithInterval2())
            .expectSubscription()
            // 用于表示在指定的时间内没有收到事件
            .expectNoEvent(Duration.ofMillis(100))
            .expectNext("a")
            // 用于表示在指定的时间内没有收到事件
            .expectNoEvent(Duration.ofMillis(100))
            .expectNext("b")
            // 用于表示在指定的时间内没有收到事件
            .expectNoEvent(Duration.ofMillis(100))
            .expectNext("c")
            .expectComplete()
            .verify();
  }

  public Flux<String> sendWithInterval2() {
    return Flux.interval(Duration.ofMillis(100))
        .zipWith(Flux.just("a", "b", "c"))
        .map(Tuple2::getT2);
  }

  /** 要在下一个设置中接收第一个预定事件。 */
  @Test
  public void virtualTime() {
    StepVerifier.withVirtualTime(
            () ->
                Flux.interval(Duration.ofMillis(0), Duration.ofMillis(1000))
                    .zipWith(Flux.just("a", "b", "c"))
                    .map(Tuple2::getT2))
        .expectSubscription()
        .thenAwait()
        .expectNext("a")
        .expectNoEvent(Duration.ofMillis(1000))
        .expectNext("b")
        .expectNoEvent(Duration.ofMillis(1000))
        .expectNext("c")
        .expectComplete()
        .verify();
  }

  @Test
  public void testContext() {
    StepVerifier.create(login("admin", "admin"))
        .expectSubscription()
        .expectAccessibleContext()
        .hasKey("security")
        .then()
        .expectNextMatches(c -> c.hasKey("security"))
        .verifyComplete();
  }

  private Publisher<Context> login(String userName, String password) {
    String keyMsg = "security";
    Mono<Context> map =
        Mono.just(userName)
            .flatMap(v -> Mono.subscriberContext())
            .subscriberContext(Context.of(keyMsg, "value"));

    return map;
  }

  private void print(Context context) {
    System.out.println("{");
    System.out.println(context);
    System.out.println("}");
  }

  @Test
  public void monoSubscriberContextWithMergedEmpty() {
    StepVerifier.create(
            Mono.just("foo")
                .flatMap(v -> Mono.subscriberContext())
                .subscriberContext(Context.empty())
                .subscriberContext(Context.of("initial", "value")))
        .expectSubscription()
        .expectAccessibleContext()
        .hasKey("initial")
        .then()
        .expectNextMatches(c -> c.hasKey("initial"))
        .verifyComplete();
  }

  @Test
  public void currentContext() throws InterruptedException {
    StepVerifier.create(
            Mono.just("foo")
                .flatMap(d -> Mono.subscriberContext().map(c -> d + c.get(Integer.class)))
                .subscriberContext(ctx -> ctx.put(Integer.class, ctx.get(Integer.class) + 1))
                .flatMapMany(Mono::just)
                .subscriberContext(ctx -> ctx.put(Integer.class, 0)))
        .expectNext("foo1")
        .verifyComplete();
  }
}
