package com.example.fera1999.edelh.clases;

public class Switches {

private int id;
private String place;
private String bulbState;
private String group_id;


    public Switches(){

    }

    public Switches(int id, String place, String group_id){
        this.id = id;
        this.place = place;
        this.group_id = group_id;
    }

    public Switches(String place, String group_id){
        this.place = place;
        this.group_id = group_id;
    }

    public Switches(int id, String place,String bulbState,String group_id) {
        this.id = id;
        this.place = place;
        this.bulbState = bulbState;
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getplace() {
        return place;
    }

    public void setplace(String place) {
        this.place = place;
    }

    public String getbulbState() {
        return bulbState;
    }

    public void setbulbState(String bulbState) {
        this.bulbState = bulbState;
    }

    public String getgroup_id() {
        return group_id;
    }

    public void setgroup_id(String group_id) {
        this.group_id = group_id;
    }
}
