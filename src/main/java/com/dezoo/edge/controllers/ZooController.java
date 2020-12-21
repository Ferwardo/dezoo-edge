package com.dezoo.edge.controllers;

import com.dezoo.edge.models.PersonnelMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZooController {

	@Autowired
	private RestTemplate restTemplate;

	//get the base urls from the
	@Value("${personnelService.baseurl}")
	private String personnelServiceBaseUrl;

	@Value("${animalService.baseurl}")
	private String animalServiceBaseUrl;

	@Value("${residenceService.baseurl}")
	private String residenceServiceBaseUrl;

	@GetMapping("/personeel")
	public List<PersonnelMember> getPersonnelmembers(){
		ResponseEntity<List<PersonnelMember>> responseEntityPersonnelMembers =
				restTemplate.exchange("http://" + personnelServiceBaseUrl + "/personnel",
						HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonnelMember>>() {
						});
		return responseEntityPersonnelMembers.getBody();
	}

	@GetMapping("/personeel/{personnelID}")
	public PersonnelMember getPersonnelMemberByPersonnelId(@PathVariable String personnelID){
		PersonnelMember personnelMember =
				restTemplate.getForObject("http://"+personnelServiceBaseUrl+"/personnel/{personnelID}",
				PersonnelMember.class, personnelID);
		return personnelMember;
	}
}
