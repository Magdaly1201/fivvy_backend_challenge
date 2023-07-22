package com.magdy.challlenge.fivvy.repositories;

import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisclaimerRepository extends MongoRepository<Disclaimer, String> {

    @Query("{ $or: [ { 'text': { $regex: ?0, $options: 'i' } }, { 'text': '' } ] }")
    List<Disclaimer> findAllByText(String searchText);

}