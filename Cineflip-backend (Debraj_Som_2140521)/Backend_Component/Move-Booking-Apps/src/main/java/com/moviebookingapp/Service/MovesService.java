package com.moviebookingapp.Service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Entity.Moves;
import com.moviebookingapp.Repo.MoveRepo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


@Service
public class MovesService {
	 @Autowired
	    private MongoTemplate mongoTemplate;
	@Autowired
	private MoveRepo moveRepo;
	public MovesService(MoveRepo movesRepositoryMock) {
		// TODO Auto-generated constructor stub
		this.moveRepo=movesRepositoryMock;
	}
	public List<Moves> getAllMoves(){
		return this.moveRepo.findAll();
	}
	public Moves save(Moves move) {
		moveRepo.save(move);
		return move;
	}
	public Optional<Moves> getMovesOnName(String moveName) {
	    return Optional.ofNullable(moveRepo.findMoveByName(moveName));
	}
	public Moves getSingleMoveOnName(String moveName) {
	    return moveRepo.findMoveByName(moveName);
	}

	public boolean deleteMovie(String id) {
        String idLower = id.toLowerCase();

        Query query = new Query(Criteria.where("_id").regex(idLower, "i"));

        Moves movie = mongoTemplate.findOne(query, Moves.class);

        if (movie != null) {
            mongoTemplate.remove(movie);
            return true;
        } else {
            return false;
        }
    }

}