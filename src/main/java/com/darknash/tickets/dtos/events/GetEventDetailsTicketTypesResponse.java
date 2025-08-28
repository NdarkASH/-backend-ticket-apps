package com.darknash.tickets.dtos.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetEventDetailsTicketTypesResponse {

    private UUID id;

    private String name;

    private Double price;

    private String description;

    private Integer totalAvailable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
