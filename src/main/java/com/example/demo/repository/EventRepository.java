package com.example.demo.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
   // 在 EventRepository 接口中添加
    List<Event> findByHost(Integer host);

    List<Event> findBySuperviseStatus(String keyword);

    @Query("SELECT e.eventId FROM Event e WHERE e.host = :host")
    List<Long> findEventIdsByHostId(@Param("host") int host);

    List<Event> findAllByOrderByClickDesc();

    @Query("SELECT e FROM Event e WHERE " +
        "(:minFee IS NULL OR e.cost >= :minFee) AND " +
        "(:maxFee IS NULL OR e.cost <= :maxFee) AND " +
        "(:startDate IS NULL OR e.startDate >= :startDate) AND " +
        "(:endDate IS NULL OR e.endDate <= :endDate) AND " +
        "(:searchTerm IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Event> findFilteredEvents(
            @Param("minFee") Integer minFee,
            @Param("maxFee") Integer maxFee,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("searchTerm") String searchTerm
    );
    @Query("SELECT e FROM Event e WHERE e.superviseStatus = :status ORDER BY e.click DESC")
    List<Event> findAllByOrderByClickDescAndSuperviseStatus(@Param("status") String status);

}

