package com.darknash.tickets.controllersTest;

import com.darknash.tickets.controllers.EventController;
import com.darknash.tickets.dtos.AppResponse;
import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.dtos.events.UpdateEventRequest;
import com.darknash.tickets.entities.Event;
import com.darknash.tickets.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {
//Create event testing
    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    private Jwt buildJwt(UUID userId) {
       return Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject(userId.toString())
                .build();
    }

    @Test
    void testCreateEvent() {
        UUID userId = UUID.randomUUID();
        CreateEventRequest req = new CreateEventRequest();
        req.setName("Test Event");

        EventResponse mockResponse = EventResponse.builder()
                .id(UUID.randomUUID())
                .name("Test Event")
                .build();

        Mockito.when(eventService.createEvent(Mockito.eq(userId), Mockito.any()))
                .thenReturn(mockResponse);

        Jwt jwt = buildJwt(userId);
        AppResponse<EventResponse> result = eventController.createEvent(jwt, req);

        assertEquals(200, result.getCode());
        assertEquals("none", jwt.getHeaders().get("alg"));
        assertEquals("Test Event", result.getData().getName());

        Mockito.verify(eventService).createEvent(Mockito.eq(userId), Mockito.any(CreateEventRequest.class));

    }

    @Test
    void testGetEvents() {
        UUID userId = UUID.randomUUID();
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(UUID.randomUUID());
        eventResponse.setName("Test Event");

        Page<EventResponse> mockPage = new PageImpl<>(List.of(eventResponse));

       Mockito.when(eventService.listEventsForOrganizer(Mockito.eq(userId), Mockito.any()))
               .thenReturn(mockPage);

        Jwt jwt = buildJwt(userId);

        AppResponse<Page<EventResponse>> result = eventController.getEvent(jwt, Pageable.unpaged());
        assertEquals(200, result.getCode());
        assertEquals("Test Event", result.getData().getContent().getFirst().getName());

        assertEquals(mockPage, result.getData());
        assertEquals(1, mockPage.getTotalElements());
        assertEquals(1, mockPage.getTotalPages());

        Mockito.verify(eventService).listEventsForOrganizer(Mockito.eq(userId), Mockito.any());
    }

    @Test
    void testUpdateEvent() {
        UUID userId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        UpdateEventRequest req = new UpdateEventRequest();
        req.setId(eventId);
        req.setName("Test Event");

        EventResponse mockResponse = EventResponse.builder()
                .id(eventId)
                .name("Test Event")
                .build();

        Mockito.when(eventService.updateEvent(userId, eventId, req))
                .thenReturn(mockResponse);

        Jwt jwt = buildJwt(userId);

        AppResponse<EventResponse> result = eventController.updateEvent(jwt, eventId, req);
        assertEquals(200, result.getCode());
        assertEquals("Test Event", result.getData().getName());
        Mockito.verify(eventService).updateEvent(userId, eventId, req);

    }
    @Test
    void testDeleteEvent() {
        UUID userId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        Mockito.doNothing().when(eventService).deleteEvent(userId, eventId);
        Jwt jwt = buildJwt(userId);
        AppResponse<Void> result = eventController.deleteEvent(jwt, eventId);

        assertEquals(200, result.getCode());
        assertEquals("none", jwt.getHeaders().get("alg"));
        Mockito.verify(eventService).deleteEvent(userId, eventId);
    }
}