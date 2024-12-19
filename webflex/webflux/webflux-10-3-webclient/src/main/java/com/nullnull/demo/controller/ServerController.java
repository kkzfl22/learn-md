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


    @PostMapping("/get40")
    @ResponseBody
    public Mono<User> getV40(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get41")
    @ResponseBody
    public Mono<User> getV41(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get42")
    @ResponseBody
    public Mono<User> getV42(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get43")
    @ResponseBody
    public Mono<User> getV43(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get44")
    @ResponseBody
    public Mono<User> getV44(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get45")
    @ResponseBody
    public Mono<User> getV45(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get46")
    @ResponseBody
    public Mono<User> getV46(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get47")
    @ResponseBody
    public Mono<User> getV47(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get48")
    @ResponseBody
    public Mono<User> getV48(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get49")
    @ResponseBody
    public Mono<User> getV49(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get50")
    @ResponseBody
    public Mono<User> getV50(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get51")
    @ResponseBody
    public Mono<User> getV51(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get52")
    @ResponseBody
    public Mono<User> getV52(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get53")
    @ResponseBody
    public Mono<User> getV53(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get54")
    @ResponseBody
    public Mono<User> getV54(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get55")
    @ResponseBody
    public Mono<User> getV55(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get56")
    @ResponseBody
    public Mono<User> getV56(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get57")
    @ResponseBody
    public Mono<User> getV57(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get58")
    @ResponseBody
    public Mono<User> getV58(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get59")
    @ResponseBody
    public Mono<User> getV59(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get60")
    @ResponseBody
    public Mono<User> getV60(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get61")
    @ResponseBody
    public Mono<User> getV61(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get62")
    @ResponseBody
    public Mono<User> getV62(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get63")
    @ResponseBody
    public Mono<User> getV63(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get64")
    @ResponseBody
    public Mono<User> getV64(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get65")
    @ResponseBody
    public Mono<User> getV65(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get66")
    @ResponseBody
    public Mono<User> getV66(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get67")
    @ResponseBody
    public Mono<User> getV67(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get68")
    @ResponseBody
    public Mono<User> getV68(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get69")
    @ResponseBody
    public Mono<User> getV69(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get70")
    @ResponseBody
    public Mono<User> getV70(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get71")
    @ResponseBody
    public Mono<User> getV71(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get72")
    @ResponseBody
    public Mono<User> getV72(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get73")
    @ResponseBody
    public Mono<User> getV73(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get74")
    @ResponseBody
    public Mono<User> getV74(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get75")
    @ResponseBody
    public Mono<User> getV75(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get76")
    @ResponseBody
    public Mono<User> getV76(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get77")
    @ResponseBody
    public Mono<User> getV77(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get78")
    @ResponseBody
    public Mono<User> getV78(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get79")
    @ResponseBody
    public Mono<User> getV79(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get80")
    @ResponseBody
    public Mono<User> getV80(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get81")
    @ResponseBody
    public Mono<User> getV81(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get82")
    @ResponseBody
    public Mono<User> getV82(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get83")
    @ResponseBody
    public Mono<User> getV83(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get84")
    @ResponseBody
    public Mono<User> getV84(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get85")
    @ResponseBody
    public Mono<User> getV85(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get86")
    @ResponseBody
    public Mono<User> getV86(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get87")
    @ResponseBody
    public Mono<User> getV87(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get88")
    @ResponseBody
    public Mono<User> getV88(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get89")
    @ResponseBody
    public Mono<User> getV89(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


    @PostMapping("/get90")
    @ResponseBody
    public Mono<User> getV90(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get91")
    @ResponseBody
    public Mono<User> getV91(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get92")
    @ResponseBody
    public Mono<User> getV92(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get93")
    @ResponseBody
    public Mono<User> getV93(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get94")
    @ResponseBody
    public Mono<User> getV94(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get95")
    @ResponseBody
    public Mono<User> getV95(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get96")
    @ResponseBody
    public Mono<User> getV96(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get97")
    @ResponseBody
    public Mono<User> getV97(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get98")
    @ResponseBody
    public Mono<User> getV98(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }

    @PostMapping("/get99")
    @ResponseBody
    public Mono<User> getV99(@RequestBody User req) {
        return Mono.just(req).delayElement(Duration.ofMillis(random.nextInt(5000)));
    }


}
