package com.moviebookingapp.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Login")
public class LoginTable {
@Id
private String loginId;
private String password;
public LoginTable() {
}
public LoginTable(String string, String string2, boolean b) {
	super();
}
public LoginTable(Integer id, String loginId, String password) {
	super();
	this.loginId=loginId;
	this.password=password;
}
public LoginTable(String loginId, String password) {
	super();
	this.loginId=loginId;
	this.password=password;
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
}
