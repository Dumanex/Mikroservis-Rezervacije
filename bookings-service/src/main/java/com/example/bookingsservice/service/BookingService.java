package com.example.bookingsservice.service;

import com.example.bookingsservice.DTOs.BookingCreateDTO;
import com.example.bookingsservice.DTOs.BookingDTO;
import com.example.bookingsservice.DTOs.BookingWithUsersDTO;
import com.example.bookingsservice.DTOs.UserDTO;
import com.example.bookingsservice.model.Booking;
import com.example.bookingsservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .orElse(null);
    }

    public BookingDTO createBooking(BookingCreateDTO bookingCreateDTO) {
        Booking booking = new Booking();
        booking.setUserIds(bookingCreateDTO.getUserIds());
        booking.setStatus(bookingCreateDTO.getStatus());
        booking.setBookingDate(bookingCreateDTO.getBookingDate());
        booking.setDetails(bookingCreateDTO.getDetails());

        for (Long userId : bookingCreateDTO.getUserIds()) {
            userService.verifyUserExists(userId);
        }

        booking = bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDTO.class);
    }

    public BookingDTO updateBooking(Long id, BookingCreateDTO bookingCreateDTO) {
        return bookingRepository.findById(id)
                .map(existingBooking -> {
                    existingBooking.setUserIds(bookingCreateDTO.getUserIds());
                    existingBooking.setStatus(bookingCreateDTO.getStatus());
                    existingBooking.setBookingDate(bookingCreateDTO.getBookingDate());
                    existingBooking.setDetails(bookingCreateDTO.getDetails());

                    for (Long userId : bookingCreateDTO.getUserIds()) {
                        userService.verifyUserExists(userId);
                    }

                    Booking updatedBooking = bookingRepository.save(existingBooking);
                    return modelMapper.map(updatedBooking, BookingDTO.class);
                }).orElse(null);
    }

    public boolean deleteBooking(Long id) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    bookingRepository.delete(booking);

                    return true;
                }).orElse(false);
    }

    public List<BookingWithUsersDTO> getAllBookingsWithUsers() {
        return bookingRepository.findAll().stream().map(booking -> {
            List<UserDTO> users = booking.getUserIds()
                    .stream()
                    .map(userService::getUserWithResilience)
                    .collect(Collectors.toList());

            BookingWithUsersDTO dto = new BookingWithUsersDTO();
            dto.setId(booking.getId());
            dto.setUsers(users);
            dto.setStatus(booking.getStatus());
            dto.setBookingDate(booking.getBookingDate());
            dto.setDetails(booking.getDetails());

            return dto;
        }).toList();
    }
}
