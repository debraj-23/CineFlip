package com.moviebookingapp.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
//import org.assertj.core.util.Arrays;
import org.junit.Test;
//import org.springframework.http.ResponseEntity;

//import com.moviebookingapp.Controller.UserController;
import com.moviebookingapp.Entity.Moves;
import com.moviebookingapp.Entity.Theater;
import com.moviebookingapp.Repo.MoveRepo;

class MovesServiceMockTest {
	@Test
	public void testForSaveUser() {
		MoveRepo movesRepositoryMock = mock(MoveRepo.class);
		Theater theater = new Theater();
		theater.setTheaterName("Inox");
		theater.setNoOfTickets(120);
		theater.setStatus("BOOK ASAP");
		List<Integer> list = new ArrayList<Integer>(120);
		theater.setSeatNumber(list);
		List<Theater> theaterList = Arrays.asList(theater);
		Moves move = new Moves();
		move.setMovename("AAA-I");
		move.setTheaters(theaterList);
		when(movesRepositoryMock.save(any())).thenReturn(move);
		MovesService moveService = new MovesService(movesRepositoryMock);
		Moves returnedMove = moveService.save(move);
		assertEquals(move.getMovename(), returnedMove.getMovename());

	}

	@Test
	public void testForGetAllMoves() {
		MoveRepo movesRepositoryMock = mock(MoveRepo.class);
		Theater theater = new Theater();
		theater.setTheaterName("Inox");
		theater.setNoOfTickets(120);
		theater.setStatus("BOOK ASAP");
		List<Integer> list = new ArrayList<Integer>(120);
		theater.setSeatNumber(list);
		List<Theater> theaterList = Arrays.asList(theater);
		Moves move = new Moves();
		move.setMovename("AAA-I");
		move.setTheaters(theaterList);
		List<Moves> movesList = Arrays.asList(move);
		when(movesRepositoryMock.findAll()).thenReturn(movesList);
		MovesService moveService = new MovesService(movesRepositoryMock);
		List<Moves> returnedMoveList = moveService.getAllMoves();
		assertEquals(1, returnedMoveList.size());

	}

	@Test
	public void testForGetAMovePositiveScenario() {
		MoveRepo movesRepositoryMock = mock(MoveRepo.class);
		Theater theater = new Theater();
		theater.setTheaterName("Inox");
		theater.setNoOfTickets(120);
		theater.setStatus("BOOK ASAP");
		List<Integer> list = new ArrayList<Integer>(120);
		theater.setSeatNumber(list);
		List<Theater> theaterList = Arrays.asList(theater);
		Moves move = new Moves();
		move.setMovename("AAA-I");
		move.setTheaters(theaterList);
		when(movesRepositoryMock.findMoveByName("AAA-I")).thenReturn(move);
		MovesService moveService = new MovesService(movesRepositoryMock);
		Moves returnedMove = moveService.getSingleMoveOnName("AAA-I");
		assertEquals(move.getMovename(), returnedMove.getMovename());

	}

	@Test
	public void testForGetAMoveNegativeScenario() {
		MoveRepo movesRepositoryMock = mock(MoveRepo.class);
		Theater theater = new Theater();
		theater.setTheaterName("Inox");
		theater.setNoOfTickets(120);
		theater.setStatus("BOOK ASAP");
		List<Integer> list = new ArrayList<Integer>(120);
		theater.setSeatNumber(list);
		List<Theater> theaterList = Arrays.asList(theater);
		Moves move = new Moves();
		move.setMovename("AAA-I");
		move.setTheaters(theaterList);
		when(movesRepositoryMock.findMoveByName("AAA-I")).thenReturn(move);
		MovesService moveService = new MovesService(movesRepositoryMock);
		Moves returnedMove = moveService.getSingleMoveOnName("CCC");
		assertNull(returnedMove);

	}

	

}
