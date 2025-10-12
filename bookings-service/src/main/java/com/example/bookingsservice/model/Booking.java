package com.example.bookingsservice.model;

import com.example.bookingsservice.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "booking_users", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "user_id")
    private List<Long> userIds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Date bookingDate;

    @Column(nullable = false)
    private String details;
}
