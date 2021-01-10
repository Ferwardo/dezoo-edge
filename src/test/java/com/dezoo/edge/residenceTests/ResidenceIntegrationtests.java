package com.dezoo.edge.residenceTests;

import com.dezoo.edge.models.Residence;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ResidenceIntegrationtests {
    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    //get the base urls from the application.properties file
    @Value("${residenceService.baseurl}")
    private String residenceServiceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void initialise() {
        Residence residence1 = new Residence("leeuwen001", "fs161100", "l001", "Pride Rock", 4, "1995", false);
        Residence residence2 = new Residence("1", "konijnen001", "cn170999", "r001", "De Konijnenpijp", 15, "2010", false);
        Residence residence3 = new Residence("eekhoorns001", "cn170999", "s001", "De Grote Boom", 8, "2020", false);

        //delete residences from database
        try {
            restTemplate.delete("http://" + residenceServiceBaseUrl + "/verblijven/{verblijfID}", "konijnen001");
            restTemplate.delete("http://" + residenceServiceBaseUrl + "/verblijven/{verblijfID}", "leeuwen001");
            restTemplate.delete("http://" + residenceServiceBaseUrl + "/verblijven/{verblijfID}", "eekhoorns001");
            restTemplate.delete("http://" + residenceServiceBaseUrl + "/verblijven/{verblijfID}", "snake001");
        } catch (Exception ignore) {
        }

        //post the residences to the database
        restTemplate.postForObject("http://" + residenceServiceBaseUrl + "/verblijven",
                residence1, Residence.class);
        restTemplate.postForObject("http://" + residenceServiceBaseUrl + "/verblijven",
                residence2, Residence.class);
        restTemplate.postForObject("http://" + residenceServiceBaseUrl + "/verblijven",
                residence3, Residence.class);
    }

    @Test
    public void whenGetResidenceById_thenReturnResidenceJson() throws Exception {
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
        mockMvc.perform(get("/verblijven/personeel/{personeelsId}", "cn170999"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("[1].verblijfID", is("konijnen001")))
                .andExpect(jsonPath("[1].name", is("De Konijnenpijp")))
                .andExpect(jsonPath("[1].personeelID", is("cn170999")))
                .andExpect(jsonPath("[1].dierID", is("r001")))
                .andExpect(jsonPath("[1].maxDieren", is(15)))
                .andExpect(jsonPath("[1].bouwJaar", is("2010")))
                .andExpect(jsonPath("[1].nocturnal", is(false)))

                .andExpect(jsonPath("[0].verblijfID", is("eekhoorns001")))
                .andExpect(jsonPath("[0].name", is("De Grote Boom")))
                .andExpect(jsonPath("[0].personeelID", is("cn170999")))
                .andExpect(jsonPath("[0].dierID", is("s001")))
                .andExpect(jsonPath("[0].maxDieren", is(8)))
                .andExpect(jsonPath("[0].bouwJaar", is("2020")))
                .andExpect(jsonPath("[0].nocturnal", is(false)));
    }

    @Test
    public void whenGetResidenceByAnimalId_thenReturnResidenceJson() throws Exception {
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
        Residence residence = new Residence("snake001", "fs161100", "snake001", "Quetzalquatl's cave AKA Big ol' Snek cave", 4, "2020", true);

        mockMvc.perform(post("/verblijven")
                .content(mapper.writeValueAsString(residence))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("snake001")))
                .andExpect(jsonPath("$.name", is("Quetzalquatl's cave AKA Big ol' Snek cave")))
                .andExpect(jsonPath("$.personeelID", is("fs161100")))
                .andExpect(jsonPath("$.dierID", is("snake001")))
                .andExpect(jsonPath("$.maxDieren", is(4)))
                .andExpect(jsonPath("$.bouwJaar", is("2020")))
                .andExpect(jsonPath("$.nocturnal", is(true)));
    }

    @Test
    public void whenUpdateResidence_thenReturnResidenceJson() throws Exception {
        Residence updatedResidence = new Residence("leeuwen001", "fs161100", "l001", "Pride Rock", 5, "1995", false);

        mockMvc.perform(put("/verblijven")
                .content(mapper.writeValueAsString(updatedResidence))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verblijfID", is("leeuwen001")))
                .andExpect(jsonPath("$.name", is("Pride Rock")))
                .andExpect(jsonPath("$.personeelID", is("fs161100")))
                .andExpect(jsonPath("$.dierID", is("l001")))
                .andExpect(jsonPath("$.maxDieren", is(5)))
                .andExpect(jsonPath("$.bouwJaar", is("1995")))
                .andExpect(jsonPath("$.nocturnal", is(false)));
    }

    @Test
    public void whenDeleteResidence_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/verblijven/{verblijfID}", "leeuwen001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteAnimal_thenStatusInternalServerError() throws Exception {
        mockMvc.perform(delete("/verblijven/{verblijfID}", "leeuwen002")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
