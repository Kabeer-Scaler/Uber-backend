package com.Kabeer.Uber.repository;

import com.Kabeer.Uber.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findByUserId(String userId);
    List<Ride> findByStatus(String status);
}

