package com.moviebookingapp.Repo;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.moviebookingapp.Entity.Tickets;
public interface TicketsRepo extends MongoRepository<Tickets,String> {

}