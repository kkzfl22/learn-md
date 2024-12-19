package com.nullnull.demo.controller;

import com.nullnull.demo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

/**
 * @author nullnull
 * @since 2024/12/19
 */
@RestController
@RequestMapping("/server")
public class ServerController {


    private Random random = new Random();


    @PostMapping("/get1")
    @ResponseBody
    public Mono<User> getV1(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get2")
    @ResponseBody
    public Mono<User> getV2(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get3")
    @ResponseBody
    public Mono<User> getV3(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get4")
    @ResponseBody
    public Mono<User> getV4(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get5")
    @ResponseBody
    public Mono<User> getV5(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get6")
    @ResponseBody
    public Mono<User> getV6(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get7")
    @ResponseBody
    public Mono<User> getV7(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get8")
    @ResponseBody
    public Mono<User> getV8(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get9")
    @ResponseBody
    public Mono<User> getV9(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get10")
    @ResponseBody
    public Mono<User> getV10(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get11")
    @ResponseBody
    public Mono<User> getV11(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get12")
    @ResponseBody
    public Mono<User> getV12(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get13")
    @ResponseBody
    public Mono<User> getV13(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get14")
    @ResponseBody
    public Mono<User> getV14(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get15")
    @ResponseBody
    public Mono<User> getV15(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get16")
    @ResponseBody
    public Mono<User> getV16(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get17")
    @ResponseBody
    public Mono<User> getV17(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get18")
    @ResponseBody
    public Mono<User> getV18(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get19")
    @ResponseBody
    public Mono<User> getV19(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get20")
    @ResponseBody
    public Mono<User> getV20(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get21")
    @ResponseBody
    public Mono<User> getV21(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get22")
    @ResponseBody
    public Mono<User> getV22(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get23")
    @ResponseBody
    public Mono<User> getV23(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get24")
    @ResponseBody
    public Mono<User> getV24(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get25")
    @ResponseBody
    public Mono<User> getV25(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get26")
    @ResponseBody
    public Mono<User> getV26(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get27")
    @ResponseBody
    public Mono<User> getV27(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get28")
    @ResponseBody
    public Mono<User> getV28(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get29")
    @ResponseBody
    public Mono<User> getV29(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get30")
    @ResponseBody
    public Mono<User> getV30(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get31")
    @ResponseBody
    public Mono<User> getV31(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get32")
    @ResponseBody
    public Mono<User> getV32(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get33")
    @ResponseBody
    public Mono<User> getV33(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get34")
    @ResponseBody
    public Mono<User> getV34(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get35")
    @ResponseBody
    public Mono<User> getV35(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get36")
    @ResponseBody
    public Mono<User> getV36(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get37")
    @ResponseBody
    public Mono<User> getV37(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get38")
    @ResponseBody
    public Mono<User> getV38(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get39")
    @ResponseBody
    public Mono<User> getV39(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

}
