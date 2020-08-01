package com.example.fera1999.edelh.ui.home;

public class Foco {
    private String switch_id;
    private String place;
    private int bulb_state;
    private int group_id;

    public Foco() {
    }

    public Foco(String switch_id, String place, int bulb_state, int group_id) {
        this.switch_id = switch_id;
        this.place = place;
        this.bulb_state = bulb_state;
        this.group_id = group_id;
    }

    public String getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(String switch_id) {
        this.switch_id = switch_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getBulb_state() {
        return bulb_state;
    }

    public void setBulb_state(int bulb_state) {
        this.bulb_state = bulb_state;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return place;
    }
}
