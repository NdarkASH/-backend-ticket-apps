package com.darknash.tickets.controllers;

import com.darknash.tickets.constants.ApiPaths;
import com.darknash.tickets.dtos.AppResponse;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.dtos.events.ListPublishedEventResponse;
import com.darknash.tickets.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Published Events", description = "Api untuk event yang sudah di publikasikan")
@SecurityRequirement(name = "basic")
public class PublishedEventController {

    private final EventService eventService;


    @GetMapping
    @Operation(
            summary = "List Published Events",
            description = "Ambil semua event yang sudah dipublish. Bisa pakai query param `param` untuk search."
    )
    public AppResponse<Page<ListPublishedEventResponse>> listPublishedEvents(
            @RequestParam(required = false) String param,
            Pageable pageable
    ) {
        Page<ListPublishedEventResponse> responses = Optional.ofNullable(param)
                .filter(StringUtils::hasText)
                .map(p -> eventService.searchPublishedEvents(param, pageable))
                .orElseGet(()-> eventService.listPublishedEvents(pageable));

        return AppResponse.<Page<ListPublishedEventResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(responses)
                .build();
    }

    @GetMapping(path = "/{eventId}")
    @Operation(
            summary = "Get Published Event Detail",
            description = "Ambil detail event yang sudah dipublish berdasarkan `eventId`"
    )
    public AppResponse<Optional<ListPublishedEventResponse>> getPublishedEventDetails(@PathVariable UUID eventId) {
        Optional<ListPublishedEventResponse> response = eventService.getPublishEvent(eventId);

        return AppResponse.<Optional<ListPublishedEventResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(response)
                .build();
    }
}
