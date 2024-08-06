package com.moviebookingapp.Controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.Entity.Moves;
import com.moviebookingapp.Entity.Theater;
import com.moviebookingapp.Entity.Tickets;
import com.moviebookingapp.Service.MovesService;
import com.moviebookingapp.Service.TicketsService;
import com.moviebookingapp.kafka.KafkaProducer;

@RestController
@CrossOrigin(origins = "http:localhost:3000")

public class KafkaController {
	private static final Logger Logger=LoggerFactory.getLogger(KafkaController.class);
	private static final org.slf4j.Logger LOGGER = null;
	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private TicketsService ticketsService;
	@Autowired
	private MovesService movesService;
	@GetMapping("/fetchNoofTicketsBookedForAMove/{moveName}")
	public ResponseEntity<String>getNoOfTicketsBookedForAMove(@PathVariable String moveName){
		System.out.println(moveName);
		//LOGGER.info("Fetching the number of tickets booked for a movie");
		
		List<Tickets>bookedTicketsList=ticketsService.getAllTicktesBooked();
		int noOfTotalTicketsBooked=0;
		for(Tickets list:bookedTicketsList) {
			if(moveName.equals(list.getMovename())) {
				noOfTotalTicketsBooked=noOfTotalTicketsBooked+list.getNumberOfTickets();
			}
		}
		kafkaProducer.sendMessage(noOfTotalTicketsBooked);
		//LOGGER.info("Process completed for Fetching number of tickets booked for a movie");
		return ResponseEntity.ok("Number of tickets booked for a movie is sent to topic");
	}
	@GetMapping("/fetchNoofTicketsAvailableForAMoveByTheaterName/{moveName}/{theaterName}")
	public ResponseEntity<String> getNoOfTicketsAvailableForAMove(
	        @PathVariable String moveName, 
	        @PathVariable String theaterName) {
	    
	    // Retrieve the movie by name
	    Moves movie = movesService.getSingleMoveOnName(moveName);
	    
	    if (movie == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Movie not found.");
	    }
	    
	    // Retrieve the list of theaters
	    List<Theater> listOfTheaters = movie.getTheaters();
	    
	    // Search for the theater in a case-insensitive manner
	    for (Theater theater : listOfTheaters) {
	        if (theater.getTheaterName().equalsIgnoreCase(theaterName)) {
	            int noOfTickets = theater.getNoOfTickets();
	            kafkaProducer.sendMessage(noOfTickets);
	            return ResponseEntity.ok(String.valueOf(noOfTickets));
	        }
	    }
	    
	    // If no theater is found
	    return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                         .body("Movie not found for this theater.");
	}

	@PutMapping("/updateTicketBookingStatus/{moveName}/{theaterName}")
	public ResponseEntity<?> updateTicketStatus(@PathVariable String moveName, @PathVariable String theaterName) {
	    // LOGGER.info("Process started to update the status of the tickets in a theater");
	    System.out.println("Updating ticket status...");

	    // Fetch the movie
	    Moves existingMove = movesService.getSingleMoveOnName(moveName);
	    if (existingMove == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
	    }

	    List<Theater> listOfTheaters = existingMove.getTheaters();
	    String updatedStatus = "BOOK ASAP";
	    boolean statusChanged = false;

	    for (Theater theater : listOfTheaters) {
	        if (theater.getTheaterName().equals(theaterName)) {
	            if (theater.getNoOfTickets() == 0) {
	                theater.setStatus("SOLD OUT");
	                updatedStatus = "SOLD OUT";
	                statusChanged = true; // Track if we changed the status
	            } else {
	                theater.setStatus("BOOK ASAP");
	                statusChanged = true;
	            }
	        }
	    }

	    // Only save if there was a status change
	    if (statusChanged) {
	        movesService.save(existingMove); // Save the updated movie status
	    }

	    // Send updated status to Kafka topic
	    kafkaProducer.sendMessage(updatedStatus);
	    // LOGGER.info("Process completed to update the status of the tickets in a theater");
	    return ResponseEntity.ok("Availability status is sent to topic");
	}
}
