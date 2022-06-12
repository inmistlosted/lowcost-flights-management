package com.lowcost.repositories;

import com.lowcost.entities.Seat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SeatRepository extends CrudRepository<Seat, Integer> {

    @Modifying
    @Transactional
    @Query("update Seat s set s.free=:free where s.seat_id=:id")
    void updateFreeStatus(@Param("free") boolean free, @Param("id") Integer id);
}
