package controllersTest;

import com.darknash.tickets.controllers.EventController;
import com.darknash.tickets.dtos.AppResponse;
import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.services.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.oauth2.jwt.Jwt;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Test
    void testCreateEvent() {
        // arrange
        UUID userId = UUID.randomUUID();
        CreateEventRequest req = new CreateEventRequest();
        req.setName("Test Event");

        EventResponse mockResponse = EventResponse.builder()
                .id(UUID.randomUUID())
                .name("Test Event")
                .build();

        Mockito.when(eventService.createEvent(Mockito.eq(userId), Mockito.any()))
                .thenReturn(mockResponse);

        // act
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject(userId.toString())
                .build();

        AppResponse<EventResponse> result = eventController.createEvent(jwt, req);

        // assert
        assertEquals(200, result.getCode());
        assertEquals(jwt.getHeaders().get("alg"), "none");
        assertEquals("Test Event", result.getData().getName());
    }
}