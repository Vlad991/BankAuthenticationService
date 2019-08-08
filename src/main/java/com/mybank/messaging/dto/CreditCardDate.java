package com.mybank.messaging.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
//@JsonPropertyOrder({"month", "year"})
public class CreditCardDate implements Serializable {
    private int month; // 00
    private int year; // 00 (2000+)

    public CreditCardDate() {
        this(1, 0);
    }

    public CreditCardDate(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public CreditCardDate(String date) {
        String[] dateParse = date.split("/");
        this.month = Integer.parseInt(dateParse[0]);
        this.year = Integer.parseInt(dateParse[1]);
    }

    @Override
    public String toString() {
        String month;
        String year;

        month = Integer.toString(this.month);
        year = Integer.toString(this.year);

        if (this.month < 10) {
            month = "0" + month;
        }
        if (this.year < 10) {
            year = "0" + year;
        }

        return month + "/" + year;
    }
}
