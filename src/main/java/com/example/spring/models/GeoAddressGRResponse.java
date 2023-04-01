package com.example.spring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GeoAddressGRResponse {
    ArrayList<GeoAddressGR> results;

    @JsonProperty("results")
    public ArrayList<GeoAddressGR> getResults() {
        return this.results;
    }
}
