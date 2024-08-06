package com.moviebookingapp.Repo;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.moviebookingapp.Entity.Moves;
public interface MoveRepo extends MongoRepository<Moves, String> {
	@Query("{ 'movename': { $regex: ?0, $options: 'i' } }")
    Moves findMoveByName(String movename);
}
