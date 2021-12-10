package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.BookingCreateDTO;
import com.restaurant.acquerello.dtos.BookingDTO;
import com.restaurant.acquerello.models.Booking;
import com.restaurant.acquerello.models.TableState;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.models.UserType;
import com.restaurant.acquerello.repositories.BookingRepository;
import com.restaurant.acquerello.services.BookingService;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    public UserService userService;

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public BookingService bookingService;

    @RequestMapping("/booking")
    public List<BookingDTO> getAll() {
        return bookingService.getAll().stream().map(BookingDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/booking/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAddress(Authentication authentication,  @RequestBody BookingCreateDTO bookingCreateDTO) {
        User user = userService.getByEmail(authentication.getName());

        if(bookingCreateDTO.getDate().lengthOfMonth() < 1) {
            return new ResponseEntity<>("The field date is empty", HttpStatus.FORBIDDEN);
        }

        LocalTime plus = bookingCreateDTO.getBookingHour().plusHours(4);

        Booking booking = new Booking(bookingCreateDTO.getDate(), bookingCreateDTO.getBookingHour(), plus, bookingCreateDTO.getSector(), 2, 2, TableState.PENDING);

        System.out.println(booking);
        user.addBooking(booking);

        bookingService.save(booking);

        return new ResponseEntity<>("Successfully booked", HttpStatus.CREATED);
    }

    @RequestMapping("/booking/cancel")
    public ResponseEntity<?> cancelBooking(@RequestParam Long id) {
        Booking booking = bookingRepository.getById(id);

        booking.setState(TableState.CANCELLED);

        bookingRepository.save(booking);

        return new ResponseEntity<>("Booking canceled", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/booking/edit", method = RequestMethod.PATCH)
    public ResponseEntity<?> editBooking(Authentication authentication, @RequestParam Long id, @RequestBody BookingCreateDTO bookingCreateDTO) {
        User user = userService.getByEmail(authentication.getName());
        Booking booking = bookingRepository.getById(id);

        if(user.getType().equals(UserType.USER)) {
            return new ResponseEntity<>("User cannot edit a booking", HttpStatus.FORBIDDEN);
        }

        if(bookingCreateDTO.getDate() != null) {
            booking.setDate(bookingCreateDTO.getDate());
        }

        if(bookingCreateDTO.getBookingHour() != null) {
            booking.setEndBooking(bookingCreateDTO.getBookingHour());
        }

        if(bookingCreateDTO.getSector() != null) {
            booking.setSector(bookingCreateDTO.getSector());
        }

        if(bookingCreateDTO.getTable() != null) {
            booking.setTable(bookingCreateDTO.getTable());
        }

        if(bookingCreateDTO.getQuantity() != null) {
            booking.setQuantity(bookingCreateDTO.getQuantity());
        }

        bookingRepository.save(booking);

        return new ResponseEntity<>("Booking edited", HttpStatus.ACCEPTED);
    }
}
