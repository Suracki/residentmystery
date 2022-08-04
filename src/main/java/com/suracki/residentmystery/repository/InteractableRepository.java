package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.Interactable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteractableRepository extends JpaRepository<Interactable, Integer> {
    @Query("SELECT u FROM Interactable u WHERE u.interactableName = ?1")
    Interactable findByInteractableName(String interactableName);

    @Query("SELECT u FROM Interactable u WHERE u.roomName = ?1")
    List<Interactable> findByRoomname(String roomName);
}
