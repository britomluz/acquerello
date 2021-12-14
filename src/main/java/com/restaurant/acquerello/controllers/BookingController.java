package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.BookingCreateDTO;
import com.restaurant.acquerello.dtos.BookingDTO;
import com.restaurant.acquerello.dtos.OrderDTO;
import com.restaurant.acquerello.models.*;
import com.restaurant.acquerello.repositories.BookingRepository;
import com.restaurant.acquerello.services.BookingService;
import com.restaurant.acquerello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    public UserService userService;


    @Autowired
    public BookingService bookingService;

    @GetMapping("/booking")
    public List<BookingDTO> getAll() {
        return bookingService.getAll().stream().map(BookingDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/booking/create")
    public ResponseEntity<?> createAddress(Authentication authentication,  @RequestBody BookingCreateDTO bookingCreateDTO) {
        User user = userService.getByEmail(authentication.getName());
        Booking table = bookingService.getTable(bookingCreateDTO.getTable());

        if(bookingCreateDTO.getDate().lengthOfMonth() < 1) {
            return new ResponseEntity<>("The field date is empty", HttpStatus.FORBIDDEN);
        }


        if (table != null){
            return new ResponseEntity<>("Table not available", HttpStatus.FORBIDDEN);
        }


        LocalDate day = bookingCreateDTO.getDate();
        if (user.getBookings().stream().map(Booking::getDateBooking).collect(Collectors.toList()).contains(day)){
            return new ResponseEntity<>("Already have a booking to this day", HttpStatus.FORBIDDEN);
        }

        if(bookingCreateDTO.getTable() == null) {
            return new ResponseEntity<>("Select a table number", HttpStatus.FORBIDDEN);
        }

        LocalTime plus = bookingCreateDTO.getBookingHour().plusHours(4);

        Booking booking = new Booking(bookingCreateDTO.getDate(), bookingCreateDTO.getBookingHour().minusMinutes(1), plus, bookingCreateDTO.getSector(), bookingCreateDTO.getTable(), bookingCreateDTO.getQuantity(), TableState.ACCEPTED, TableAvailability.NOTAVAILABLE);

        System.out.println(booking);
        user.addBooking(booking);

        bookingService.save(booking);

        return new ResponseEntity<>("Successfully booked", HttpStatus.CREATED);
    }

    @GetMapping("/user/current/bookings")
    public ResponseEntity<?> getUserBookings(Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
       return new ResponseEntity<>( user.getBookings().stream().map(BookingDTO::new).collect(Collectors.toList()), HttpStatus.CREATED);
    }
//ver para que es esto
    @RequestMapping("/booking/cancel")
    public ResponseEntity<?> cancelBooking(@RequestParam Long id) {
        Booking booking = bookingService.getById(id).orElse(null);

        if (booking == null){
            return new ResponseEntity<>("Booking doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (booking.getState().equals(TableState.CANCELED)){
            return new ResponseEntity<>("Booking is already canceled", HttpStatus.FORBIDDEN);
        }

        booking.setState(TableState.CANCELED);

        bookingService.save(booking);

        return new ResponseEntity<>("Booking canceled", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/booking/edit")
    public ResponseEntity<?> editBooking(Authentication authentication, @RequestParam Long id, @RequestBody BookingCreateDTO bookingCreateDTO) {
        User user = userService.getByEmail(authentication.getName());
        Booking booking = bookingService.getById(id).orElse(null);

        if(user.getType().equals(UserType.USER)) {
            return new ResponseEntity<>("User cannot edit a booking", HttpStatus.FORBIDDEN);
        }
        if (booking == null){
            return new ResponseEntity<>("Booking doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (booking.getState().equals(TableState.CANCELED)){
            return new ResponseEntity<>("Booking is already canceled", HttpStatus.FORBIDDEN);
        }

        if(bookingCreateDTO.getDate() != null) {
            booking.setDate(bookingCreateDTO.getDate());
        }

        if(bookingCreateDTO.getBookingHour() != null) {
            booking.setBookingHour(bookingCreateDTO.getBookingHour());
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
        bookingService.save(booking);

        return new ResponseEntity<>("Booking edited", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/admin/booking/edit")
    public ResponseEntity<Object> edit( Authentication authentication,
                                        @RequestParam Long id,
                                        @RequestParam String type) {

        User user = userService.getByEmail(authentication.getName());
        Booking booking = bookingService.getById(id).orElse(null);
        //OrderState orderType = OrderState.valueOf(type);
        TableState state= TableState.valueOf(type);


        if(!user.getType().equals(UserType.ADMIN)) {
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }

        if (type.isEmpty()) {
            return new ResponseEntity<>("Order don't have any type", HttpStatus.BAD_REQUEST);
        }

        if(booking == null){
            return new ResponseEntity<>("Denied", HttpStatus.BAD_REQUEST);
        }
        if (booking.getState().equals(TableState.CANCELED)){
            return new ResponseEntity<>("Booking is already canceled", HttpStatus.FORBIDDEN);
        }

        booking.setState(state);
        bookingService.save(booking);

        return new ResponseEntity<>("Order state change",HttpStatus.OK);
    }



}
