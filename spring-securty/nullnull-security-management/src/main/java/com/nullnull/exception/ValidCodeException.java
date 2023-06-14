package com.nullnull.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author liujun
 * @since 2023/6/8
 */
public class ValidCodeException extends AuthenticationException {

    public ValidCodeException(String msg) {
        super(msg);
    }
}
