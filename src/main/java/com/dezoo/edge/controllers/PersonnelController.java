package com.dezoo.edge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("personnel")
public class PersonnelController {
	@Autowired
	private RestTemplate restTemplate;
}
