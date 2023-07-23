package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.exceptions.DisclaimerNotFoundException;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceRequestDTO;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceResponseDTO;

import java.util.List;

public interface AcceptanceService {
    AcceptanceResponseDTO create(String disclaimerId, AcceptanceRequestDTO acceptanceRequestDTO) throws DisclaimerNotFoundException;
    List<AcceptanceResponseDTO> getAllAcceptanceByUserId(String userId);
}