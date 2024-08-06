package com.moviebookingapp.Entity;

import java.util.List;

public class ShowTiming {
    private String time;
    private int noOfTickets;
    private List<Integer> seatNumbers;

    public ShowTiming() {
        super();
    }

    public ShowTiming(String time, int noOfTickets, List<Integer> seatNumbers) {
        this.time = time;
        this.noOfTickets = noOfTickets;
        this.seatNumbers = seatNumbers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public List<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<Integer> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }
}
