package com.suracki.residentmystery.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String roomName;

    private String roomDesc;

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

    public boolean isStartRoom() {
        return startRoom;
    }

    public void setStartRoom(boolean startRoom) {
        this.startRoom = startRoom;
    }

}
