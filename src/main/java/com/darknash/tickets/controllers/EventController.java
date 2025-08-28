package com.darknash.tickets.controllers;

import com.darknash.tickets.constants.ApiPaths;
import com.darknash.tickets.dtos.AppResponse;
import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.CreateEventResponse;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
