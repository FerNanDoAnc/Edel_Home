package com.example.fera1999.edelh.ui.home;

public class Foco {
    private int switch_id;
    private String place;


    public Foco() {
    }

    public Foco(int switch_id, String place) {
        this.switch_id = switch_id;
        this.place = place;
    }

    public int getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(int switch_id) {
        this.switch_id = switch_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return place;
    }
}
