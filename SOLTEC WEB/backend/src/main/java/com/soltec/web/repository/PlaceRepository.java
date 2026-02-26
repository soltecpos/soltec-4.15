package com.soltec.web.repository;

import com.soltec.web.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {
    List<Place> findByFloorId(String floorId);
    List<Place> findAllByOrderByNameAsc();
}
