package com.soltec.web.controller;

import com.soltec.web.entity.WebKitchenOrder;
import com.soltec.web.repository.WebKitchenOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/kitchen")
public class KitchenController {

    @Autowired
    private WebKitchenOrderRepository kitchenOrderRepository;

    @GetMapping("/pending")
    public List<WebKitchenOrder> getPendingOrders() {
        return kitchenOrderRepository.findByStatusOrderByCreatedAtAsc("PENDING");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable String id) {
        Optional<WebKitchenOrder> orderOpt = kitchenOrderRepository.findById(id);
        if (orderOpt.isPresent()) {
            WebKitchenOrder order = orderOpt.get();
            order.setStatus("COMPLETED");
            kitchenOrderRepository.save(order);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        }
        return ResponseEntity.notFound().build();
    }
}
