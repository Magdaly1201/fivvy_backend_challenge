package com.magdy.challlenge.fivvy.services;

import com.magdy.challlenge.fivvy.exceptions.DisclaimerNotFoundException;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerResponseDTO;
import com.magdy.challlenge.fivvy.models.entities.Disclaimer;
import com.magdy.challlenge.fivvy.models.dtos.DisclaimerRequestDTO;
import com.magdy.challlenge.fivvy.repositories.DisclaimerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<DisclaimerResponseDTO> findByFilter(String searchText) {
        List<Disclaimer> filteredDisclaimers = disclaimerRepository.findAllByText(searchText);
        return filteredDisclaimers.stream()
                .map(disclaimerEntity -> modelMapper.map(disclaimerEntity, DisclaimerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DisclaimerResponseDTO getById(String id) throws DisclaimerNotFoundException {
        Disclaimer disclaimer = disclaimerRepository.findById(id).orElseThrow(DisclaimerNotFoundException::new);
        return  modelMapper.map(disclaimer, DisclaimerResponseDTO.class);
    }
}
