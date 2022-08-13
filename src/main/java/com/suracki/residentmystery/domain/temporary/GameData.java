package com.suracki.residentmystery.domain.temporary;

import com.suracki.residentmystery.domain.*;

import java.util.List;

public class GameData {

    List<Room> rooms;
    List<Interactable> interactables;
    List<Loot> loots;
    List<ExitMapping> exitMappings;
    List<Ending> endings;
    List<Npc> npcs;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Interactable> getInteractables() {
        return interactables;
    }

    public void setInteractables(List<Interactable> interactables) {
        this.interactables = interactables;
    }

    public List<Loot> getLoots() {
        return loots;
    }

    public void setLoots(List<Loot> loots) {
        this.loots = loots;
    }

    public List<ExitMapping> getExitMappings() {
        return exitMappings;
    }

    public void setExitMappings(List<ExitMapping> exitMappings) {
        this.exitMappings = exitMappings;
    }

    public List<Ending> getEndings() {
        return endings;
    }

    public void setEndings(List<Ending> endings) {
        this.endings = endings;
    }

    public List<Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<Npc> npcs) {
        this.npcs = npcs;
    }
}
