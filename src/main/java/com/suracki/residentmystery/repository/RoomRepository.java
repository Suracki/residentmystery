package com.suracki.residentmystery.repository;

import com.suracki.residentmystery.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT u FROM Room u WHERE u.roomName = ?1")
    Room findByRoomname(String roomName);

    @Query("SELECT u FROM Room u WHERE u.startRoom = true")
    Room findStartingRoom();

}
