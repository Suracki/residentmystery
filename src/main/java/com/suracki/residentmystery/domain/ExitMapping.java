package com.suracki.residentmystery.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ExitMappings")
public class ExitMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Enter name of source room.")
    private String roomName;
    @NotEmpty(message = "Enter name of exit interactable.")
    private String exitName;
    @NotEmpty(message = "Enter direction of exit (eg north, west, up, down, etc).")
    private String exitDirection;

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

    public String getExitName() {
        return exitName;
    }

    public void setExitName(String exitName) {
        this.exitName = exitName;
    }

    public String getExitDirection() {
        return exitDirection;
    }

    public void setExitDirection(String exitDirection) {
        this.exitDirection = exitDirection;
    }
}
