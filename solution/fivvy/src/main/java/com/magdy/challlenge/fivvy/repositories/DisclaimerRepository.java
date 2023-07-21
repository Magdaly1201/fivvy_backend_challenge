package com.magdy.challlenge.fivvy.repositories;

import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisclaimerRepository extends MongoRepository<Disclaimer, String> {

}