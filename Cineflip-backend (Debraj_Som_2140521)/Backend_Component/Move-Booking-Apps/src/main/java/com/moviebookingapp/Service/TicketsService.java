package com.moviebookingapp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Entity.Tickets;
import com.moviebookingapp.Repo.TicketsRepo;

@Service
public class TicketsService {
@Autowired
private TicketsRepo ticketsRepo;
public List<Tickets> getAllTicktesBooked(){
	return this.ticketsRepo.findAll();
}

/*
 * public Tickets save(Tickets ticktes, String loginId) {
 * tickets.setLoginId(loginId); ticketsRepo.save(ticktes); return ticktes; }
 */
public Tickets save(Tickets ticket, String loginId) {
    ticket.setloginId(loginId);
    return ticketsRepo.save(ticket);
}
}
