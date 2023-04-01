package com.example.spring.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoAddressGR {
    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    String city;

    @JsonProperty("street")
    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    String street;

    @JsonProperty("county")
    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    String county;

    @JsonProperty("streetNumbers")
    public String getStreetNumbers() {
        return this.streetNumbers;
    }

    public void setStreetNumbers(String streetNumbers) {
        this.streetNumbers = streetNumbers;
    }

    String streetNumbers;

    @JsonProperty("postCode")
    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    String postCode;

    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    @JsonProperty("addressFormatted")
    public String getAddressFormatted() {
        return this.addressFormatted;
    }

    public void setAddressFormatted(String addressFormatted) {
        this.addressFormatted = addressFormatted;
    }

    String addressFormatted;
}

