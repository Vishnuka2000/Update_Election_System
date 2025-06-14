package com.election.fullstack.Service;


import com.election.fullstack.Dto.*;
import com.election.fullstack.Entity.Party;
import com.election.fullstack.exception.ResourceNotFoundException;
import com.election.fullstack.Repository.PartyRepository;
import com.election.fullstack.Service.PartyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartyServiceImpl(PartyRepository partyRepository, ModelMapper modelMapper) {
        this.partyRepository = partyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartyDTO createParty(PartyDTO partyDTO) {
        Party party = modelMapper.map(partyDTO, Party.class);
        Party savedParty = partyRepository.save(party);
        return modelMapper.map(savedParty, PartyDTO.class);
    }

    @Override
    public PartyDTO getPartyById(Integer id) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + id));
        return modelMapper.map(party, PartyDTO.class);
    }

    @Override
    public List<PartyDTO> getAllParties() {
        return partyRepository.findAll().stream()
                .map(party -> modelMapper.map(party, PartyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartyDTO> searchPartiesByName(String name) {
        return partyRepository.findByPartyNameContainingIgnoreCase(name).stream()
                .map(party -> modelMapper.map(party, PartyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PartyDTO updateParty(Integer id, PartyDTO partyDTO) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + id));

        party.setPartyName(partyDTO.getPartyName());
        Party updatedParty = partyRepository.save(party);
        return modelMapper.map(updatedParty, PartyDTO.class);
    }

    @Override
    public void deleteParty(Integer id) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + id));
        partyRepository.delete(party);
    }
}