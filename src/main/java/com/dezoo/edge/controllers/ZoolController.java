package com.dezoo.edge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ZoolController {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${personnelservice.baseurl}")
	private String personnelServiceBaseUrl;
}
