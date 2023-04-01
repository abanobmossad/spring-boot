package com.example.spring.common.facories;

import com.example.spring.common.filters.ClientFilters;
import com.example.spring.common.filters.CookieExchangeFilterFunction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Collections;
import java.util.List;


public class ClientFactory {

    private static final int MAX_IN_MEMORY_SIZE = 2000;

    public static WebClient createWebClient(String url) {
        // Create a cookie manager with an accept-all policy
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        ExchangeFilterFunction exchangeFilterFunction = ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    cookieManager
                            .getCookieStore()
                            .getCookies()
                            .forEach(httpCookie -> clientRequest.cookies().add(httpCookie.getName(), httpCookie.getValue()));
                    return Mono.just(clientRequest);
                }
        ).andThen(ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                    List<String> setCookiesHeader = clientResponse.headers().asHttpHeaders().get(HttpHeaders.SET_COOKIE);
                    System.out.println(setCookiesHeader);
                    if (setCookiesHeader != null) {
                        setCookiesHeader.forEach(cookie -> {
                            List<HttpCookie> httpCookies = HttpCookie.parse(cookie);
                            // Add the cookies to the cookie manager
                            httpCookies.forEach(httpCookie -> cookieManager.getCookieStore().add(URI.create(url), httpCookie));
                        });
                    }
                    return Mono.just(clientResponse);
                }
        ));

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .filter(ClientFilters.responseErrorFilter())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", url))
                .filter(exchangeFilterFunction)
                .build();

        return webClient;
    }
}
