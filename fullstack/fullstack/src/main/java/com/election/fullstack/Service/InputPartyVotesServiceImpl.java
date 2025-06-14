package com.election.fullstack.Service;

import com.election.fullstack.Dto.InputPartyVotesDTO;
import com.election.fullstack.Entity.*;
import com.election.fullstack.exception.ResourceNotFoundException;
import com.election.fullstack.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InputPartyVotesServiceImpl implements InputPartyVotesService {

    private final InputPartyVotesRepository inputPartyVotesRepository;
    private final PartyRepository partyRepository;
    private final InputDistrictVotesRepository inputDistrictVotesRepository;
    private final ProvinceRepository provinceRepository;

    // Manually added constructor for dependency injection
    public InputPartyVotesServiceImpl(InputPartyVotesRepository inputPartyVotesRepository,
                                      PartyRepository partyRepository,
                                      InputDistrictVotesRepository inputDistrictVotesRepository,
                                      ProvinceRepository provinceRepository) {
        this.inputPartyVotesRepository = inputPartyVotesRepository;
        this.partyRepository = partyRepository;
        this.inputDistrictVotesRepository = inputDistrictVotesRepository;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public InputPartyVotesDTO createVoteRecord(InputPartyVotesDTO InputPartyVotesDTO) {
        // Validate and get related entities
        InputDistrictVotes districtEntity = inputDistrictVotesRepository.findFirstByDistrictDistrictId(InputPartyVotesDTO.getDistrictId());
        if (districtEntity == null) {
            throw new ResourceNotFoundException("District votes not found for districtId: " + InputPartyVotesDTO.getDistrictId());
        }
        District district = districtEntity.getDistrict();

        Province province = provinceRepository.findById(InputPartyVotesDTO.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found with id: " + InputPartyVotesDTO.getProvinceId()));

        Party party = partyRepository.findById(InputPartyVotesDTO.getPartyId())
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + InputPartyVotesDTO.getPartyId()));

        // Create and save new vote record
        InputPartyVotes voteRecord = new InputPartyVotes();
        voteRecord.setPartyTotalVotes(InputPartyVotesDTO.getPartyTotalVotes());
        voteRecord.setDistrict(district);
        voteRecord.setProvince(province);
        voteRecord.setParty(party);

        InputPartyVotes savedRecord = inputPartyVotesRepository.save(voteRecord);
        return toDto(savedRecord);
    }

    @Override
    public InputPartyVotesDTO getVoteRecordById(Integer id) {
        return inputPartyVotesRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Vote record not found with id: " + id));
    }

    @Override
    public List<InputPartyVotesDTO> getAllVoteRecords() {
        return inputPartyVotesRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InputPartyVotesDTO> getVotesByDistrict(Integer districtId) {
        return inputPartyVotesRepository.findByDistrictDistrictId(districtId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InputPartyVotesDTO> getVotesByParty(Integer partyId) {
        return inputPartyVotesRepository.findByPartyPartyId(partyId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InputPartyVotesDTO updateVoteRecord(Integer id, InputPartyVotesDTO votesDTO) {
        InputPartyVotes existingRecord = inputPartyVotesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote record not found with id: " + id));

        // Update district if changed
        if (!existingRecord.getDistrict().getDistrictId().equals(votesDTO.getDistrictId())) {
            InputDistrictVotes districtEntity = inputDistrictVotesRepository.findFirstByDistrictDistrictId(votesDTO.getDistrictId());
            if (districtEntity == null) {
                throw new ResourceNotFoundException("District votes not found for districtId: " + votesDTO.getDistrictId());
            }
            District district = districtEntity.getDistrict();
            existingRecord.setDistrict(district);
        }

        // Update province if changed
        if (!existingRecord.getProvince().getProvinceId().equals(votesDTO.getProvinceId())) {
            Province province = provinceRepository.findById(votesDTO.getProvinceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Province not found with id: " + votesDTO.getProvinceId()));
            existingRecord.setProvince(province);
        }

        // Update party if changed
        if (!existingRecord.getParty().getPartyId().equals(votesDTO.getPartyId())) {
            Party party = partyRepository.findById(votesDTO.getPartyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + votesDTO.getPartyId()));
            existingRecord.setParty(party);
        }

        // Update votes
        existingRecord.setPartyTotalVotes(votesDTO.getPartyTotalVotes());

        return toDto(inputPartyVotesRepository.save(existingRecord));
    }

    @Override
    public void deleteVoteRecord(Integer id) {
        if (!inputPartyVotesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vote record not found with id: " + id);
        }
        inputPartyVotesRepository.deleteById(id);
    }

    private InputPartyVotesDTO toDto(InputPartyVotes entity) {
        InputPartyVotesDTO dto = new InputPartyVotesDTO();
        dto.setId(entity.getId());
        dto.setPartyTotalVotes(entity.getPartyTotalVotes());
        dto.setDistrictId(entity.getDistrict().getDistrictId());
        dto.setProvinceId(entity.getProvince().getProvinceId());
        dto.setPartyId(entity.getParty().getPartyId());
        return dto;
    }
}