package com.dezoo.edge.controllers;

import com.dezoo.edge.models.Residence;
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

@RestController
@RequestMapping("/verblijven")
public class ResidenceController {
    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    @Value("${residenceService.baseurl}")
    private String residenceService;

    /**
     * Gets all residences linked to a personnel member
     * @param personeelsID the id of the personnel member
     * @return A list of residences linked with the mentioned personnel member
     */
    @GetMapping("/personeel/{personeelsID}")
    public List<Residence> getResidencesByPersoneelID(@PathVariable String personeelsID){
        ResponseEntity<List<Residence>> responseEntityResidences =
                restTemplate.exchange("http://" + residenceService + "/verblijven/personeel/"+personeelsID,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Residence>>() {
                        });
        return responseEntityResidences.getBody();
    }

    /**
     * Get all residences of a particular animal
     * @param dierID the id of the animal
     * @return A list of residences of the mentioned animal
     */
    @GetMapping("/dieren/{dierID}")
    public List<Residence> getResidencesByAnimalID(@PathVariable String dierID){
        ResponseEntity<List<Residence>> responseEntityResidences =
                restTemplate.exchange("http://" + residenceService + "/verblijven/dieren/"+dierID,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Residence>>() {
                        });
        return responseEntityResidences.getBody();
    }

    /**
     * Get a residence by residence id
     * @param verblijfID the id of the residence wanted
     * @return A residence
     */
    @GetMapping("/{verblijfID}")
    public Residence getResidenceByResidenceID(@PathVariable String verblijfID){
        return restTemplate.getForObject("http://" + residenceService + "/verblijven/{verblijfID}",
                Residence.class, verblijfID);
    }

    /**
     * Adds a residence to the database
     * @param residence the residence to be added
     * @return the newly added residence
     */
    @PostMapping
    public Residence addResidence(@RequestBody Residence residence){
        return restTemplate.postForObject("http://" + residenceService + "/verblijven",
                residence, Residence.class);
    }

    /**
     * Updates a residence
     * @param residence the residence to be updated
     * @return the updated residence
     */
    @PutMapping
    public Residence updateResidence(@RequestBody Residence residence) {
        return restTemplate.exchange("http://" + residenceService + "/verblijven/",
                HttpMethod.PUT, new HttpEntity<>(residence), Residence.class).getBody();
    }

    /**
     * Deletes a residence from the database
     * @param verblijfsID the residence id of the residence to be deleted
     * @return a status of either 200 when it is deleted or a 5xx status when something went wrong
     */
    @DeleteMapping("/{verblijfsID}")
    public ResponseEntity<Residence> deleteResidence(@PathVariable String verblijfsID) {
        try {
            restTemplate.delete("http://" + residenceService + "/verblijven/{verblijfsID}", verblijfsID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
