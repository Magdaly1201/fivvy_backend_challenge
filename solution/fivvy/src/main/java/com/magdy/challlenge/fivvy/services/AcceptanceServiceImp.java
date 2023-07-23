package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.exceptions.DisclaimerNotFoundException;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceRequestDTO;
import com.magdy.challlenge.fivvy.models.dtos.AcceptanceResponseDTO;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerResponseDTO;
import com.magdy.challlenge.fivvy.models.entities.Acceptance;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import com.magdy.challlenge.fivvy.repositories.AcceptanceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AcceptanceServiceImp implements AcceptanceService {

    private final DisclaimerService disclaimerService;
    private final AcceptanceRepository acceptanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public AcceptanceResponseDTO create(String disclaimerId, AcceptanceRequestDTO acceptanceRequestDTO) throws DisclaimerNotFoundException {
        DisclaimerResponseDTO disclaimerResponseDTO = disclaimerService.getById(disclaimerId);
        log.info("add acceptance to the disclaimer {}", disclaimerResponseDTO.getId());

        Acceptance acceptance = modelMapper.map(acceptanceRequestDTO, Acceptance.class);
        acceptance.setDisclaimerId(disclaimerResponseDTO.getId());
        acceptance.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")));

        acceptance = acceptanceRepository.save(acceptance);
        log.info("added acceptance id {} userId:{} , disclaimerId:{} ", acceptance.getId(), acceptance.getUserId(), acceptance.getDisclaimerId());

        return modelMapper.map(acceptance, AcceptanceResponseDTO.class);
    }

    @Override
    public List<AcceptanceResponseDTO> getAllAcceptanceByUserId(String userId) {
        log.info("get list all acceptance by userId {}", userId);

        List<Acceptance> acceptances = acceptanceRepository.findAllByUserId(userId);
        return acceptances.stream()
                .map(disclaimerEntity -> modelMapper.map(disclaimerEntity, AcceptanceResponseDTO.class))
                .collect(Collectors.toList());
    }
}