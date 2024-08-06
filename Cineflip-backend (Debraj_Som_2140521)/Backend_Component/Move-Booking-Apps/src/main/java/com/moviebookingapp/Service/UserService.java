package com.moviebookingapp.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Entity.User;
import com.moviebookingapp.Repo.UserRepo;



@Service
public class UserService {
@Autowired
private UserRepo userRepo;
public List<User> getAllUsers(){
	return this.userRepo.findAll();
}
public User save(User user) {
	userRepo.save(user);
	return user;
}
public String getPasswordByLoginId(String loginId) {
    User user = userRepo.findByLoginId(loginId);
    if (user != null) {
        return user.getPassword();
    } else {
        return null;
    }
}
public User registerAdmin(User admin) {
    admin.setAdmin(true); // Set the user as admin
    return userRepo.save(admin);
}
public User findByLoginIdAndPassword(String loginId, String password) {
    return userRepo.findByLoginIdAndPassword(loginId, password);
}

public User findById(String loginId) {  // Add this method
    return userRepo.findByLoginId(loginId);
}
}
