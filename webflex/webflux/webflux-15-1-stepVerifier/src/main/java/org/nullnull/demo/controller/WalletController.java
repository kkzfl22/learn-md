package org.nullnull.demo.controller;

import org.nullnull.demo.entity.Wallet;
import org.nullnull.demo.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/21
 */
@RestController
@RequestMapping("/wallets")
public class WalletController {

  private final WalletService walletService;

  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @GetMapping("/list")
  @ResponseBody
  public Flux<Wallet> list() {
    return walletService.list();
  }

  @PostMapping("/send")
  public Mono<String> send(Mono<Wallet> data) {
    return walletService.send(data);
  }
}
