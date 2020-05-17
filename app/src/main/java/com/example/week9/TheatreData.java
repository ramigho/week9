package com.example.week9;

public class TheatreData {

    String dttmShowStart;
    String EventID;
    String Title;
    String ProductionYear;
    String TheatreID;
    String Theatre;

    public TheatreData(){}

    public TheatreData(String TheatreID, String Theatre){
        this.TheatreID = TheatreID;
        this.Theatre = Theatre;
    }

    public TheatreData(String dttmShowStart, String EventID, String Title, String ProductionYear, String TheatreID, String Theatre) {
        this.dttmShowStart = dttmShowStart;
        this.EventID = EventID;
        this.Title = Title;
        this.ProductionYear = ProductionYear;
        this.TheatreID = TheatreID;
        this.Theatre = Theatre;
    }

    public TheatreData(String Title) {
        this.Title = Title;
    }

    public String getDttmShowStart() {
        return dttmShowStart;
    }

    public String getEventID() {
        return EventID;
    }

    public String getTitle() {
        return Title;
    }

    public String getProductionYear() {
        return ProductionYear;
    }

    public String getTheatreID() {
        return TheatreID;
    }

    public String getTheatre() {
        return Theatre;
    }
}
