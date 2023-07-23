package com.magdy.challlenge.fivvy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceRequestDTO;
import com.magdy.challlenge.fivvy.models.entities.Acceptance;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import com.magdy.challlenge.fivvy.repositories.AcceptanceRepository;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AcceptanceRepository acceptanceRepository;

    @Autowired
    private DisclaimerRepository disclaimerRepository;

    @BeforeEach
    public void cleanup() {
        acceptanceRepository.deleteAll();
    }

    @Test
    public void testAddAcceptanceById_ResponseAccept() throws Exception {
        createMockAcceptance();
        AcceptanceRequestDTO acceptanceRequestDTO = new AcceptanceRequestDTO();
        acceptanceRequestDTO.setUserId("b001a5b2-28dd-11ee-be56-0242ac120002");
        String jsonAcceptance = objectMapper.writeValueAsString(acceptanceRequestDTO);

        String disclaimerId = "31b502e8-2843-11ee-be56-0242ac120002";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer/{disclaimerId}/acceptance", disclaimerId)
                .content(jsonAcceptance)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

        List<Acceptance> acceptanceFound = acceptanceRepository.findAll();
        assertFalse(acceptanceFound.isEmpty());
        assertNotNull(acceptanceFound.get(0).getId());
        assertEquals(acceptanceRequestDTO.getUserId(), acceptanceFound.get(0).getUserId());
        assertEquals(disclaimerId, acceptanceFound.get(0).getDisclaimerId());
    }

    @Test
    public void testAddAcceptanceById_ResponseNotFoundDisclaimerId() throws Exception {
        AcceptanceRequestDTO acceptanceRequestDTO = new AcceptanceRequestDTO();
        acceptanceRequestDTO.setUserId("b001a5b2-28dd-11ee-be56-0242ac120002");
        String jsonAcceptance = objectMapper.writeValueAsString(acceptanceRequestDTO);

        String disclaimerId = "31b502e8-2843-11ee-be56-0242ac120001";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer/{disclaimerId}/acceptance", disclaimerId)
                .content(jsonAcceptance)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value("Disclaimer not found"));
    }

    @Test
    public void testAddAcceptanceById_NotUserAndBadRequest() throws Exception {
        AcceptanceRequestDTO acceptanceRequestDTO = new AcceptanceRequestDTO();
        String jsonAcceptance = objectMapper.writeValueAsString(acceptanceRequestDTO);

        String disclaimerId = "31b502e8-2843-11ee-be56-0242ac120001";
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer/{disclaimerId}/acceptance", disclaimerId)
                .content(jsonAcceptance)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    public void testListAllAcceptanceByUser_ResponseOk() throws Exception {
        createMockAcceptance();

        String disclaimerId = "31b502e8-2843-11ee-be56-0242ac120002";
        String userId = "b001a5b2-28dd-11ee-be56-0242ac120002";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer/acceptance/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].disclaimerId").value(disclaimerId));
    }

    @Test
    public void testListAllAcceptanceByUserNotExist_ResponseOk() throws Exception {
        createMockAcceptance();

        String disclaimerId = "31b502e8-2843-11ee-be56-0242ac120002";
        String userId = "b001a5b2-28dd-11ee-be56-0242ac120009";

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/disclaimer/acceptance/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").doesNotExist());
    }

    private void createMockAcceptance(){

        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setId(UUID.fromString("31b502e8-2843-11ee-be56-0242ac120002").toString());
        disclaimer.setName("Name Disclaimerer");
        disclaimer.setText("text Disclaimer");
        disclaimer.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        disclaimer.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        disclaimer.setVersion(2.0);

        disclaimerRepository.save(disclaimer);

        Disclaimer disclaimer1 = new Disclaimer();
        disclaimer1.setId(UUID.fromString("51f43a1e-2844-11ee-be56-0242ac120002").toString());
        disclaimer1.setName("Name Disclaimerer 1");
        disclaimer1.setText("text");
        disclaimer1.setVersion(2.1);
        disclaimer.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        disclaimer.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        disclaimerRepository.save(disclaimer1);

        Acceptance acceptance1 = new Acceptance();
        acceptance1.setId(UUID.fromString("76c66d24-28eb-11ee-be56-0242ac120002").toString());
        acceptance1.setDisclaimerId(disclaimer.getId());
        acceptance1.setUserId("b001a5b2-28dd-11ee-be56-0242ac120002");
        acceptance1.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        acceptanceRepository.save(acceptance1);

        Acceptance acceptance2 = new Acceptance();
        acceptance2.setId(UUID.fromString("711dc372-28eb-11ee-be56-0242ac120002").toString());
        acceptance2.setDisclaimerId(disclaimer1.getId());
        acceptance2.setUserId("b001a5b2-28dd-11ee-be56-0242ac120002");
        acceptance2.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        acceptanceRepository.save(acceptance2);

        Acceptance acceptance3 = new Acceptance();
        acceptance3.setId(UUID.fromString("9dc47e85-b2ac-4fcd-afc5-d551b94822f2").toString());
        acceptance3.setDisclaimerId(disclaimer1.getId());
        acceptance3.setUserId("b001a5b2-28dd-11ee-be56-0242ac120001");
        acceptance3.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")).minusHours(1));
        acceptanceRepository.save(acceptance3);

    }

}
