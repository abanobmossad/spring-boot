package com.example.spring.controllers;

import com.example.spring.repositories.GeographicAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class GeographicAddressController {
    GeographicAddressRepository geographicAddressRepository;

    @Autowired
    GeographicAddressController(GeographicAddressRepository geographicAddressRepository) {
        this.geographicAddressRepository = geographicAddressRepository;
    }

    @RequestMapping(value = "/getAddressesList", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    private void getAddressList(String tenant) {
        this.geographicAddressRepository.getGeographicAddressesList("Athina");
    }
}
