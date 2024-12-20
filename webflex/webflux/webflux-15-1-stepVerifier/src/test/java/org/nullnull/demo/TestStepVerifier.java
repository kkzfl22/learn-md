package org.nullnull.demo;

import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.nullnull.demo.entity.Wallet;
import org.reactivestreams.Publisher;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author nullnull
 * @since 2024/12/20
 */
public class TestStepVerifier {

  /** 验证过程中事件发生的顺序 */
  @Test
  public void create() {
    StepVerifier.create(Flux.just("hello", "nullnull"))
        .expectSubscription()
        .expectNext("hello")
        .expectNext("nullnull")
        .expectComplete()
        .verify();
  }

  @Test
  public void expectNextCount() {
    StepVerifier.create(Flux.range(0, 100))
        .expectSubscription()
        // 期望下一个元素与指定值相等
        .expectNext(0)
        // 从上个期望或从开始订阅开始，期望收到指定个数的元素
        .expectNextCount(98)
        // 期望下个元素与指定的相等
        .expectNext(99)
        // 期望收到omComplete信号
        .expectComplete()
        // 阻塞验证
        .verify();
  }

  /** 在验证负责按特定规则过滤或选择元素的代码时，检查所有发出的项是否与过滤规则匹配非常重要。 */
  @Test
  public void assertThatTest() {
    Publisher<Wallet> usersWallets = findAllUsersWallets();
    StepVerifier.create(usersWallets)
        .expectSubscription()
        .recordWith(ArrayList::new)
        .expectNextCount(1)
        .consumeRecordedWith(
            wallets ->
                Assert.assertThat(
                    wallets,
                    CoreMatchers.everyItem(
                        Matchers.hasProperty("owner", CoreMatchers.equalTo("admin")))))
        .expectComplete()
        .verify();
  }

  private Publisher<Wallet> findAllUsersWallets() {
    return Flux.just(Wallet.wallet("admin", 1000));
  }

  /** 下一个元素的期望定义 */
  @Test
  public void nextMatches() {
    StepVerifier.create(Flux.just("hello", "nullnull"))
        .expectSubscription()
        .expectNextMatches(e -> e.startsWith("hel"))
        .expectNextMatches(e -> e.startsWith("null"))
        .expectComplete()
        .verify();
  }

  /** 编写自定义断言 */
  @Test
  public void assertNext() {
    StepVerifier.create(findUsersUSDWallet())
        .expectSubscription()
        .assertNext(
            wallet ->
                Assert.assertThat(
                    wallet, Matchers.hasProperty("currency", CoreMatchers.equalTo("USD"))))
        .expectComplete()
        .verify();
  }

  private Publisher<Wallet> findUsersUSDWallet() {
    return Flux.just(Wallet.wallet("admin", 1000, "USD"));
  }

  /** 错误验证 */
  @Test
  public void expectError() {
    StepVerifier.create(Flux.error(new RuntimeException("Error"))).expectError().verify();
  }

  /** 登录错误的验证 */
  @Test
  public void loginError() {
    StepVerifier.create(login("admin", "wrong"))
        .expectSubscription()
        .expectError(BadCredentialsException.class)
        .verify();
  }

  private Publisher<Void> login(String userName, String password) {
    return Flux.error(new BadCredentialsException("loginError"));
  }
}
