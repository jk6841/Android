package com.jk.soccer.etc;

public enum Role {
    NONE(0),
    FW(1),
    MF(2),
    DF(3),
    GK(4),
    COACH(5);

    private Integer value;

    Role(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public void setValue(Integer value){
        this.value = value;
    }
}
