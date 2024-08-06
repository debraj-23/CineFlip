package com.moviebookingapp.Entity;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection="User")

public class User {
	@Id
	private String loginId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String password;
    private String conformPassword;
    private String contactNumber;
    private boolean admin;
    private List<Tickets> bookedTickets;

    public User() {
        super();
    }

    public User(String userFirstName, String userLastName, String userEmail, String loginId, String password,
                String conformPassword, String contactNumber, boolean admin, List<Tickets> bookedTickets) {
        super();
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.loginId = loginId;
        this.password = password;
        this.conformPassword = conformPassword;
        this.contactNumber = contactNumber;
        this.admin = admin;
        this.bookedTickets = bookedTickets;
    }

	
	public void setUserFirstName(String userFirstName) {
		this.userFirstName=userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName=userLastName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail=userEmail;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId=loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getConformPassword() {
		return conformPassword;
	}
	public void setConformPassword(String conformPassword) {
		this.conformPassword=conformPassword;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber=contactNumber;
	}
	public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

	/*
	 * public ObjectId getUserid() { // TODO Auto-generated method stub return
	 * userid; }
	 */
	 public List<Tickets> getBookedTickets() {
	        return bookedTickets;
	    }

	    public void setBookedTickets(List<Tickets> bookedTickets) {
	        this.bookedTickets = bookedTickets;
	    }
}
