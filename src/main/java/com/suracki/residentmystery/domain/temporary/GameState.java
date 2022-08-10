package com.suracki.residentmystery.domain.temporary;

import com.suracki.residentmystery.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<Room> rooms;
    private List<Loot> loots;
    private List<Interactable> interactables;
    private List<Npc> npcs;
    private List<ExitMapping> exitMappings;
    private List<Ending> endings;
    private String currentRoom;
    private List<String> currentLoot;
    private List<String> spokenToNpcs;
    private LocalDateTime startTime;

    private static final Logger logger = LogManager.getLogger(GameState.class);

    public void logState() {
        logger.info("GameState for User contains:");
        logger.info(rooms.size() + " rooms.");
        logger.info(loots.size() + " loots.");
        logger.info(interactables.size() + " interactables.");
        logger.info(npcs.size() + " npcs.");
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

    public List<Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<Npc> npcs) {
        this.npcs = npcs;
    }

    public Room findStartingRoom() {
        for (Room room : rooms) {
            if (room.isStartRoom()) {
                return room;
            }
        }
        return rooms.get(0);
    }

    public List<ExitMapping> getExitMappings() {
        return exitMappings;
    }

    public void setExitMappings(List<ExitMapping> exitMappings) {
        this.exitMappings = exitMappings;
    }

    public List<ExitMapping> findExitMappingByRoomname(String roomName) {
        List<ExitMapping> roomExits = new ArrayList<>();
        for (ExitMapping exitMapping : exitMappings) {
            if (exitMapping.getRoomName().equals(roomName)) {
                roomExits.add(exitMapping);
            }
        }
        return roomExits;
    }

    public List<Loot> findLootByRoomname(String roomName) {
        List<Loot> roomLoot = new ArrayList<>();
        for (Loot loot : loots) {
            if (loot.getRoomName() != null ) {
                if (loot.getRoomName().equals(roomName)) {
                    roomLoot.add(loot);
                }
            }
        }
        return roomLoot;
    }

    public List<Interactable> findInteractablesByRoomname(String roomName) {
        List<Interactable> roomInters = new ArrayList<>();
        for (Interactable interactable : interactables) {
            if (interactable.getRoomName().equals(roomName)) {
                roomInters.add(interactable);
            }
        }
        return roomInters;
    }

    public List<Npc> findNpcsByRoomname(String roomName) {
        List<Npc> roomNpcs = new ArrayList<>();
        for (Npc npc : npcs) {
            if (npc.getRoomName().equals(roomName)) {
                roomNpcs.add(npc);
            }
        }
        return roomNpcs;
    }

    public Interactable findInteractableByName(String interactableName) {
        for (Interactable interactable : interactables) {
            if (interactable.getInteractableName().equals(interactableName)) {
                return interactable;
            }
        }
        return null;
    }

    public List<Ending> getEndings() {
        return endings;
    }

    public void setEndings(List<Ending> endings) {
        this.endings = endings;
    }

    public Ending findEndingByName(String endingName) {
        for (Ending ending : endings) {
            if (ending.getEndingName().equals(endingName)) {
                return ending;
            }
        }
        return null;
    }

    public Npc findNpc(String npcName) {
        for (Npc npc : npcs) {
            if (npc.getNpcName().equals(npcName)) {
                return npc;
            }
        }
        return null;
    }

    public List<String> getSpokenToNpcs() {
        return spokenToNpcs;
    }

    public void setSpokenToNpcs(List<String> spokenToNpcs) {
        this.spokenToNpcs = spokenToNpcs;
    }

    public boolean hasSpokenTo(String npcName) {
        for (String npc : spokenToNpcs) {
            if (npc.equals(npcName)) {
                return true;
            }
        }
        return false;
    }
}
