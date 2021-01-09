package com.dezoo.edge.controllers;

import com.dezoo.edge.models.Animal;
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

    @GetMapping("/personeel/{personeelsID}")
    public List<Residence> getResidencesByPersoneelID(@PathVariable String personeelsID){
        ResponseEntity<List<Residence>> responseEntityResidences =
                restTemplate.exchange("http://" + residenceService + "/verblijven/personeel/"+personeelsID,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Residence>>() {
                        });
        return responseEntityResidences.getBody();
    }

    @GetMapping("/dieren/{dierID}")
    public List<Residence> getResidencesByAnimalID(@PathVariable String dierID){
        ResponseEntity<List<Residence>> responseEntityResidences =
                restTemplate.exchange("http://" + residenceService + "/verblijven/dieren/"+dierID,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Residence>>() {
                        });
        return responseEntityResidences.getBody();
    }
    @GetMapping("/{verblijfID}")
    public Residence getResidenceByResidenceID(@PathVariable String verblijfID){
        return restTemplate.getForObject("http://" + residenceService + "/verblijven/{verblijfID}",
                Residence.class, verblijfID);
    }

    @PostMapping
    public Residence addResidence(@RequestBody Residence residence){
        return restTemplate.postForObject("http://" + residenceService + "/verblijven",
                residence, Residence.class);
    }

    @PutMapping
    public Residence updateAnimal(@RequestBody Residence residence) {
        return restTemplate.exchange("http://" + residenceService + "/verblijven/",
                HttpMethod.PUT, new HttpEntity<>(residence), Residence.class).getBody();
    }

    @DeleteMapping("/{verblijfsID}")
    public ResponseEntity<Animal> deleteResidence(@PathVariable String verblijfsID) {
        try {
            restTemplate.delete("http://" + residenceService + "/verblijven/{verblijfsID}", verblijfsID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
