package com.darknash.tickets.services;

import com.darknash.tickets.dtos.events.CreateEventRequest;
import com.darknash.tickets.dtos.events.EventResponse;
import com.darknash.tickets.dtos.events.ListPublishedEventResponse;
import com.darknash.tickets.dtos.events.UpdateEventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    EventResponse createEvent(UUID organizerId, CreateEventRequest eventRequest);
    Page<EventResponse> listEventsForOrganizer(UUID organizerId, Pageable pageable);
    Optional<EventResponse> getEventForOrganizer(UUID organizerId, UUID eventId);
    EventResponse updateEvent(UUID organizerId, UUID eventId, UpdateEventRequest eventRequest);
    void deleteEvent(UUID organizerId, UUID eventId);
    Page<ListPublishedEventResponse> listPublishedEvents(Pageable pageable);
    Page<ListPublishedEventResponse> searchPublishedEvents(String query, Pageable pageable);
    Optional<ListPublishedEventResponse> getPublishEvent(UUID id);

}
