package com.hutool.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * @author ：bohan.zhou@ucarinc.com
 * @date ：Created in 2020/4/10 16:13
 * @description：
 * @version: $
 */
public class FileTypeException extends RuntimeException {

    private HttpStatus status = null;

    public FileTypeException(HttpStatus httpStatus ,String message) {
        super(message);
        status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}