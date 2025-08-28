package com.darknash.tickets.dtos.events;

import com.darknash.tickets.constants.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEventDetailsResponse {

    private UUID id;

    private String name;

    private LocalDateTime start;

    private LocalDateTime end;

    private String venue;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    private EventStatusEnum status;

    private List<GetEventDetailsTicketTypesResponse> ticketTypes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
