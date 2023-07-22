package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerResponseDTO;
import java.util.List;

public interface DisclaimerService {
    DisclaimerResponseDTO create(DisclaimerRequestDTO disclaimer);
    List<DisclaimerResponseDTO> findByFilter(String searchText);
}