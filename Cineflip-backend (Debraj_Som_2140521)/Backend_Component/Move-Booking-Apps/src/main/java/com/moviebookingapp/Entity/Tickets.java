package com.moviebookingapp.Entity;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Tickets")
public class Tickets {
    private String movename;
    private String theaterName;
    private int numberOfTickets;
    private List<Integer> seatNumber;
    private String language;
    private String showTime;
    private String loginId;

    public Tickets() {
        super();
    }

    public Tickets(String movename, String theaterName, int numberOfTickets, List<Integer> seatNumber, String language, String showTime,String userId) {
        this.movename = movename;
        this.theaterName = theaterName;
        this.numberOfTickets = numberOfTickets;
        this.seatNumber = seatNumber;
        this.language = language;
        this.showTime = showTime;
        this.loginId = loginId;
    }

    public String getMovename() {
        return movename;
    }

    public void setMovename(String movename) {
        this.movename = movename;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public List<Integer> getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(List<Integer> seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
    public String getLoginId() {
        return loginId;
    }

    public void setloginId(String userId) {
        this.loginId = userId;
    }
}

