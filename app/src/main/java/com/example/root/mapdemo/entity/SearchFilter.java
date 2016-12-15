package com.example.root.mapdemo.entity;

public class SearchFilter {

    private String beginDate;

    private String endDate;
    private boolean airConditioner;
    private int passangers;
    private int luggage;
    private int officeOriginId;
    private int officeEndId;

    public String getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public boolean isAirConditioner() {
        return airConditioner;
    }
    public void setAirConditioner(boolean airConditioner) {
        this.airConditioner = airConditioner;
    }
    public int getPassangers() {
        return passangers;
    }
    public void setPassangers(int passangers) {
        this.passangers = passangers;
    }
    public int getLuggage() {
        return luggage;
    }
    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    @Override
    public String toString() {
        return "SearchFilter [beginDate=" + beginDate + ", endDate=" + endDate + ", airConditioner=" + airConditioner
                + ", passangers=" + passangers + ", luggage=" + luggage + "]";
    }
    public int getOfficeOriginId() {
        return officeOriginId;
    }
    public void setOfficeOriginId(int officeOriginId) {
        this.officeOriginId = officeOriginId;
    }
    public int getOfficeEndId() {
        return officeEndId;
    }
    public void setOfficeEndId(int officeEndId) {
        this.officeEndId = officeEndId;
    }



}
