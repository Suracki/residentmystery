package com.suracki.residentmystery.domain.temporary;

import com.suracki.residentmystery.domain.ExitMapping;
import com.suracki.residentmystery.domain.Interactable;
import com.suracki.residentmystery.domain.Loot;
import com.suracki.residentmystery.domain.Room;

import java.util.List;

public class GameData {

    List<Room> rooms;
    List<Interactable> interactables;
    List<Loot> loots;
    List<ExitMapping> exitMappings;

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
}
