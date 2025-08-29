package com.darknash.tickets.services;

import com.darknash.tickets.constants.ApiPaths;
import com.darknash.tickets.constants.EventStatusEnum;
import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.repositories.EventRepository;
import com.darknash.tickets.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class EventControllerIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();
        UUID organizerId = UUID.randomUUID();

    }

    @Test
    void createEvent() throws Exception {
        CreateEventRequest request = new CreateEventRequest();
        request.setName("Test Event");
        request.setStartTime(LocalDateTime.now().minusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(1));
        request.setVenue("Test Venue");
        request.setStatus(EventStatusEnum.PUBLISHED);


        mockMvc.perform(MockMvcRequestBuilders.post(ApiPaths.EVENT)
                        .header("Authorization", "Bearer dummy-jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.code").value(200),
                        jsonPath("$.data.name").value("Test Event"),
                        jsonPath("$.data.venue").value("Test Venue")
                );



    }

    @Test
    void listEventsForOrganizer() {
    }

    @Test
    void getEventForOrganizer() {
    }

    @Test
    void updateEvent() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void listPublishedEvents() {
    }

    @Test
    void searchPublishedEvents() {
    }

    @Test
    void getPublishEvent() {
    }
}