package com.nullnull.spring4observer2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujun
 * @since 2023/7/15
 */
@RestController
public class TemperatureController {

    private final TemperatureSensor temperatureSensor;

    public TemperatureController(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    @RequestMapping(value = "/temperature-stream", method =
            RequestMethod.GET)
    public SseEmitter events(HttpServletRequest request) {

        RxSseEmitter emitter = new RxSseEmitter();
        temperatureSensor.temperatureStream().subscribe(emitter.getSubscriber());

        return emitter;
    }
}
