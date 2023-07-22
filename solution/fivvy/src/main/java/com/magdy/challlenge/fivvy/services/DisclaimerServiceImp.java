package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.models.dtos.DisclaimerResponseDTO;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.repositories.DisclaimerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
public class DisclaimerServiceImp implements DisclaimerService{

    private final DisclaimerRepository disclaimerRepository;
    private final ModelMapper modelMapper;

    public DisclaimerServiceImp(DisclaimerRepository disclaimerRepository, ModelMapper modelMapper) {
        this.disclaimerRepository = disclaimerRepository;
        this.modelMapper = modelMapper;
    }

        @Override
        public DisclaimerResponseDTO create(DisclaimerRequestDTO disclaimerDTO) {
            log.info("Creating a new disclaimer...");
            try {
                Disclaimer disclaimer = modelMapper.map(disclaimerDTO, Disclaimer.class);
                disclaimer.setCreateAt(LocalDateTime.now(ZoneId.of("UTC")));
                disclaimer.setUpdateAt(LocalDateTime.now(ZoneId.of("UTC")));

                Disclaimer disclaimerCreated = disclaimerRepository.save(disclaimer);
                log.info("Disclaimer created successfully: {}", disclaimerCreated.getId());

                return modelMapper.map(disclaimerCreated, DisclaimerResponseDTO.class);
            } catch (Exception e) {
                log.error("Error while creating the disclaimer: {}", e.getMessage(), e);
                throw new RuntimeException("Error while creating the disclaimer", e);
            }
        }
}