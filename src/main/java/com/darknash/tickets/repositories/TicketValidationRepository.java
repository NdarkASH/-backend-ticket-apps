package com.darknash.tickets.repositories;

import com.darknash.tickets.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {

}
