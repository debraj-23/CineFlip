package com.moviebookingapp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.moviebookingapp.Entity.LoginTable;
import com.moviebookingapp.Repo.LoginRepo;

@Service
public class LoginService {
@Autowired
private LoginRepo loginRepo;
public LoginService(LoginRepo repositoryMock) {
	// TODO Auto-generated constructor stub
	this.loginRepo=repositoryMock;
}
public List<LoginTable> getAllIds(){
	return this.loginRepo.findAll();
}
public LoginTable save(LoginTable loginData) {
	loginRepo.save(loginData);
	return loginData;
}
}
