package com.dezoo.edge;

import com.dezoo.edge.models.PersonnelMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonnelIntegrationTests {

    @Autowired
    private RestTemplate restTemplate;

    //get the base urls from the application.properties file
    @Value("${personnelService.baseurl}")
    private String personnelServiceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetPersonnelMembers_thenReturnPersonnelMembersJson() throws Exception {
        //the hasSize needs to be 4 because the post test is run before this test.
        mockMvc.perform(get("/personeel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("[0].personnelId", is("fs161100")))
                .andExpect(jsonPath("[0].firstName", is("Ferre")))
                .andExpect(jsonPath("[0].lastName", is("Snyers")))
                .andExpect(jsonPath("[0].dateOfBirth", is("2000-11-15T23:00:00.000+00:00")))
                .andExpect(jsonPath("[0].address", is("Gestelstraat 21")))
                .andExpect(jsonPath("[0].postalCode", is("2250")))
                .andExpect(jsonPath("[0].privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("[0].personelCategory", is("Administration")))

                .andExpect(jsonPath("[1].personnelId", is("cn170999")))
                .andExpect(jsonPath("[1].firstName", is("Christophe")))
                .andExpect(jsonPath("[1].lastName", is("Neefs")))
                .andExpect(jsonPath("[1].dateOfBirth", is("1999-09-16T22:00:00.000+00:00")))
                .andExpect(jsonPath("[1].address", is("Lier")))
                .andExpect(jsonPath("[1].postalCode", is("2500")))
                .andExpect(jsonPath("[1].privatePhoneNumber", is("")))
                .andExpect(jsonPath("[1].personelCategory", is("Rabbits")))

                .andExpect(jsonPath("[2].personnelId", is("rh031000")))
                .andExpect(jsonPath("[2].firstName", is("Robbe")))
                .andExpect(jsonPath("[2].lastName", is("Heremans")))
                .andExpect(jsonPath("[2].dateOfBirth", is("2000-10-02T22:00:00.000+00:00")))
                .andExpect(jsonPath("[2].address", is("Westerlo")))
                .andExpect(jsonPath("[2].postalCode", is("2260")))
                .andExpect(jsonPath("[2].privatePhoneNumber", is("")))
                .andExpect(jsonPath("[2].personelCategory", is("Lions")));
    }

    @Test
    public void whenGetPersonnelMemberByPersonnelId_thenReturnPeronnelMemberJson() throws Exception {
        mockMvc.perform(get("/personeel/{personnelId}", "cn170999"))
                .andExpect(jsonPath("$.personnelId", is("cn170999")))
                .andExpect(jsonPath("$.firstName", is("Christophe")))
                .andExpect(jsonPath("$.lastName", is("Neefs")))
                .andExpect(jsonPath("$.dateOfBirth", is("1999-09-16T22:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Lier")))
                .andExpect(jsonPath("$.postalCode", is("2500")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("")))
                .andExpect(jsonPath("$.personelCategory", is("Rabbits")));
    }

    @Test
    public void whenAddPersonnelMember_thenReturnPersonnelMemberJson() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember personnelMember = new PersonnelMember("dj151099", "Dirk", "Janssens", format.parse("15/10/1999"), "Gladiolenstraat 2", "2250", "", "Administration");
        mockMvc.perform(post("/personeel")
                .content(mapper.writeValueAsString(personnelMember))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("dj151099")))
                .andExpect(jsonPath("$.firstName", is("Dirk")))
                .andExpect(jsonPath("$.lastName", is("Janssens")))
                .andExpect(jsonPath("$.dateOfBirth", is("1999-10-14T22:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gladiolenstraat 2")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void whenUpdatePersonnelMember_thenReturnPersonnelMemberJson() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PersonnelMember updatedPersonnelMember = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 22", "2250", "+32441439", "Administration");

        mockMvc.perform(put("/personeel")
                .content(mapper.writeValueAsString(updatedPersonnelMember))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("fs161100")))
                .andExpect(jsonPath("$.firstName", is("Ferre")))
                .andExpect(jsonPath("$.lastName", is("Snyers")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-11-15T23:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gestelstraat 22")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void whenDeletePersonnelMember_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/personeel/{personnelID}", "dj151099")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void whenDeletePersonnelMember_thenStatusInternalServerError() throws Exception {
        mockMvc.perform(delete("/personeel/{personnelID}", "df151099")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

    }
}
