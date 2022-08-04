package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.Loot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LootRepository extends JpaRepository<Loot, Integer> {
    @Query("SELECT u FROM Loot u WHERE u.lootName = ?1")
    Loot findByLootname(String lootName);

    @Query("SELECT u FROM Loot u WHERE u.roomName = ?1")
    List<Loot> findByRoomname(String roomName);
}
