package com.soltec.web.repository;

import com.soltec.web.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByReceiptId(String receiptId);

    @Query("SELECT p FROM Payment p WHERE p.receiptId IN :receiptIds")
    List<Payment> findByReceiptIdIn(@Param("receiptIds") List<String> receiptIds);

    @Query("SELECT p.payment, SUM(p.total), COUNT(p) FROM Payment p " +
           "JOIN TicketEntity t ON p.receiptId = t.id " +
           "WHERE p.receiptId IN :receiptIds AND t.tickettype = 0 " +
           "GROUP BY p.payment")
    List<Object[]> summarizeByPaymentType(@Param("receiptIds") List<String> receiptIds);
}
