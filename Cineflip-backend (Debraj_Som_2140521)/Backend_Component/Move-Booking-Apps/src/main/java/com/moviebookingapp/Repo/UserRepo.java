package com.moviebookingapp.Repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.moviebookingapp.Entity.User;
public interface UserRepo extends MongoRepository<User,ObjectId> {
	User findByLoginId(String loginId);
	User findByLoginIdAndPassword(String loginId, String password);
	
}
