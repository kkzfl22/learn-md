package org.nullnull.demo.controller;

import org.junit.jupiter.api.Test;
import org.nullnull.demo.entity.Wallet;
import org.nullnull.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

/**
 * @author nullnull
 * @since 2024/12/21
 */
@WebFluxTest
@Import(WalletService.class)
public class TestWalletController {

  @Autowired WebTestClient webTestClient;

  @Test
  public void verifyResponse() {
    Wallet nullItem = Wallet.wallet("nullnull", 1000);
    Wallet admin = Wallet.wallet("admin", 500);

    webTestClient
        .get()
        .uri("http://127.0.0.1:8080/wallets/list")
        .exchange()
        .expectHeader()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is2xxSuccessful()
        .returnResult(Wallet.class)
        .getResponseBody()
        .as(StepVerifier::create)
        // .expectNextCount(2)
        .expectNextMatches((item) -> item.getOwner().equals(nullItem.getOwner()))
        .expectNextMatches((item) -> item.getOwner().equals(admin.getOwner()))
        .expectComplete()
        .verify();
  }
}
