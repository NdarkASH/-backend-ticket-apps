package com.darknash.tickets.controllers;

import com.darknash.tickets.constants.ApiPaths;
import com.darknash.tickets.dtos.AppResponse;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = ApiPaths.PUBLISHEDEVENT)
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;


    @GetMapping
    public AppResponse<Page<EventResponse>> listPublishedEvents(
            @RequestParam(required = false) String param,
            Pageable pageable
    ) {
        Page<EventResponse> responses = Optional.ofNullable(param)
                .filter(StringUtils::hasText)
                .map(p -> eventService.searchPublishedEvents(param, pageable))
                .orElseGet(()-> eventService.listPublishedEvents(pageable));

        return AppResponse.<Page<EventResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(responses)
                .build();
    }

    @GetMapping(path = "/{eventId}")
    public AppResponse<Optional<EventResponse>> getPublishedEventDetails(@PathVariable UUID eventId) {
        Optional<EventResponse> response = eventService.getPublishEvent(eventId);

        return AppResponse.<Optional<EventResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }
}
