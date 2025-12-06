package com.Kabeer.Uber.service;

import com.Kabeer.Uber.dto.CreateRideRequest;
import com.Kabeer.Uber.exception.BadRequestException;
import com.Kabeer.Uber.exception.NotFoundException;
import com.Kabeer.Uber.model.Ride;
import com.Kabeer.Uber.model.User;
import com.Kabeer.Uber.repository.RideRepository;
import com.Kabeer.Uber.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public RideService(RideRepository rideRepository,
                       UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Logged in user not found"));
    }

    public Ride createRide(CreateRideRequest request) {
        User user = getLoggedInUser();
        if (!"ROLE_USER".equals(user.getRole())) {
            throw new BadRequestException("Only USER can request rides");
        }

        Ride ride = new Ride();
        ride.setUserId(user.getId());
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus("REQUESTED");
        ride.setCreatedAt(new Date());

        return rideRepository.save(ride);
    }

    public List<Ride> getUserRides() {
        User user = getLoggedInUser();
        return rideRepository.findByUserId(user.getId());
    }

    public List<Ride> getPendingRidesForDriver() {
        User driver = getLoggedInUser();
        if (!"ROLE_DRIVER".equals(driver.getRole())) {
            throw new BadRequestException("Only DRIVER can view pending rides");
        }
        return rideRepository.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId) {
        User driver = getLoggedInUser();
        if (!"ROLE_DRIVER".equals(driver.getRole())) {
            throw new BadRequestException("Only DRIVER can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED state");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus("ACCEPTED");
        return rideRepository.save(ride);
    }

    public Ride completeRide(String rideId) {
        User user = getLoggedInUser();

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in ACCEPTED state");
        }

        // Assignment says USER or DRIVER can complete
        if (!user.getId().equals(ride.getUserId()) &&
                (ride.getDriverId() == null || !user.getId().equals(ride.getDriverId()))) {
            throw new BadRequestException("You are not allowed to complete this ride");
        }

        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }
}

