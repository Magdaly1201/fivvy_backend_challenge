package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.exceptions.DisclaimerNotFoundException;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceRequestDTO;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceResponseDTO;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerResponseDTO;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;

import java.util.List;

public interface DisclaimerService {
    DisclaimerResponseDTO create(DisclaimerRequestDTO disclaimer);
    List<DisclaimerResponseDTO> findByFilter(String searchText);
    DisclaimerResponseDTO getById(String id) throws DisclaimerNotFoundException;
    void deleteById(String id);
    DisclaimerResponseDTO update(String id,  DisclaimerRequestDTO disclaimerDTO) throws DisclaimerNotFoundException;
    Disclaimer getEntityById(String id) throws DisclaimerNotFoundException;

}