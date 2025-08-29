package com.darknash.tickets.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListPublishedEventResponse {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
}
