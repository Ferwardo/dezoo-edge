package com.dezoo.edge.personnelTests;

import com.dezoo.edge.models.PersonnelMember;
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
import java.text.ParseException;
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
public class PersonnelUnitTests {

    //inject a restTemplate
    @Autowired
    private RestTemplate restTemplate;

    //get the base urls from the application.properties file
    @Value("${personnelService.baseurl}")
    private String personnelServiceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private PersonnelMember personnelMember1;
    private PersonnelMember personnelMember2;
    private PersonnelMember personnelMember3;
    private List<PersonnelMember> personnelMemberList;

    @BeforeEach
    public void initialize() throws ParseException {
        personnelMember1 = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 21", "2250", "+32441439", "Administration");
        personnelMember2 = new PersonnelMember("cn170999", "Christophe", "Neefs", format.parse("17/09/1999"), "Lier", "2500", "", "Rabbits");
        personnelMember3 = new PersonnelMember("rh031000", "Robbe", "Heremans", format.parse("03/10/2000"), "Westerlo", "2260", "", "Lions");

        personnelMemberList = new ArrayList<PersonnelMember>();
        personnelMemberList.add(personnelMember1);
        personnelMemberList.add(personnelMember2);
        personnelMemberList.add(personnelMember3);

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetPersonnelMembers_thenReturnPersonnelMembersJson() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + personnelServiceBaseUrl + "/personnel")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(personnelMemberList))
                );

        mockMvc.perform(get("/personeel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
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
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + personnelServiceBaseUrl + "/personnel/cn170999")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(personnelMember2))
                );

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
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + personnelServiceBaseUrl + "/personnel")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(personnelMember1))
                );

        mockMvc.perform(post("/personeel")
                .content(mapper.writeValueAsString(personnelMember1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personnelId", is("fs161100")))
                .andExpect(jsonPath("$.firstName", is("Ferre")))
                .andExpect(jsonPath("$.lastName", is("Snyers")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-11-15T23:00:00.000+00:00")))
                .andExpect(jsonPath("$.address", is("Gestelstraat 21")))
                .andExpect(jsonPath("$.postalCode", is("2250")))
                .andExpect(jsonPath("$.privatePhoneNumber", is("+32441439")))
                .andExpect(jsonPath("$.personelCategory", is("Administration")));
    }

    @Test
    public void whenUpdatePersonnelMember_thenReturnPersonnelMemberJson() throws Exception {
        PersonnelMember updatedPersonnelMember = new PersonnelMember("fs161100", "Ferre", "Snyers", format.parse("16/11/2000"), "Gestelstraat 22", "2250", "+32441439", "Administration");

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + personnelServiceBaseUrl + "/personnel")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedPersonnelMember))
                );

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
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + personnelServiceBaseUrl + "/personnel/fs161100")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));

        mockMvc.perform(delete("/personeel/{personnelID}", "fs161100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeletePersonnelMember_thenStatusInternalServerError() throws Exception {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + personnelServiceBaseUrl + "/personnel/df151099")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(delete("/personeel/{personnelID}", "df151099")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

    }
}
