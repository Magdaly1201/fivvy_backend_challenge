package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerResponseDTO;

public interface DisclaimerService {
    DisclaimerResponseDTO create(DisclaimerRequestDTO disclaimer);
}