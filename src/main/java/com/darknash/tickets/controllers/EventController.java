package com.darknash.tickets.controllers;

import com.darknash.tickets.constants.ApiPaths;
import com.darknash.tickets.dtos.AppResponse;
import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.CreateEventResponse;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.dtos.events.UpdateEventRequest;
import com.darknash.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.EVENT)
public class EventController {
    private final EventService eventService;

    @PostMapping
    public AppResponse<EventResponse> createEvent(
            @AuthenticationPrincipal Jwt user,
            @Valid @RequestBody CreateEventRequest request) {
        UUID userId = UUID.fromString(user.getSubject());
        EventResponse createEventRequest = eventService.createEvent(userId, request);

        return AppResponse.<EventResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(createEventRequest)
                .build();
    }

    @GetMapping
    public AppResponse<Page<EventResponse>> getEvent(
            @AuthenticationPrincipal Jwt user,
            Pageable pageable
    )  {
        UUID userId = UUID.fromString(user.getSubject());
        Page<EventResponse> eventResponses = eventService.listEventsForOrganizer(userId, pageable);

        return AppResponse.<Page<EventResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(eventResponses)
                .build();
    }

    @GetMapping(path = "/{eventId}")
    public AppResponse<Optional<EventResponse>> getEventForOrganizer(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Optional<EventResponse> response = eventService.getEventForOrganizer(userId, eventId);

        return AppResponse.<Optional<EventResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }

    @PutMapping(path = "/{eventId}")
    public AppResponse<EventResponse> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @RequestBody UpdateEventRequest request) {
        UUID userId = UUID.fromString(jwt.getSubject());
        EventResponse updateEventRequest = eventService.updateEvent(userId, eventId, request);

        return AppResponse.<EventResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(updateEventRequest)
                .build();
    }


    @DeleteMapping(path = "/{eventId}")
    public AppResponse<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId) {
        UUID userId = UUID.fromString(jwt.getSubject());
        eventService.deleteEvent(userId, eventId);

        return AppResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }
}
