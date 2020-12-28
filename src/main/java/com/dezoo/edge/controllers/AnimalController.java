package com.dezoo.edge.controllers;

import com.dezoo.edge.models.Animal;
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
@RequestMapping("/dieren")
public class AnimalController {
    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    @Value("${animalService.baseurl}")
    private String animalServiceBaseUrl;

    /**
     * Gets all animals from the animal microservice
     * @return a list of all animals
     */
    @GetMapping
    private List<Animal> getAnimals(){
        ResponseEntity<List<Animal>> responseEntityAnimals =
                restTemplate.exchange("http://" + animalServiceBaseUrl + "/animals",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Animal>>() {
                        });
        return responseEntityAnimals.getBody();
    }

    /**
     * Gets an from the animal microservices using an animalID
     *
     * @param animalID The animalId of the animal that needs to be returned.
     * @return One Animal corresponding to the given animalId.
     * @author Ferwardo (Ferre Snyers)
     */
    @GetMapping("/{animalID}")
    public Animal getAnimalByAnimalId(@PathVariable String animalID) {
        return restTemplate.getForObject("http://" + animalServiceBaseUrl + "/{animalID}",
                Animal.class, animalID);
    }

    /**
     * Add a new animal to the database via the animal microservice
     *
     * @param animal The animal that needs to be added.
     * @return The newly added animal.
     * @author Ferwardo (Ferre Snyers)
     */
    @PostMapping
    public Animal addAnimal(@RequestBody Animal animal) {
        return restTemplate.postForObject("http://" + animalServiceBaseUrl + "/animals",
                animal, Animal.class);
    }

    /**
     * Updates a animal via the animal microservice
     *
     * @param animal The animal that has to be updated.
     * @return The updated animal.
     * @author Ferwardo (Ferre Snyers)
     */
    @PutMapping
    public Animal updateAnimal(@RequestBody Animal animal) {
        return restTemplate.exchange("http://" + animalServiceBaseUrl + "/animal",
                HttpMethod.PUT, new HttpEntity<>(animal), Animal.class).getBody();
    }

    /**
     * Deletes a animal
     *
     * @param animalID the animal id (not db id) of the animal to be deleted
     * @return a status of either 200 when it is deleted or a 5xx status when something went wrong
     * @author Ferre Snyers
     */
    @DeleteMapping("/{animalID}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable String animalID) {
        try {
            restTemplate.delete("http://" + animalServiceBaseUrl + "/animal/{animalID}", animalID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
