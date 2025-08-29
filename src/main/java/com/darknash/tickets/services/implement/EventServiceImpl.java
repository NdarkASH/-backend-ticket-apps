package com.darknash.tickets.services.implement;

import com.darknash.tickets.constants.EventStatusEnum;
import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.dtos.events.UpdateEventRequest;
import com.darknash.tickets.dtos.tickets.UpdateTicketTypeRequest;
import com.darknash.tickets.entities.Event;
import com.darknash.tickets.entities.TicketType;
import com.darknash.tickets.entities.User;
import com.darknash.tickets.exceptions.EventNotFoundException;
import com.darknash.tickets.exceptions.TicketTypeNotFoundException;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Event existingEvent = eventRepository
                .findByIdAndOrganizerId(organizerId, eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format("Event with id %s not found", eventId)));

        existingEvent.setName(eventRequest.getName());
        existingEvent.setEventStart(eventRequest.getStartTime());
        existingEvent.setEventEnd(eventRequest.getEndTime());
        existingEvent.setVenue(eventRequest.getVenue());
        existingEvent.setSalesStart(eventRequest.getSalesStart());
        existingEvent.setSalesEnd(eventRequest.getSalesEnd());
        existingEvent.setStatus(eventRequest.getStatus());
        eventRepository.save(existingEvent);

        Set<UUID> requestTicketTypeIds = eventRequest.getTicketTypeRequests()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::isNull).collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType ->
                !requestTicketTypeIds.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypeIndex = existingEvent
                .getTicketTypes()
                .stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));



        for (UpdateTicketTypeRequest ticketTypeRequest : eventRequest.getTicketTypeRequests()) {
//            Create
            if (ticketTypeRequest.getId() == null) {
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketTypeRequest.getName());
                ticketTypeToCreate.setPrice(ticketTypeRequest.getPrice());
                ticketTypeToCreate.setDescription(ticketTypeRequest.getDescription());
                ticketTypeToCreate.setPrice(ticketTypeRequest.getPrice());
                ticketTypeToCreate.setEvent(existingEvent);
            } else if(existingTicketTypeIndex.containsKey(ticketTypeRequest.getId())) {
//                Update
                TicketType ticketTypeToUpdate = existingTicketTypeIndex.get(ticketTypeRequest.getId());
                ticketTypeToUpdate.setName(ticketTypeRequest.getName());
                ticketTypeToUpdate.setPrice(ticketTypeRequest.getPrice());
                ticketTypeToUpdate.setDescription(ticketTypeRequest.getDescription());
                ticketTypeToUpdate.setTotalAvaible(ticketTypeRequest.getTotalAvailable());
            } else {
                throw new TicketTypeNotFoundException(String
                        .format("Ticket type with id %s not found", ticketTypeRequest.getId()));
            }
        }

        Event savedEvent = eventRepository.save(existingEvent);
        return toEventResponse(savedEvent);
    }
    @Override
    @Transactional(rollbackFor = EventNotFoundException.class)
    public void deleteEvent (UUID organizerId, UUID eventId) {
        getEventForOrganizer(organizerId, eventId)
                .ifPresent(eventResponse -> eventRepository.deleteById(eventId));
    }

    @Override
    public Page<EventResponse> listPublishedEvents(Pageable pageable) {
        Page<Event> events= eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
        return events.map(this::toEventResponse);
    }

    @Override
    public Page<EventResponse> searchPublishedEvents(String query, Pageable pageable) {
        Page<Event> events = eventRepository.searchEvent(query, pageable);
        return events.map(this::toEventResponse);
    }

    @Override
    public Optional<EventResponse> getPublishEvent(UUID id) {
        Optional<Event> eventResponse = eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
        return eventResponse.map(this::toEventResponse);
    }

    private EventResponse toEventResponse(Event event) {
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
