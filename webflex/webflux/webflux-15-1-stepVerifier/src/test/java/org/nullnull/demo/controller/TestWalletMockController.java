package org.nullnull.demo.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.nullnull.demo.entity.Wallet;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TestWalletMockController {

  @Test
  public void verifyResponse() {
    WalletController mockController = Mockito.mock(WalletController.class);
    Wallet nullItem = Wallet.wallet("nullnull", 1000);
    Wallet admin = Wallet.wallet("admin", 500);
    Mockito.when(mockController.list()).thenReturn(Flux.just(nullItem, admin));

    WebTestClient.bindToController(mockController)
        .build()
        .get()
        .uri("/wallets/list")
        .exchange()
        .expectHeader()
        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
        .expectStatus()
        .is2xxSuccessful()
        .returnResult(Wallet.class)
        .getResponseBody()
        .as(StepVerifier::create)
        // .expectNextCount(2)
        .expectNextMatches((item) -> item.getId().equals(nullItem.getId()))
        .expectNextMatches((item) -> item.getId().equals(admin.getId()))
        .expectComplete()
        .verify();
  }
}
