package com.soltec.web.repository;

import com.soltec.web.entity.TicketLine;
import com.soltec.web.entity.TicketLineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketLineRepository extends JpaRepository<TicketLine, TicketLineId> {

    List<TicketLine> findByTicket(String ticket);

    @Query("SELECT tl FROM TicketLine tl WHERE tl.ticket IN :ticketIds")
    List<TicketLine> findByTicketIn(@Param("ticketIds") List<String> ticketIds);

    @Query("SELECT tl.productId, SUM(tl.units) as totalUnits, SUM(tl.units * tl.price) as totalSales " +
           "FROM TicketLine tl WHERE tl.ticket IN :ticketIds " +
           "GROUP BY tl.productId ORDER BY totalSales DESC")
    List<Object[]> findTopProductsByTickets(@Param("ticketIds") List<String> ticketIds);
}
