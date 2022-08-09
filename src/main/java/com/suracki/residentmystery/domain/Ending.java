package com.suracki.residentmystery.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "endings")
public class Ending {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Ending name is mandatory.")
    private String endingName;

    @NotEmpty(message = "Ending text is mandatory.")
    private String endingText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndingText() {
        return endingText;
    }

    public void setEndingText(String endingText) {
        this.endingText = endingText;
    }

    public String getEndingName() {
        return endingName;
    }

    public void setEndingName(String endingName) {
        this.endingName = endingName;
    }
}
