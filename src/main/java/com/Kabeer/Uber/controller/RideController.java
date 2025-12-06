package com.Kabeer.Uber.controller;

import jakarta.validation.Valid;
import com.Kabeer.Uber.dto.CreateRideRequest;
import com.Kabeer.Uber.model.Ride;
import com.Kabeer.Uber.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // USER: create ride
    @PostMapping("/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest request) {
        Ride ride = rideService.createRide(request);
        return ResponseEntity.ok(ride);
    }

    // USER: get own rides
    @GetMapping("/user/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Ride>> getUserRides() {
        return ResponseEntity.ok(rideService.getUserRides());
    }

    // DRIVER: pending ride requests
    @GetMapping("/driver/rides/requests")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<Ride>> getPendingRides() {
        return ResponseEntity.ok(rideService.getPendingRidesForDriver());
    }

    // DRIVER: accept ride
    @PostMapping("/driver/rides/{id}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Ride> acceptRide(@PathVariable("id") String rideId) {
        return ResponseEntity.ok(rideService.acceptRide(rideId));
    }

    // USER or DRIVER: complete ride
    @PostMapping("/rides/{id}/complete")
    @PreAuthorize("hasAnyRole('USER','DRIVER')")
    public ResponseEntity<Ride> completeRide(@PathVariable("id") String rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }
}

