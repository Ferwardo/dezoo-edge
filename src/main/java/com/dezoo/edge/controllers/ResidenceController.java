package com.dezoo.edge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/verblijven")
public class ResidenceController {
    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    @Value("${animalService.baseurl}")
    private String animalServiceBaseUrl;


}
