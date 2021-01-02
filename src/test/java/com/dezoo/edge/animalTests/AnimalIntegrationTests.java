package com.dezoo.edge.animalTests;

import com.dezoo.edge.models.Animal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalIntegrationTests {

    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    //get the base urls from the application.properties file
    @Value("${animalService.baseurl}")
    private String animalServiceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    public void initialiseDb() throws Exception {
        //initialise animal for the db
        Animal animal1 = new Animal("r001", "Flappie", "Rabbit", format.parse("16/04/2017"), true, "mammal");
        Animal animal2 = new Animal("l001", "Simba", "Lion", format.parse("24/07/2011"), true, "mammal");
        Animal animal3 = new Animal("s001", "Scrat", "Squirrel", format.parse("04/01/2019"), true, "mammal");

        //delete all animals from the database

        try {
            restTemplate.delete("http://" + animalServiceBaseUrl + "/animals/{animalID}", "r001");
            restTemplate.delete("http://" + animalServiceBaseUrl + "/animals/{animalID}", "l001");
            restTemplate.delete("http://" + animalServiceBaseUrl + "/animals/{animalID}", "s001");
            restTemplate.delete("http://" + animalServiceBaseUrl + "/animals/{animalID}", "r002");
        } catch (Exception ignore) {
        }

        //post the animals to the database
        restTemplate.postForObject("http://" + animalServiceBaseUrl + "/animals",
                animal1, Animal.class);
        restTemplate.postForObject("http://" + animalServiceBaseUrl + "/animals",
                animal2, Animal.class);
        restTemplate.postForObject("http://" + animalServiceBaseUrl + "/animals",
                animal3, Animal.class);
    }

    @Test
    public void whenGetAnimals_thenReturnAnimalsJson() throws Exception {
        mockMvc.perform(get("/dieren"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].animalId", is("r001")))
                .andExpect(jsonPath("[0].name", is("Flappie")))
                .andExpect(jsonPath("[0].kind", is("Rabbit")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2017-04-15T22:00:00.000+00:00")))
                .andExpect(jsonPath("[0].vertebrate", is(true)))
                .andExpect(jsonPath("[0].classification", is("mammal")))

                .andExpect(jsonPath("[1].animalId", is("l001")))
                .andExpect(jsonPath("[1].name", is("Simba")))
                .andExpect(jsonPath("[1].kind", is("Lion")))
                .andExpect(jsonPath("[1].dateOfBirth", is("2011-07-23T22:00:00.000+00:00")))
                .andExpect(jsonPath("[1].vertebrate", is(true)))
                .andExpect(jsonPath("[1].classification", is("mammal")))

                .andExpect(jsonPath("[2].animalId", is("s001")))
                .andExpect(jsonPath("[2].name", is("Scrat")))
                .andExpect(jsonPath("[2].kind", is("Squirrel")))
                .andExpect(jsonPath("[2].dateOfBirth", is("2019-01-03T23:00:00.000+00:00")))
                .andExpect(jsonPath("[2].vertebrate", is(true)))
                .andExpect(jsonPath("[2].classification", is("mammal")));
    }

    @Test
    public void whenGetVertebrates_thenReturnAnimalsJson() throws Exception {
        mockMvc.perform(get("/dieren/gewervelden"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[0].animalId", is("r001")))
                .andExpect(jsonPath("[0].name", is("Flappie")))
                .andExpect(jsonPath("[0].kind", is("Rabbit")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2017-04-15T22:00:00.000+00:00")))
                .andExpect(jsonPath("[0].vertebrate", is(true)))
                .andExpect(jsonPath("[0].classification", is("mammal")))

                .andExpect(jsonPath("[1].animalId", is("l001")))
                .andExpect(jsonPath("[1].name", is("Simba")))
                .andExpect(jsonPath("[1].kind", is("Lion")))
                .andExpect(jsonPath("[1].dateOfBirth", is("2011-07-23T22:00:00.000+00:00")))
                .andExpect(jsonPath("[1].vertebrate", is(true)))
                .andExpect(jsonPath("[1].classification", is("mammal")))

                .andExpect(jsonPath("[2].animalId", is("s001")))
                .andExpect(jsonPath("[2].name", is("Scrat")))
                .andExpect(jsonPath("[2].kind", is("Squirrel")))
                .andExpect(jsonPath("[2].dateOfBirth", is("2019-01-03T23:00:00.000+00:00")))
                .andExpect(jsonPath("[2].vertebrate", is(true)))
                .andExpect(jsonPath("[2].classification", is("mammal")));
    }

    @Test
    public void whenGetAnimalByAnimalId_thenReturnAnimalJson() throws Exception {
        mockMvc.perform(get("/dieren/{animalId}", "r001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", is("r001")))
                .andExpect(jsonPath("$.name", is("Flappie")))
                .andExpect(jsonPath("$.kind", is("Rabbit")))
                .andExpect(jsonPath("$.dateOfBirth", is("2017-04-15T22:00:00.000+00:00")))
                .andExpect(jsonPath("$.vertebrate", is(true)))
                .andExpect(jsonPath("$.classification", is("mammal")));

    }

    @Test
    public void whenAddAnimal() throws Exception {
        Animal animal = new Animal("r002", "Ronnie", "Rabbit", format.parse("16/04/2017"), true, "mammal");
        mockMvc.perform(post("/dieren")
                .content(mapper.writeValueAsString(animal))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateAnimal_thenReturnAnimalJson() throws Exception {
        Animal updatedAnimal = new Animal("r001", "Ronnie", "Rabbit", format.parse("16/04/2017"), true, "mammal");

        mockMvc.perform(put("/dieren/{animalId}", "r001")
                .content(mapper.writeValueAsString(updatedAnimal))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animalId", is("r001")))
                .andExpect(jsonPath("$.name", is("Ronnie")))
                .andExpect(jsonPath("$.kind", is("Rabbit")))
                .andExpect(jsonPath("$.dateOfBirth", is("2017-04-15T22:00:00.000+00:00")))
                .andExpect(jsonPath("$.vertebrate", is(true)))
                .andExpect(jsonPath("$.classification", is("mammal")));
    }

    @Test
    public void whenDeleteAnimal_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/dieren/{animalID}", "r001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteAnimal_thenStatusInternalServerError() throws Exception {

        mockMvc.perform(delete("/dieren/{animalID}", "r002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
