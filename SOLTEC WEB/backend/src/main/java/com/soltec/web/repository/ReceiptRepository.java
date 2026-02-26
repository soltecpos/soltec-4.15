package com.soltec.web.repository;

import com.soltec.web.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, String> {

    @Query("SELECT r FROM Receipt r WHERE r.datenew >= :startDate AND r.datenew <= :endDate ORDER BY r.datenew DESC")
    List<Receipt> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT r FROM Receipt r WHERE r.datenew >= :startDate ORDER BY r.datenew DESC")
    List<Receipt> findByDateAfter(@Param("startDate") Date startDate);

    List<Receipt> findByMoneyIn(List<String> moneyIds);

    @Query(value = "SELECT r.* FROM receipts r JOIN closedcash c ON r.money = c.money WHERE c.dateend IS NULL ORDER BY r.datenew DESC", nativeQuery = true)
    List<Receipt> findByActiveShift();
}
