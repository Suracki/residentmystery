package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.ExitMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExitMappingRepository extends JpaRepository<ExitMapping, Integer> {

    @Query("SELECT u FROM ExitMapping u WHERE u.roomName = ?1")
    List<ExitMapping> findByRoomname(String roomName);

}
