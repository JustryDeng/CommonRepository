package com.szlzcl.jwttoken.util;


/**
 * 签名校验失败异常
 * 即:token被篡改异常
 *
 * @author JustryDeng
 * @date 2019/7/21 14:23
 */
@SuppressWarnings("all")
public class JwtSignatureVerifyException extends Exception {

    private static final long serialVersionUID = -861994790728930634L;

    /**
     * Creates a new JwtSignatureVerifyException with the specified message.
     *
     * @param message The exception message.
     */
    public JwtSignatureVerifyException(String message) {
        super(message);
    }

    /**
     * Creates a new JwtSignatureVerifyException with the specified message and cause.
     *
     * @param message The exception message.
     * @param cause   The exception cause.
     */
    public JwtSignatureVerifyException(String message, Throwable cause) {
        super(message, cause);
    }
}
