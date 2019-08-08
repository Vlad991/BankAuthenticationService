package com.mybank.messaging.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
//@JsonPropertyOrder({"day", "month", "year"})
public class Date implements Serializable {
    private int day;
    private int month;
    private int year;

    public Date() {
        this(1, 1, 1900);
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String date) {
        String[] dateParse = date.split("/");
        this.day = Integer.parseInt(dateParse[0]);
        this.month = Integer.parseInt(dateParse[1]);
        this.year = Integer.parseInt(dateParse[2]);
    }

    @Override
    public String toString() {
        String day;
        String month;
        String year;

        day = Integer.toString(this.day);
        month = Integer.toString(this.month);
        year = Integer.toString(this.year);

        if (this.day < 10) {
            day = "0" + day;
        }
        if (this.month < 10) {
            month = "0" + month;
        }

        return day + "/" + month + "/" + year;
    }
}
