package com.darknash.tickets.services.implement;

import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.dtos.events.UpdateEventRequest;
import com.darknash.tickets.entities.Event;
import com.darknash.tickets.entities.TicketType;
import com.darknash.tickets.entities.User;
import com.darknash.tickets.exceptions.UserNotFoundException;
import com.darknash.tickets.mappers.TicketTypeMapper;
import com.darknash.tickets.repositories.EventRepository;
import com.darknash.tickets.repositories.UserRepository;
import com.darknash.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    @Override
    public EventResponse createEvent(UUID organizerId, CreateEventRequest eventRequest) {
        User user = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Event event = new Event();
        List<TicketType> ticketTypes = eventRequest.getTicketTypeRequests()
                .stream()
                .map(createTicketTypeRequest -> {
                    TicketType ticketType = new TicketType();
                    ticketType.setName(createTicketTypeRequest.getName());
                    ticketType.setPrice(createTicketTypeRequest.getPrice());
                    ticketType.setDescription(createTicketTypeRequest.getDescription());
                    ticketType.setTotalAvaible(ticketType.getTotalAvaible());
                    return ticketType;
                }).toList();
        event.setName(eventRequest.getName());
        event.setOrganizer(user);
        event.setEventStart(event.getEventStart());
        event.setEventEnd(event.getEventEnd());
        event.setVenue(event.getVenue());
        event.setSalesStart(event.getSalesStart());
        event.setSalesEnd(event.getSalesEnd());
        event.setTicketTypes(ticketTypes);
        eventRepository.save(event);
        return toEventResponse(event);

    }

    @Override
    public Page<EventResponse> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        Page<Event> event = eventRepository.findByOrganizerId(organizerId, pageable);
        return event.map(this::toEventResponse);

    }


    @Override
    public Optional<EventResponse> getEventForOrganizer(UUID organizerId, UUID eventId) {
        Optional<Event> event = eventRepository.findByIdAndOrganizerId(organizerId, eventId);

        return event.map(this::toEventResponse);
    }

    @Override
    public EventResponse updateEvent(UUID organizerId, UUID eventId, UpdateEventRequest eventRequest) {
        return null;
    }

    @Override
    public void deleteEvent(UUID organizerId, UUID eventId) {

    }

    @Override
    public Page<EventResponse> listPublishedEvents(Pageable pageable) {
        return null;
    }

    @Override
    public Page<EventResponse> searchPublishedEvents(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<EventResponse> getPublishEvent(UUID id) {
        return Optional.empty();
    }


    private EventResponse toEventResponse (Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .venue(event.getVenue())
                .start(event.getEventStart())
                .end(event.getEventEnd())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .salesStart(event.getSalesStart())
                .salesEnd(event.getSalesEnd())
                .ticketType(event.getTicketTypes()
                        .stream()
                        .map(TicketTypeMapper::toResponse).toList())
                .build();
    }
}
