package com.magdy.challlenge.fivvy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import com.magdy.challlenge.fivvy.repositories.DisclaimerRepository;
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("The field : 'name' The name must have at least 3 characters."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[1]").value("The field : 'name' The name cannot be blank."));

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("The field : 'text' The text cannot be blank."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[1]").value("The field : 'text' The text must have at least 3 characters."));

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("The field : 'text' The text cannot be blank."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[1]").value("The field : 'name' The text cannot be blank."));

        List<Disclaimer> disclaimers = disclaimerRepository.findAll();
        assertEquals(0 ,disclaimers.size());
    }
}