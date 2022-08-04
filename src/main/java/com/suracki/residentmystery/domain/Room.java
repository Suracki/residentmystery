package com.suracki.residentmystery.domain;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String roomName;

    private String roomDesc;

    private String[] interactables;

    private String[] loots;

    @ElementCollection
    private Map<String, String> exits;

    private boolean startRoom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public String[] getInteractables() {
        return interactables;
    }

    public void setInteractables(String[] interactables) {
        this.interactables = interactables;
    }

    public String[] getLoots() {
        return loots;
    }

    public void setLoots(String[] loots) {
        this.loots = loots;
    }

    public Map<String, String> getExits() {
        return exits;
    }

    public void setExits(Map<String, String> exits) {
        this.exits = exits;
    }

    public boolean isStartRoom() {
        return startRoom;
    }

    public void setStartRoom(boolean startRoom) {
        this.startRoom = startRoom;
    }
}
