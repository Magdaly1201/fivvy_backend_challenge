package com.magdy.challlenge.fivvy.repositories;

import com.magdy.challlenge.fivvy.models.entities.Acceptance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AcceptanceRepository extends MongoRepository<Acceptance, String> {

    List<Acceptance> findAllByUserId(String userId);
}