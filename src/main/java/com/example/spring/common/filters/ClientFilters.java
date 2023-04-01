package com.example.spring.common.filters;

import com.example.spring.common.exceptions.ClientException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;


public class ClientFilters {

    public static ExchangeFilterFunction responseErrorFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new ClientException(errorBody)));
            }

            return Mono.just(clientResponse);
        });
    }
}
