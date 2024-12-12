package com.nullnull.demo.controller;

import com.nullnull.demo.entity.User;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/12
 */
@RestController
public class AlbumsController {

    final ReactiveAdapterRegistry adapterRegistry;

    public AlbumsController(ReactiveAdapterRegistry adapterRegistry) {
        this.adapterRegistry = adapterRegistry;
    }


    @PostMapping("/info-by-user")
    public Observable<User> findByUserInfo(@RequestBody Mono<String> nameMono) {
        //将Mono转换为Observable类型
        Observable<String> observable = (Observable<String>) adapterRegistry.getAdapter(Observable.class)
                .fromPublisher(nameMono);

        return observable.flatMap(new Function<String, ObservableSource<? extends User>>() {
            @Override
            public ObservableSource<? extends User> apply(String s) throws Exception {
                return Observable.just(new User(s + "-用户名", 18, "地址信息"));
            }
        });
    }
}
