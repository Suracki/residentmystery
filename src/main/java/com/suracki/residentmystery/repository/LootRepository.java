package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.Loot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LootRepository extends JpaRepository<Loot, Integer> {
    @Query("SELECT u FROM Loot u WHERE u.lootName = ?1")
    Loot findByLootname(String lootName);

}
