package com.darknash.tickets.repositories;

import com.darknash.tickets.entities.Payment;
import com.darknash.tickets.entities.QrisPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface QrisRepository extends JpaRepository<QrisPayment, UUID> {
    @Query("SELECT p FROM Payment p LEFT JOIN FETCH p.qrisPayment q WHERE p.id = :id")
    Optional<Payment> findByIdWithQris(@Param("id") UUID id);
}
