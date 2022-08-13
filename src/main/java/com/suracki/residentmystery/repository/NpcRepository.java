package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.Interactable;
import com.suracki.residentmystery.domain.Npc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NpcRepository extends JpaRepository<Npc, Integer> {

    @Query("SELECT u FROM Npc u WHERE u.roomName = ?1")
    List<Npc> findByRoomname(String roomName);

}
