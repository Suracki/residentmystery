package com.suracki.residentmystery.security;

public class SessionDetails {

    Integer lastPageNumber;
    String lastViewedType;

    public Integer getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(Integer lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public String getLastViewedType() {
        return lastViewedType;
    }

    public void setLastViewedType(String lastViewedType) {
        this.lastViewedType = lastViewedType;
    }
}
