package com.jk.soccer.etc;

public enum Type {
    NONE(0),
    PERSON(1),
    TEAM(2),
    LEAGUE(3),
    MATCH(4),
    COUNTRY(5);

    private Integer value;

    private Type(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public void setValue(Integer value){
        this.value = value;
    }
}
