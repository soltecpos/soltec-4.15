package com.soltec.web.repository;

import com.soltec.web.entity.StockCurrent;
import com.soltec.web.entity.StockCurrentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockCurrentRepository extends JpaRepository<StockCurrent, StockCurrentId> {

    @Query("SELECT sc FROM StockCurrent sc JOIN FETCH sc.product p ORDER BY p.name")
    List<StockCurrent> findAllWithProduct();

    List<StockCurrent> findByProductId(String productId);
}
