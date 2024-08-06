package com.moviebookingapp.Controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import com.moviebookingapp.Controller.UserController;
import com.moviebookingapp.Entity.LoginTable;
import com.moviebookingapp.Entity.Moves;
import com.moviebookingapp.Entity.Theater;
import com.moviebookingapp.Entity.Tickets;
import com.moviebookingapp.Entity.User;
import com.moviebookingapp.Service.LoginService;
import com.moviebookingapp.Service.MovesService;
import com.moviebookingapp.Service.TicketsService;
import com.moviebookingapp.Service.UserService;
import java.util.List;

class UserControllerMockTest {
	private static final int ArrayList=0;
	@Test
	public void userRegistrationScenario() throws Exception{
		UserService userServiceMock=mock(UserService.class);
		LoginService loginServiceMock=mock(LoginService.class);
		User user=new User();
		//user.setUserid(1);
		user.setUserFirstName("Vinoth");
		user.setUserLastName("Ragul");
		user.setUserEmail("vino@gmail.com");
		user.setLoginId("vinoragul");
		user.setPassword("Vino@1234");
		user.setConformPassword("Vino@1234");
		user.setContactNumber("9750709739");
		when(userServiceMock.save(user)).thenReturn(user);
		UserController userController=new UserController(userServiceMock, loginServiceMock);
		//ResponseEntity<?> getUser=userController.registrationRequest(user);
		//assertEquals("<200 OK OK,[]>", getUser.toString());
	}
	@Test
	public void getAllUsersNegativeScenario() {
		UserService userServiceMock=mock(UserService.class);
		List<User> listofUsers=new ArrayList<>(0);
		when(userServiceMock.getAllUsers()).thenReturn(listofUsers);
		UserController userController=new UserController(userServiceMock);
		List<User> getUserList=userController.getAllUsers();
		assertEquals(0,getUserList.size());
	}
	@Test
	public void userLoginPositiveScenario() throws Exception{
		LoginService loginServiceMock=mock(LoginService.class);
		LoginTable loginTable=new LoginTable();
		loginTable.setLoginId("Vinoragul");
		loginTable.setPassword("Vino@1234");
		//List<LoginTable> loginList=Arrays.asList(loginTable);
		List<LoginTable> loginList=Arrays.asList(loginTable);
		when(loginServiceMock.getAllIds()).thenReturn(loginList);
		UserController userController=new UserController(loginServiceMock);
		ResponseEntity<?> login=userController.loginRequest(loginTable);
		assertEquals("{message=User Logged In Successfully!!!}", login.getBody().toString());
		
	}
	@Test
	public void getAllUserPositiveScenario() {
		UserService userServiceMock=mock(UserService.class);
		User user=new User();
		//user.setUserid(1);
		user.setUserFirstName("Vinoth");
		user.setUserLastName("Ragul");
		user.setUserEmail("vino@gmail.com");
		user.setLoginId("vinoragul");
		user.setPassword("Vino@1234");
		user.setConformPassword("Vino@1234");
		user.setContactNumber("9750709739");
		List<User> userList=Arrays.asList(user);
		when(userServiceMock.getAllUsers()).thenReturn(userList);
		UserController userController=new UserController(userServiceMock);
		List<User> getUserList=userController.getAllUsers();
		assertEquals(1, getUserList.size());
	}
	@Test
	public void userNegativeScenario() throws Exception{
		LoginService loginServiceMock=mock(LoginService.class);
		LoginTable loginTable=new LoginTable();
		loginTable.setLoginId("Vinoragul");
		loginTable.setPassword("Vino@1234");
		LoginTable loginTableTemp=new LoginTable();
		loginTableTemp.setLoginId("Venkat");
		loginTableTemp.setPassword("Venkatji@1");
		List<LoginTable> loginList=Arrays.asList(loginTable);
		when(loginServiceMock.getAllIds()).thenReturn(loginList);
		UserController userController=new UserController(loginServiceMock);
		ResponseEntity<?> login=userController.loginRequest(loginTableTemp);
		assertEquals("{message=Please Enter Valid Login Credentials!!!}", login.getBody().toString());
		
	}

	/*
	 * @Test public void adminRegistrationPositiveScenario() throws Exception{
	 * AdminLoginService adminRegServiceMock=mock(AdminLoginService.class);
	 * AdminLoginTable adminLoginTable=new AdminLoginTable();
	 * adminLoginTable.setAdminLoginId("Admin");
	 * adminLoginTable.setPassword("Admin@1234");
	 * when(adminRegServiceMock.save(adminLoginTable)).thenReturn(adminLoginTable);
	 * UserController userController=new UserController(adminRegServiceMock);
	 * ResponseEntity<AdminLoginTable>
	 * getAdmin=userController.adminRegistration(adminLoginTable);
	 * assertEquals("<200 OK OK,[]>", getAdmin.toString()); }
	 * 
	 * @Test public void adminLoginPositiveScenario() throws Exception{
	 * AdminLoginService adminloginServiceMock=mock(AdminLoginService.class);
	 * AdminLoginTable adminLoginTable=new AdminLoginTable();
	 * adminLoginTable.setAdminLoginId("Admin");
	 * adminLoginTable.setPassword("Admin@1234"); List<AdminLoginTable>
	 * adminLoginList=Arrays.asList(adminLoginTable);
	 * when(adminloginServiceMock.getAllIds()).thenReturn(adminLoginList);
	 * UserController userController=new UserController(adminloginServiceMock);
	 * ResponseEntity<?> login=userController.adminLoginRequest(adminLoginTable);
	 * assertEquals("{message=Admin Logged In Successfully!!!}",
	 * login.getBody().toString());
	 * 
	 * }
	 * 
	 * @Test public void adminLoginNegativeScenario() throws Exception{
	 * AdminLoginService adminloginServiceMock=mock(AdminLoginService.class);
	 * AdminLoginTable adminLoginTable=new AdminLoginTable();
	 * adminLoginTable.setAdminLoginId("Admin");
	 * adminLoginTable.setPassword("Admin@1234"); AdminLoginTable
	 * adminLoginTableTemp=new AdminLoginTable();
	 * adminLoginTable.setAdminLoginId("Admin1");
	 * adminLoginTable.setPassword("Admin1@1234"); List<AdminLoginTable>
	 * adminLoginList=Arrays.asList(adminLoginTable);
	 * when(adminloginServiceMock.getAllIds()).thenReturn(adminLoginList);
	 * UserController userController=new UserController(adminloginServiceMock);
	 * ResponseEntity<?>
	 * login=userController.adminLoginRequest(adminLoginTableTemp);
	 * assertEquals("{message=Please enter a valid login credintials!!!}",
	 * login.getBody().toString());
	 * 
	 * }
	 */
    @Test
    public void addingMovePositiveScenario() throws Exception{
    	MovesService movesServiceMock=mock(MovesService.class);
    	Theater theater=new Theater();
    	theater.setTheaterName("Inox");
    	theater.setNoOfTickets(120);
    	theater.setStatus("BOOK ASAP");
    	List<Integer> list=new ArrayList<Integer>(120);
    	theater.setSeatNumber(list);
    	List<Theater> theaterList=Arrays.asList(theater);
    	Moves move=new Moves();
    	move.setMovename("AAA-I");
    	move.setTheaters(theaterList);
    	when(movesServiceMock.save(move)).thenReturn(move);
    	UserController userController=new UserController(movesServiceMock);
    	ResponseEntity<?> login=userController.movesAddingRequest(move);
    	assertEquals("{message=Move Added Successfully!!!}",login.getBody().toString());
    	
    }
    @Test
    public void fetchingAllMovePositiveScenario() throws Exception{
    	MovesService movesServiceMock=mock(MovesService.class);
    	Theater theater=new Theater();
    	theater.setTheaterName("Inox");
    	theater.setNoOfTickets(120);
    	theater.setStatus("BOOK ASAP");
    	List<Integer> list=new ArrayList<Integer>(120);
    	theater.setSeatNumber(list);
    	List<Theater> theaterList=Arrays.asList(theater);
    	Moves move=new Moves();
    	move.setMovename("AAA-I");
    	move.setTheaters(theaterList);
    	List<Moves> movesList=Arrays.asList(move);
    	when(movesServiceMock.getAllMoves()).thenReturn(movesList);
    	UserController userController=new UserController(movesServiceMock);
    	List<Moves> listOfMoves=userController.getAllMoves();
    	assertEquals(1, listOfMoves.size());
    	
    }
    @Test
    public void fetchingAllMoveNegativeScenario() throws Exception{
    	MovesService movesServiceMock=mock(MovesService.class);
    	List<Moves> listOfMoves= new ArrayList<>(0);
    	when(movesServiceMock.getAllMoves()).thenReturn(listOfMoves);
    	UserController userController=new UserController(movesServiceMock);
    	List<Moves> movesList=userController.getAllMoves();
    	assertEquals(0, movesList.size());
    	
    }
    @Test
    public void fetchingAMoveByNamePositiveScenario() throws Exception{
    	MovesService movesServiceMock=mock(MovesService.class);
    	Theater theater=new Theater();
    	theater.setTheaterName("Inox");
    	theater.setNoOfTickets(120);
    	theater.setStatus("BOOK ASAP");
    	List<Integer> list=new ArrayList<Integer>(120);
    	theater.setSeatNumber(list);
    	List<Theater> theaterList=Arrays.asList(theater);
    	Moves move=new Moves();
    	move.setMovename("AAA-I");
    	move.setTheaters(theaterList);
    	when(movesServiceMock.getSingleMoveOnName("AAA-I")).thenReturn(move);
    	UserController userController=new UserController(movesServiceMock);
    	ResponseEntity<?> singleMove=userController.getMoveOnName("AAA-I");
    	assertEquals("200 OK", singleMove.getStatusCode().toString());
    	
    }
    @Test
    public void fetchingAMoveByNameNegativeScenario() throws Exception{
    	MovesService movesServiceMock=mock(MovesService.class);
    	Theater theater=new Theater();
    	theater.setTheaterName("Inox");
    	theater.setNoOfTickets(120);
    	theater.setStatus("BOOK ASAP");
    	List<Integer> list=new ArrayList<Integer>(120);
    	theater.setSeatNumber(list);
    	List<Theater> theaterList=Arrays.asList(theater);
    	Moves move=new Moves();
    	move.setMovename("AAA-I");
    	move.setTheaters(theaterList);
    	when(movesServiceMock.getSingleMoveOnName("AAA-I")).thenReturn(move);
    	UserController userController=new UserController(movesServiceMock);
    	ResponseEntity<?> singleMove=userController.getMoveOnName("BBB");
    	assertEquals("{message= Move Is Not Existed!!!}", singleMove.getBody().toString());
    	
    }
    @Test
    public void moveTicketBookingPositiveScenario() throws Exception{
    	TicketsService ticketsServiceMock=mock(TicketsService.class);
    	MovesService movesServiceMock=mock(MovesService.class);
    	Tickets ticket=new Tickets();
    	ticket.setId("1");
    	ticket.setMovename("AAA-I");
    	ticket.setTheaterName("Inox");
    	ticket.setNumberOfTickets(120);
        List<Integer> list=new ArrayList<Integer>(120);
    	ticket.setSeatNumber(list);
    	Theater theater=new Theater();
    	theater.setTheaterName("Inox");
    	theater.setNoOfTickets(120);
    	theater.setStatus("BOOK ASAP");
    	List<Integer> listOfTheater=new ArrayList<Integer>(120);
    	theater.setSeatNumber(listOfTheater);
    	List<Theater> theaterList=Arrays.asList(theater);
    	Moves move=new Moves();
    	move.setMovename("AAA-I");
    	move.setTheaters(theaterList);
    	when((movesServiceMock.getSingleMoveOnName(ticket.getMovename()))).thenReturn(move);
    	when(movesServiceMock.save(move)).thenReturn(move);
    	when(ticketsServiceMock.save(ticket)).thenReturn(ticket);
    	UserController userController=new UserController(movesServiceMock, ticketsServiceMock);
    	ResponseEntity<?> bookedTicket=userController.bookingMoveTicketsRequest(ticket);
    	assertEquals("{message= Tickets booked Sucessfully!!!}", bookedTicket.getBody().toString());
    	
    }
    @Test
    public void fetchAllBookedTicketsPositiveScenario() throws Exception{
    	TicketsService ticketsServiceMock=mock(TicketsService.class);
    	
    	Tickets ticket=new Tickets();
    	ticket.setId("1");
    	ticket.setMovename("AAA-I");
    	ticket.setTheaterName("Inox");
    	ticket.setNumberOfTickets(120);
        List<Integer> list=new ArrayList<Integer>(120);
        ticket.setSeatNumber(list);
        List<Tickets> theaterList=Arrays.asList(ticket);
        when(ticketsServiceMock.getAllTicktesBooked()).thenReturn(theaterList);
        UserController userController=new UserController(ticketsServiceMock);
    	List<Tickets> bookedTickets=userController.getAllBookedTickets();
    	assertEquals(1, bookedTickets.size());
    	
    	
    }
}   
