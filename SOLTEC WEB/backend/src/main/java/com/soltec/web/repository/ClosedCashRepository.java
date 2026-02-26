package com.soltec.web.repository;

import com.soltec.web.entity.ClosedCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClosedCashRepository extends JpaRepository<ClosedCash, String> {

    @Query("SELECT c FROM ClosedCash c WHERE c.dateend IS NULL ORDER BY c.datestart DESC")
    List<ClosedCash> findOpenCashSessions();

    @Query("SELECT c FROM ClosedCash c ORDER BY c.datestart DESC")
    List<ClosedCash> findAllOrderByDateDesc();
}
