package com.darknash.tickets.dtos.events;

import com.darknash.tickets.constants.EventStatusEnum;
import com.darknash.tickets.dtos.tickets.TicketTypeResponse;
import com.darknash.tickets.dtos.users.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponse {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private UserResponse user;
    private List<UserResponse> staffs;
    private List<UserResponse> attendees;
    private List<TicketTypeResponse> ticketType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
