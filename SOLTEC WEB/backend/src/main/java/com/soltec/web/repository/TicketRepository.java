package com.soltec.web.repository;

import com.soltec.web.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, String> {

    @Query("SELECT t FROM TicketEntity t JOIN t.receipt r WHERE r.datenew >= :startDate AND r.datenew <= :endDate ORDER BY r.datenew DESC")
    List<TicketEntity> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT t FROM TicketEntity t WHERE t.tickettype = :type")
    List<TicketEntity> findByTickettype(@Param("type") int type);

    @Query("SELECT COUNT(t) FROM TicketEntity t WHERE t.id IN :receiptIds AND t.tickettype = :type")
    long countByReceiptIdInAndTickettype(@Param("receiptIds") List<String> receiptIds, @Param("type") int type);
}
