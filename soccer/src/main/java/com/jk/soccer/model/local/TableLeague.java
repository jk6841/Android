package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tableLeague")
public class TableLeague {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private Integer ID;

    @NonNull
    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    @NonNull
    @ColumnInfo(name = "Country", defaultValue = "")
    private String country;

    @ColumnInfo(name = "ChildrenDate", defaultValue = "")
    private Date childrenDate;

    public TableLeague(@NonNull Integer ID,
                       @NonNull String name,
                       @NonNull String country,
                       Date childrenDate) {
        this.ID = ID;
        this.name = name;
        this.country = country;
        this.childrenDate = childrenDate;
    }

    @NonNull
    public Integer getID() {
        return ID;
    }

    public void setID(@NonNull Integer ID) {
        this.ID = ID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    public Date getChildrenDate() {
        return childrenDate;
    }

    public void setChildrenDate(Date childrenDate) {
        this.childrenDate = childrenDate;
    }
}
