package com.example.assessment_3.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "name")
    @NonNull
    public String name;
    @ColumnInfo(name = "review")
    @NonNull
    public String review;

    public double rating;

    public Customer( @NonNull String name, @NonNull String review, double rating) {
        this.name=name;
        this.review=review;
        this.rating=rating;
    }
}
