package com.darknash.tickets.repositories;

import com.darknash.tickets.constants.EventStatusEnum;
import com.darknash.tickets.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

    Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);

    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);

    @Query(
            value = "SELECT e.* FROM events e " +
                    "WHERE e.status = 'PUBLISHED' " +
                    "AND e.search_tsv @@ websearch_to_tsquery('simple', :searchTerm) " +
                    "ORDER BY ts_rank(e.search_tsv, websearch_to_tsquery('simple', :searchTerm)) DESC",
            countQuery = "SELECT count(*) FROM events e " +
                    "WHERE e.status = 'PUBLISHED'" +
                    "AND e.search_tsv @@ websearch_to_tsquery('simple', :searchTerm)",
            nativeQuery = true
    )
    Page<Event> searchEvent(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Event> findByIdAndStatus(UUID id, EventStatusEnum status);

    @EntityGraph(attributePaths = {
            "organizer",
            "ticketTypes",
            "ticketTypes.tickets",
            "attendees",
            "staff"
    })
    Optional<Event> findDetailById(UUID id);

    @EntityGraph(attributePaths = {
            "organizer",
            "ticketTypes"
    })
    Page<Event> findDetailByStatus(EventStatusEnum status, Pageable pageable);
}
