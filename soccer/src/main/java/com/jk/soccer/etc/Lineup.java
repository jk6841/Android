package com.jk.soccer.etc;

public class Lineup {
    public Lineup(Integer position, Integer shirt, String name){
        this.position = position;
        this.shirt = shirt;
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getShirt() {
        return shirt;
    }

    public void setShirt(Integer shirt) {
        this.shirt = shirt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer position;
    private Integer shirt;
    private String name;
}
