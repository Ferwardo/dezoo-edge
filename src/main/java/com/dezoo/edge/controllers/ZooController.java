package com.dezoo.edge.controllers;

import com.dezoo.edge.models.PersonnelMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

	//Personnel functions
	//TODO add function to get personnel with the residence they work in
	/**
	 * Gets all personnel from the personnel microservices
	 * @return            a list of PersonnelMember object representing the personnel members of the zoo
	 * @author            Ferwardo (Ferre Snyers)
	 */
	@GetMapping("/personeel")
	public List<PersonnelMember> getPersonnelmembers(){
		ResponseEntity<List<PersonnelMember>> responseEntityPersonnelMembers =
				restTemplate.exchange("http://" + personnelServiceBaseUrl + "/personnel",
						HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonnelMember>>() {
						});
		return responseEntityPersonnelMembers.getBody();
	}

	/**
	 * gets a personnel member from the personnel microservices using a personnelId
	 * @param personnelID The personnelId of the personnelMember that needs to be returned.
	 * @return            One PersonnelMember corresponding to the given personnelId
	 * @author            Ferwardo (Ferre Snyers)
	 */
	@GetMapping("/personeel/{personnelID}")
	public PersonnelMember getPersonnelMemberByPersonnelId(@PathVariable String personnelID){
		return restTemplate.getForObject("http://"+personnelServiceBaseUrl+"/personnel/{personnelID}",
		PersonnelMember.class, personnelID);
	}

	@PostMapping("/personeel")
	public PersonnelMember addPersonnelMember(@RequestBody PersonnelMember personnelMember){
		return restTemplate.postForObject("http://"+personnelServiceBaseUrl+"/personnel",personnelMember,PersonnelMember.class);
	}
}
