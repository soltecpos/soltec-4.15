package com.soltec.web.repository;

import com.soltec.web.entity.SharedTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SharedTicketRepository extends JpaRepository<SharedTicket, String> {
    List<SharedTicket> findByAppuser(String appuser);
    List<SharedTicket> findAllByOrderByIdAsc();
}
