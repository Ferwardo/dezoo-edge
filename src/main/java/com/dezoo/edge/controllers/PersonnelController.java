package com.dezoo.edge.controllers;

import com.dezoo.edge.models.PersonnelMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("personeel")
public class PersonnelController {

    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    //get the base urls from the application.properties file
    @Value("${personnelService.baseurl}")
    private String personnelServiceBaseUrl;

//    @Value("${residenceService.baseurl}")
//    private String residenceServiceBaseUrl;

    /**
     * Gets all personnel from the personnel microservices
     *
     * @return a list of PersonnelMember object representing the personnel members of the zoo
     * @author Ferwardo (Ferre Snyers)
     */
    @GetMapping
    public List<PersonnelMember> getPersonnelmembers() {
        ResponseEntity<List<PersonnelMember>> responseEntityPersonnelMembers =
                restTemplate.exchange("http://" + personnelServiceBaseUrl + "/personnel",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonnelMember>>() {
                        });
        return responseEntityPersonnelMembers.getBody();
    }

    /**
     * Gets a personnel member from the personnel microservices using a personnelId
     *
     * @param personnelID The personnelId of the personnelMember that needs to be returned.
     * @return One PersonnelMember corresponding to the given personnelId
     * @author Ferwardo (Ferre Snyers)
     */
    @GetMapping("/{personnelID}")
    public PersonnelMember getPersonnelMemberByPersonnelId(@PathVariable String personnelID) {
        return restTemplate.getForObject("http://" + personnelServiceBaseUrl + "/personnel/{personnelID}",
                PersonnelMember.class, personnelID);
    }

    /**
     * Add a new personnel member to the database via the personnel microservice
     *
     * @param personnelMember The personnelMember that has to be added.
     * @return The newly added personnel member
     * @author Ferwardo (Ferre Snyers)
     */
    @PostMapping
    public PersonnelMember addPersonnelMember(@RequestBody PersonnelMember personnelMember) {
        return restTemplate.postForObject("http://" + personnelServiceBaseUrl + "/personnel",
                personnelMember, PersonnelMember.class);
    }

    /**
     * Updates a personnel member via the personnel microservice
     *
     * @param personnelMember The personnel member that has to be updated
     * @return The updated personnel member
     * @author Ferwardo (Ferre Snyers)
     */
    @PutMapping
    public PersonnelMember updatePersonnelMember(@RequestBody PersonnelMember personnelMember) {
        return restTemplate.exchange("http://" + personnelServiceBaseUrl + "/personnel",
                HttpMethod.PUT, new HttpEntity<>(personnelMember), PersonnelMember.class).getBody();
    }

    /**
     * Deletes a personnel member
     *
     * @param personnelID the personnel id (not db id) of the personnel member to be deleted
     * @return a status of either 200 when it is deleted or a 5xx status when something went wrong
     * @author Ferwardo (Ferre Snyers)
     */
    @DeleteMapping("/{personnelID}")
    public ResponseEntity<PersonnelMember> deletePersonnelMember(@PathVariable String personnelID) {
        try {
            restTemplate.delete("http://" + personnelServiceBaseUrl + "/personnel/{personnelID}", personnelID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
