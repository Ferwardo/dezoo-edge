package com.dezoo.edge.animalTests;

import com.dezoo.edge.models.Animal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalUnitTests {

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

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private Animal animal1;
    private List<Animal> animalList;

    @BeforeEach
    public void initialize() throws Exception {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        animal1 = new Animal(1,"r001", "Flappie", "Rabbit", format.parse("16/04/2017"), true, "mammal");

        animalList = new ArrayList<Animal>();
        animalList.add(animal1);
        animalList.add(new Animal("l001", "Simba", "Lion", format.parse("24/07/2011"), true, "mammal"));
        animalList.add(new Animal("s001", "Scrat", "Squirrel", format.parse("04/01/2019"), true, "mammal"));
    }

    @Test
    public void whenGetAnimals_thenReturnAnimalsJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + animalServiceBaseUrl + "/animals")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(animalList))
                );

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
    public void whenGetVertebrates_thenReturnAnimalsJson() throws Exception{
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + animalServiceBaseUrl + "/animals/getVertebrates")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(animalList))
                );

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
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + animalServiceBaseUrl + "/animals/r001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(animal1))
                );

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
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + animalServiceBaseUrl + "/animals")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(animal1))
                );

        mockMvc.perform(post("/dieren")
                .content(mapper.writeValueAsString(animal1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateAnimal_thenReturnAnimalJson() throws Exception{
        Animal updatedAnimal = new Animal("r001", "Ronnie", "Rabbit", format.parse("16/04/2017"), true, "mammal");

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://"+animalServiceBaseUrl+"/animals/r001")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedAnimal))
                );

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
    public void whenDeleteAnimal_thenStatusOk() throws Exception{
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + animalServiceBaseUrl + "/animals/r001")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(delete("/dieren/{animalID}", "r001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteAnimal_thenStatusInternalServerError() throws Exception{
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + animalServiceBaseUrl + "/animals/r002")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(delete("/dieren/{animalID}", "r002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
