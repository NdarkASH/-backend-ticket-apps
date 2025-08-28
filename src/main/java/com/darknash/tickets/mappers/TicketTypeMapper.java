package com.darknash.tickets.mappers;

import com.darknash.tickets.dtos.tickets.TicketTypeResponse;
import com.darknash.tickets.entities.TicketType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TicketTypeMapper {
        public TicketTypeResponse toResponse(TicketType ticketType) {
            return TicketTypeResponse.builder()
                    .id(ticketType.getId())
                    .name(ticketType.getName())
                    .price(ticketType.getPrice())
                    .description(ticketType.getDescription())
                    .totalAvailable(ticketType.getTotalAvaible())
                    .build();
        }

}
