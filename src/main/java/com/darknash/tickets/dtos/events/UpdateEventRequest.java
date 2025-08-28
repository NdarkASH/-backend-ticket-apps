package com.darknash.tickets.dtos.events;

import com.darknash.tickets.constants.EventStatusEnum;
import com.darknash.tickets.dtos.tickets.CreateTicketTypeRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private UUID id;
    @NotBlank(message = "Event name is required")
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotBlank(message = "Venue information is required")
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    @NotNull
    private EventStatusEnum status;
    @Builder.Default
    private List<CreateTicketTypeRequest> ticketTypeRequests = new ArrayList<>();
}
