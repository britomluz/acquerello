package com.restaurant.acquerello.controllers;

import com.restaurant.acquerello.dtos.BookingCreateDTO;
import com.restaurant.acquerello.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    public BookingService bookingService;

    @RequestMapping(value = "/booking/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAddress(@RequestBody BookingCreateDTO bookingCreateDTO) {

        System.out.println(bookingCreateDTO.getDate().lengthOfMonth());

        return new ResponseEntity<>("Successfully booked", HttpStatus.CREATED);
    }
}
