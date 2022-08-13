package com.suracki.residentmystery.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "npcs")
public class Npc {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "NPC name is mandatory.")
    private String npcName;

    @NotEmpty(message = "NPC description is mandatory.")
    private String npcDesc;

    @NotEmpty(message = "Enter name of room NPC starts in.")
    private String roomName;

    private boolean canWander;

    private int wanderChance;

    @NotEmpty(message = "First interaction text is mandatory.")
    private String firstInteraction;

    @NotEmpty(message = "Repeat interaction text is mandatory.")
    private String repeatInteraction;

    @NotEmpty(message = "Solving interaction text is mandatory.")
    private String solvingInteraction;

    @NotEmpty(message = "Aleady solved interaction text is mandatory.")
    private String alreadySolvedInteraction;

    private String rewardLoot;

    private boolean locked;

    private String keyName;

    private boolean consumeKey;

    private boolean gameEnd;

    private String endName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    public String getNpcDesc() {
        return npcDesc;
    }

    public void setNpcDesc(String npcDesc) {
        this.npcDesc = npcDesc;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isCanWander() {
        return canWander;
    }

    public void setCanWander(boolean canWander) {
        this.canWander = canWander;
    }

    public String getFirstInteraction() {
        return firstInteraction;
    }

    public void setFirstInteraction(String firstInteraction) {
        this.firstInteraction = firstInteraction;
    }

    public String getRepeatInteraction() {
        return repeatInteraction;
    }

    public void setRepeatInteraction(String repeatInteraction) {
        this.repeatInteraction = repeatInteraction;
    }

    public String getSolvingInteraction() {
        return solvingInteraction;
    }

    public void setSolvingInteraction(String solvingInteraction) {
        this.solvingInteraction = solvingInteraction;
    }

    public String getAlreadySolvedInteraction() {
        return alreadySolvedInteraction;
    }

    public void setAlreadySolvedInteraction(String alreadySolvedInteraction) {
        this.alreadySolvedInteraction = alreadySolvedInteraction;
    }

    public String getRewardLoot() {
        return rewardLoot;
    }

    public void setRewardLoot(String rewardLoot) {
        this.rewardLoot = rewardLoot;
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

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public int getWanderChance() {
        return wanderChance;
    }

    public void setWanderChance(int wanderChance) {
        this.wanderChance = wanderChance;
    }

    public boolean doesConsumeKey() {
        return consumeKey;
    }

    public void setConsumeKey(boolean consumeKey) {
        this.consumeKey = consumeKey;
    }

    public boolean isConsumeKey() {
        return consumeKey;
    }
}
