package com.suracki.residentmystery.domain;

import javax.persistence.*;

@Entity
@Table(name = "loots")
public class Loot {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String lootName;

    private String lootDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLootName() {
        return lootName;
    }

    public void setLootName(String lootName) {
        this.lootName = lootName;
    }

    public String getLootDesc() {
        return lootDesc;
    }

    public void setLootDesc(String lootDesc) {
        this.lootDesc = lootDesc;
    }
}
