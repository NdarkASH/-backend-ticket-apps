package com.darknash.tickets.repositories;

import com.darknash.tickets.constants.QrCodeStatusEnum;
import com.darknash.tickets.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QrcodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId_(UUID ticketId,UUID ticketPurchaserId);
    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum qrCode);
}
