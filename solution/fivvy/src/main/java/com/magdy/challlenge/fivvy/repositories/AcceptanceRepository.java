package com.magdy.challlenge.fivvy.repositories;

import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AcceptanceRepository extends MongoRepository<Disclaimer, String> {

}