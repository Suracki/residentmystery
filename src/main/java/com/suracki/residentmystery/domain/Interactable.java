package com.suracki.residentmystery.domain;

import javax.persistence.*;

@Entity
@Table(name = "interactables")
public class Interactable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String interactableName;

    private String interactableDesc;

    private String solvedText;

    private String alreadySolvedText;

    private String[] contents;

    private boolean used;

    private boolean locked;

    private String keyName;

    private String roomName;

    private String target;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getInteractableName() {
        return interactableName;
    }

    public void setInteractableName(String interactableName) {
        this.interactableName = interactableName;
    }

    public String getInteractableDesc() {
        return interactableDesc;
    }

    public void setInteractableDesc(String interactableDesc) {
        this.interactableDesc = interactableDesc;
    }

    public String getSolvedText() {
        return solvedText;
    }

    public void setSolvedText(String solvedText) {
        this.solvedText = solvedText;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getAlreadySolvedText() {
        return alreadySolvedText;
    }

    public void setAlreadySolvedText(String alreadySolvedText) {
        this.alreadySolvedText = alreadySolvedText;
    }
}
