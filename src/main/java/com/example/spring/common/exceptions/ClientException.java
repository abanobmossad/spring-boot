package com.example.spring.common.exceptions;

import org.springframework.web.reactive.function.client.WebClientException;

public class ClientException extends WebClientException {
    public ClientException(String msg) {
        super(msg);
        System.out.println(msg);
    }
}