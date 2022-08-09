package com.suracki.residentmystery.domain.temporary;

import com.suracki.residentmystery.domain.Interactable;
import com.suracki.residentmystery.domain.Loot;
import com.suracki.residentmystery.domain.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class GameState {

    private List<Room> rooms;
    private List<Loot> loots;
    private List<Interactable> interactables;
    private String currentRoom;
    private List<String> currentLoot;
    private LocalDateTime startTime;

    private static final Logger logger = LogManager.getLogger(GameState.class);

    public void logState() {
        logger.info("GameState for User contains:");
        logger.info(rooms.size() + " rooms.");
        logger.info(loots.size() + " loots.");
        logger.info(interactables.size() + " interactables.");
        logger.info(currentRoom + " as current room.");
        logger.info(currentLoot + " as current loot.");
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Loot> getLoots() {
        return loots;
    }

    public void setLoots(List<Loot> loots) {
        this.loots = loots;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }

    public void setInteractables(List<Interactable> interactables) {
        this.interactables = interactables;
    }

    public Room findRoom(String roomName) {
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    public Loot findLoot(String lootName) {
        for (Loot loot : loots) {
            if (loot.getLootName().equals(lootName)) {
                return loot;
            }
        }
        return null;
    }

    public Interactable findInteractable(String interName) {
        for (Interactable interactable : interactables) {
            if (interactable.getInteractableName().equals(interName)) {
                return interactable;
            }
        }
        return null;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<String> getCurrentLoot() {
        return currentLoot;
    }

    public void setCurrentLoot(List<String> currentLoot) {
        this.currentLoot = currentLoot;
    }

    public void addLoot(String loot) {
        currentLoot.add(loot);
    }

    public boolean hasLoot(String lootName) {
        for (String loot : currentLoot) {
            if (loot.equals(lootName)) {
                return true;
            }
        }
        return false;
    }

    public void removeLoot(String lootName) {
        currentLoot.remove(lootName);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
