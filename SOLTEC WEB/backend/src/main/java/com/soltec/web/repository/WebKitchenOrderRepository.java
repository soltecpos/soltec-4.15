package com.soltec.web.repository;

import com.soltec.web.entity.WebKitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebKitchenOrderRepository extends JpaRepository<WebKitchenOrder, String> {
    List<WebKitchenOrder> findByStatusOrderByCreatedAtAsc(String status);
    List<WebKitchenOrder> findByTicketId(String ticketId);
}
