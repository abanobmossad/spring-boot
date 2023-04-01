package com.example.spring.repositories;

import com.example.spring.models.GeoAddressGR;
import com.example.spring.models.GeoAddressGRResponse;
import com.example.spring.common.facories.ClientFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;


@Repository
public class GeographicAddressRepository {

    private final WebClient webClient;

    GeographicAddressRepository() {
        String url = "https://www.vodafone.gr/service/";
        this.webClient = ClientFactory.createWebClient(url);
    }

    public ArrayList<GeoAddressGR> getGeographicAddressesList(String streetName) {
        // Vodafone GR query params
        final MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.put("type", Collections.singletonList("typeAhead"));
        requestParams.put("street", Collections.singletonList(streetName));

        GeoAddressGRResponse dd = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/").queryParams(requestParams).build())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<GeoAddressGRResponse>() {
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(this::is5xxServerError))
                .block();

        assert dd != null;
        return dd.getResults();
    }

    private boolean is5xxServerError(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode().is3xxRedirection();
    }
}
