package com.example.spring.common.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class CookieExchangeFilterFunction implements ExchangeFilterFunction {

    private final CookieStore cookieStore;

    public CookieExchangeFilterFunction(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        URI url = request.url();
        List<HttpCookie> cookies = cookieStore.get(url);

        if (!cookies.isEmpty()) {
            request.headers().set(HttpHeaders.COOKIE, formatCookies(cookies));
        }

        return next.exchange(request);
    }

    private String formatCookies(List<HttpCookie> cookies) {
        return cookies.stream()
                .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                .collect(Collectors.joining("; "));
    }
}