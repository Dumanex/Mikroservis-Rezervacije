package com.example.bookingsservice.controller;

import com.example.bookingsservice.DTOs.BookingCreateDTO;
import com.example.bookingsservice.DTOs.BookingDTO;
import com.example.bookingsservice.DTOs.BookingWithUsersDTO;
import com.example.bookingsservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);

        return (booking != null) ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingCreateDTO bookingCreateDTO) {
        return bookingService.createBooking(bookingCreateDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingCreateDTO bookingCreateDTO) {
        BookingDTO updated = bookingService.updateBooking(id, bookingCreateDTO);

        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        return bookingService.deleteBooking(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/with-users")
    public List<BookingWithUsersDTO> getAllBookingsWithUsers() {
        return bookingService.getAllBookingsWithUsers();
    }
}
