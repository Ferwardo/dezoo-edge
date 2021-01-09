package com.dezoo.edge.residenceTests;

import com.dezoo.edge.models.Residence;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ResidenceUnitTests {
    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    //get the base urls from the application.properties file
    @Value("${residenceService.baseurl}")
    private String residenceServiceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Residence residence1;
    private List<Residence> residenceList;

    @BeforeEach
    public void initialise() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        residence1 = new Residence("leeuwen001", "fs161100", "l001", "Pride Rock", 4, "1995", false);

        residenceList = new ArrayList<>();
        residenceList.add(new Residence("1", "konijnen001", "cn170999", "r001", "De Konijnenpijp", 15, "2010", false));
        residenceList.add(new Residence("eekhoorns001", "cn170999", "s001", "De Grote Boom", 8, "2020", false));
    }

    @Test
    public void whenGetResidenceById_thenReturnResidenceJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven/leeuwen001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(residence1))
                );

        mockMvc.perform(get("/verblijven/{residenceID}", "leeuwen001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("leeuwen001")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.personeelID", is("fs161100")))
                .andExpect(jsonPath("$.dierID", is("l001")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwJaar", is("1995")))
                .andExpect(jsonPath("$.nocturnal", is(false)));
    }

    @Test
    public void whenGetResidenceByPersonnelId_thenReturnResidenceJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven/personeel/cn170999")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(residenceList))
                );

        mockMvc.perform(get("/verblijven/personeel/{personeelsId}", "cn170999"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].verblijfID", is("konijnen001")))
                .andExpect(jsonPath("[0].name", is("De Konijnenpijp")))
                .andExpect(jsonPath("[0].personeelID", is("cn170999")))
                .andExpect(jsonPath("[0].dierID", is("r001")))
                .andExpect(jsonPath("[0].maxDieren", is(15)))
                .andExpect(jsonPath("[0].bouwJaar", is("2010")))
                .andExpect(jsonPath("[0].nocturnal", is(false)))

                .andExpect(jsonPath("[1].verblijfID", is("eekhoorns001")))
                .andExpect(jsonPath("[1].name", is("De Grote Boom")))
                .andExpect(jsonPath("[1].personeelID", is("cn170999")))
                .andExpect(jsonPath("[1].dierID", is("s001")))
                .andExpect(jsonPath("[1].maxDieren", is(8)))
                .andExpect(jsonPath("[1].bouwJaar", is("2020")))
                .andExpect(jsonPath("[1].nocturnal", is(false)));
    }

    @Test
    public void whenGetResidenceByAnimalId_thenReturnResidenceJson() throws Exception {
        residenceList.remove(1);
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven/dieren/r001")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(residenceList))
                );

        mockMvc.perform(get("/verblijven/dieren/{dierenID}", "r001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].verblijfID", is("konijnen001")))
                .andExpect(jsonPath("[0].name", is("De Konijnenpijp")))
                .andExpect(jsonPath("[0].personeelID", is("cn170999")))
                .andExpect(jsonPath("[0].dierID", is("r001")))
                .andExpect(jsonPath("[0].maxDieren", is(15)))
                .andExpect(jsonPath("[0].bouwJaar", is("2010")))
                .andExpect(jsonPath("[0].nocturnal", is(false)));
    }

    @Test
    public void whenAddResidence_thenReturnResidenceJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(residence1))
                );

        mockMvc.perform(post("/verblijven")
                .content(mapper.writeValueAsString(residence1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("leeuwen001")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.personeelID", is("fs161100")))
                .andExpect(jsonPath("$.dierID", is("l001")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwJaar", is("1995")))
                .andExpect(jsonPath("$.nocturnal", is(false)));
    }

    @Test
    public void whenUpdateResidence_thenReturnResidenceJson() throws Exception {
        Residence updatedResidence = new Residence("leeuwen001", "fs161100", "l001", "Pride Rock", 4, "1995", false);

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven/")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedResidence))
                );

        mockMvc.perform(put("/verblijven")
                .content(mapper.writeValueAsString(updatedResidence))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("leeuwen001")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.personeelID", is("fs161100")))
                .andExpect(jsonPath("$.dierID", is("l001")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwJaar", is("1995")))
                .andExpect(jsonPath("$.nocturnal", is(false)));
    }

    @Test
    public void whenDeleteResidence_thenStatusOk() throws Exception{
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven/leeuwen001")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(delete("/verblijven/{verblijfID}", "leeuwen001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteAnimal_thenStatusInternalServerError() throws Exception{
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + residenceServiceBaseUrl + "/verblijven/leeuwen002")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(delete("/verblijven/{verblijfID}", "leeuwen002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
