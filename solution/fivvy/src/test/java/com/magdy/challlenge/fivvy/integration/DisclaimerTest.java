package com.magdy.challlenge.fivvy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import com.magdy.challlenge.fivvy.repositories.DisclaimerRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class DisclaimerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DisclaimerRepository disclaimerRepository;

    @BeforeEach
    public void cleanup() {
        disclaimerRepository.deleteAll();
    }

    @Test
    public void testCreateDisclaimer_and_returnStatusCreated() throws Exception {

        DisclaimerRequestDTO disclaimer = new DisclaimerRequestDTO();
        disclaimer.setName("Name Disclaimerer");
        disclaimer.setText("text Disclaimer");
        disclaimer.setVersion(2);

        String jsonDisclaimer = objectMapper.writeValueAsString(disclaimer);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDisclaimer));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(disclaimer.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(disclaimer.getText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(disclaimer.getVersion()));

        List<Disclaimer> disclaimers = disclaimerRepository.findAll();
        assertEquals(1 ,disclaimers.size());
        assertEquals(disclaimer.getName(), disclaimers.get(0).getName());
        assertEquals(disclaimer.getText(), disclaimers.get(0).getText());
        assertEquals(disclaimer.getVersion(), disclaimers.get(0).getVersion());
        assertNotNull(disclaimers.get(0).getCreateAt());
        assertNotNull(disclaimers.get(0).getUpdateAt());
    }

    @Test
    public void testCreateDisclaimer_NameEmpty_And_ReturnErrorWithBodyErrors() throws Exception {

        DisclaimerRequestDTO disclaimer = new DisclaimerRequestDTO();
        disclaimer.setName("");
        disclaimer.setText("text Disclaimer");
        disclaimer.setVersion(2);


        String jsonDisclaimer = objectMapper.writeValueAsString(disclaimer);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDisclaimer));

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(2)));


        List<Disclaimer> disclaimers = disclaimerRepository.findAll();
        assertEquals(0 ,disclaimers.size());
    }

    @Test
    public void testCreateDisclaimer_TextEmpty_And_ReturnErrorWithBodyErrors() throws Exception {

        DisclaimerRequestDTO disclaimer = new DisclaimerRequestDTO();
        disclaimer.setName("Name Disclaimerer");
        disclaimer.setText("");
        disclaimer.setVersion(2);


        String jsonDisclaimer = objectMapper.writeValueAsString(disclaimer);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDisclaimer));

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(2)));

        List<Disclaimer> disclaimers = disclaimerRepository.findAll();
        assertEquals(0 ,disclaimers.size());
    }

    @Test
    public void testCreateDisclaimer_BodyNull_And_ReturnErrorWithBodyErrors() throws Exception {

        DisclaimerRequestDTO disclaimer = new DisclaimerRequestDTO();

        String jsonDisclaimer = objectMapper.writeValueAsString(disclaimer);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDisclaimer));

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(2)));


        List<Disclaimer> disclaimers = disclaimerRepository.findAll();
        assertEquals(0 ,disclaimers.size());
    }

    @Test
    public void
    testListByText_ResponseOK() throws Exception {
        createMockRepository();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/disclaimer/search")
                .param("searchText","text Disclaimer")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("31b502e8-2843-11ee-be56-0242ac120002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Name Disclaimerer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("text Disclaimer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value("2.0"));
    }

    @Test
    public void testListByTextEmpty_ResponseOK() throws Exception {
        createMockRepository();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/disclaimer/search")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("31b502e8-2843-11ee-be56-0242ac120002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Name Disclaimerer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("text Disclaimer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value("2.0"));
    }

    @Test
    public void testGetBy_ResponseOK() throws Exception {
        createMockRepository();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/disclaimer")
                        .param("id","31b502e8-2843-11ee-be56-0242ac120002")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("31b502e8-2843-11ee-be56-0242ac120002"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Name Disclaimerer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("text Disclaimer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value("2.0"));
    }

    @Test
    public void testGetBy_ResponseNotFound() throws Exception {
        createMockRepository();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/disclaimer")
                .param("id","31b502e8-2843-11ee-be56-0242ac120001")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Disclaimer not found"));

    }



    private void createMockRepository() {
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setId(UUID.fromString("31b502e8-2843-11ee-be56-0242ac120002").toString());
        disclaimer.setName("Name Disclaimerer");
        disclaimer.setText("text Disclaimer");
        disclaimer.setVersion(2.0);

        disclaimerRepository.save(disclaimer);

        Disclaimer disclaimer1 = new Disclaimer();
        disclaimer1.setId(UUID.fromString("51f43a1e-2844-11ee-be56-0242ac120002").toString());
        disclaimer1.setName("Name Disclaimerer 1");
        disclaimer1.setText("text");
        disclaimer1.setVersion(2.1);
        disclaimerRepository.save(disclaimer1);
    }

}