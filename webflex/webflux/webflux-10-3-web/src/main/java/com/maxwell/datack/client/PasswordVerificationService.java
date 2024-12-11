package com.maxwell.datack.client;

import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/11
 */
public interface PasswordVerificationService {

    /**
     * 检查密码接口
     *
     * @param raw
     * @param encoded
     * @return
     */
    Mono<Void> check(String raw, String encoded);

}
