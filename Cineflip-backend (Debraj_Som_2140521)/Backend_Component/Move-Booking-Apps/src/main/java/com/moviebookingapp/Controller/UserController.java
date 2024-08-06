package com.moviebookingapp.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.Entity.LoginTable;
import com.moviebookingapp.Entity.Moves;
import com.moviebookingapp.Entity.ShowTiming;
import com.moviebookingapp.Entity.Theater;
import com.moviebookingapp.Entity.Tickets;
import com.moviebookingapp.Entity.User;
import com.moviebookingapp.Service.LoginService;
import com.moviebookingapp.Service.MovesService;
import com.moviebookingapp.Service.TicketsService;
import com.moviebookingapp.Service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MovesService movesService;
    @Autowired
    private TicketsService ticketsService;

    public UserController() {}

    public UserController(MovesService movesServiceMock, TicketsService ticketsServiceMock) {
        this.movesService = movesServiceMock;
        this.ticketsService = ticketsServiceMock;
    }

    public UserController(UserService userServiceMock, LoginService loginServiceMock) {
        this.userService = userServiceMock;
        this.loginService = loginServiceMock;
    }

    public UserController(UserService userServiceMock) {
        this.userService = userServiceMock;
    }

    public UserController(LoginService loginServiceMock) {
        this.loginService = loginServiceMock;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<User> registrationRequest(@RequestBody User user) throws Exception {
        user.setBookedTickets(new ArrayList<>());
        User userReg = new User(
            user.getUserFirstName(),
            user.getUserLastName(),
            user.getUserEmail(),
            user.getLoginId(), // Ensure this is correctly passed
            user.getPassword(),
            user.getConformPassword(),
            user.getContactNumber(),
            user.isAdmin(),
            user.getBookedTickets()
        );

        LoginTable loginCredentials = new LoginTable(user.getLoginId(), user.getPassword(), user.isAdmin());
        loginService.save(loginCredentials);
        
        User savedUser = userService.save(userReg);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @GetMapping("/fetchAllUsers")
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginRequest(@RequestBody LoginTable login) throws Exception {
        User user = userService.findByLoginIdAndPassword(login.getLoginId(), login.getPassword());
        if (user != null) {
            // Prepare the response
            Map<String, Object> response = new HashMap<>();
            response.put("message", user.isAdmin() ? "Admin login successful." : "User login successful.");
            response.put("isAdmin", user.isAdmin());
            response.put("loginId", user.getLoginId()); // Add loginId to the response
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials.");
        }
    }


    @GetMapping("/forgetPassword/{loginId}")
    public String forgetPassword(@PathVariable String loginId) {
        String password = userService.getPasswordByLoginId(loginId);
        if (password != null) {
            return "Password for login ID " + loginId + " is: " + password;
        } else {
            return "Login ID " + loginId + " not found.";
        }
    }

    @RequestMapping(value = "/addingMoves", method = RequestMethod.POST)
    public ResponseEntity<?> movesAddingRequest(@RequestBody Moves move) throws Exception {
        List<Theater> listOfTheaters = new ArrayList<>();
        for (Theater list : move.getTheaters()) {
            List<ShowTiming> showTimings = list.getShowTimings().stream()
                .map(show -> new ShowTiming(show.getTime(), list.getNoOfTickets(),
                    IntStream.rangeClosed(1, list.getNoOfTickets()).boxed().collect(Collectors.toList())))
                .collect(Collectors.toList());

            Theater theater = new Theater(list.getTheaterName(), list.getNoOfTickets(), list.getStatus(), list.getLanguages(), showTimings);
            listOfTheaters.add(theater);
        }

        Moves addingMove = new Moves(move.getMovename(), listOfTheaters, move.getPosterUrl());
        movesService.save(addingMove);
        return ResponseEntity.ok(Map.of("message", "Move Added Successfully!!"));
    }

    @GetMapping("/fetchAMove/{movename}")
    public ResponseEntity<?> getMoveOnName(@PathVariable String movename) {
        Moves movie = movesService.getSingleMoveOnName(movename);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Movie not found."));
        } else {
            return ResponseEntity.ok(movie);
        }
    }

    @GetMapping("/fetchAllMoves")
    public List<Moves> getAllMovies() {
        return this.movesService.getAllMoves();
    }

    @DeleteMapping("/movies/{id}")
    public String deleteMovie(@PathVariable String id) {
        boolean isRemoved = movesService.deleteMovie(id);
        if (isRemoved) {
            return "Movie with ID " + id + " has been deleted.";
        } else {
            return "Movie with ID " + id + " not found.";
        }
    }

    @RequestMapping(value="/bookingMoveTickets", method=RequestMethod.POST)
    public ResponseEntity<?> bookingMoveTicketsRequest(@RequestBody Tickets tickets) throws Exception {
        Moves existingMove = movesService.getSingleMoveOnName(tickets.getMovename());
        if (existingMove == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        List<Theater> listOfTheater = existingMove.getTheaters();
        Theater selectedTheater = null;

        // Find the selected theater
        for (Theater theater : listOfTheater) {
            if(theater.getTheaterName().equalsIgnoreCase(tickets.getTheaterName())) {
                selectedTheater = theater;
                break;
            }
        }

        if (selectedTheater == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theater not found.");
        }

        // Find the selected show time
        ShowTiming selectedShowTiming = null;
        for (ShowTiming showTiming : selectedTheater.getShowTimings()) {
            if(showTiming.getTime().equals(tickets.getShowTime())) {
                selectedShowTiming = showTiming;
                break;
            }
        }

        if (selectedShowTiming == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Show time not found.");
        }

        // Check if there are enough available tickets
        if (selectedShowTiming.getNoOfTickets() < tickets.getNumberOfTickets()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient available tickets.");
        }

        // Deduct the number of tickets and remove the booked seats
        selectedShowTiming.setNoOfTickets(selectedShowTiming.getNoOfTickets() - tickets.getNumberOfTickets());
        if (selectedShowTiming.getSeatNumbers().removeAll(tickets.getSeatNumber())) {
            // Proceed with booking
            Tickets bookingMoveTickets = new Tickets(tickets.getMovename(), tickets.getTheaterName(),
                    tickets.getNumberOfTickets(), tickets.getSeatNumber(), tickets.getLanguage(), tickets.getShowTime(), tickets.getLoginId());
            
            // Pass both the ticket and loginId to the save method
            ticketsService.save(bookingMoveTickets, tickets.getLoginId());
            movesService.save(existingMove);  // Save the updated move

            // Save booked tickets against the user
            User user = userService.findById(tickets.getLoginId());
            if (user != null) {
                user.getBookedTickets().add(bookingMoveTickets);
                userService.save(user);
            }

            return ResponseEntity.ok(Map.of("message", "Ticket Booked Successfully!!!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some seats are not available.");
        }
    }


    
    @GetMapping("/fetchBookingDetails/{loginId}")
    public ResponseEntity<?> getBookingDetails(@PathVariable String loginId) { // Keep as String for URL
        try {
            //String loginId = new ObjectId(userId); // Convert to ObjectId
            User user = userService.findById(loginId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found."));
            } else {
                return ResponseEntity.ok(user.getBookedTickets());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid user ID format."));
        }
    }


}
