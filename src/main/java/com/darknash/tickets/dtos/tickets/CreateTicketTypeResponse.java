package com.darknash.tickets.dtos.tickets;


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
public class CreateTicketTypeResponse {

    private UUID id;

    private String name;

    private String description;

    private Double price;

    private Integer totalAvailable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
