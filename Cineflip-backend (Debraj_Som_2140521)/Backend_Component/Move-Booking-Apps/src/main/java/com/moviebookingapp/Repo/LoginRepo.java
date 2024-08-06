package com.moviebookingapp.Repo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.moviebookingapp.Entity.LoginTable;
public interface LoginRepo extends MongoRepository<LoginTable,Integer> {

}
