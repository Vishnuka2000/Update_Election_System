package com.election.fullstack.Service;

import com.election.fullstack.Dto.InputDistrictVotesDTO;
import com.election.fullstack.Entity.*;
import com.election.fullstack.Repository.*;
import com.election.fullstack.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InputDistrictVotesServiceImpl implements InputDistrictVotesService {

    private static final Logger logger = LoggerFactory.getLogger(InputDistrictVotesServiceImpl.class);

    private final InputDistrictVotesRepository repository;
    private final ModelMapper modelMapper;
    private final DistrictRepository districtRepository;

    public InputDistrictVotesServiceImpl(InputDistrictVotesRepository repository,
                                         ModelMapper modelMapper,
                                         DistrictRepository districtRepository) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.districtRepository = districtRepository;
    }

    @Override
    public InputDistrictVotesDTO createDistrictVotes(InputDistrictVotesDTO votesDTO) {
        logger.info("Creating district votes for district ID: {}", votesDTO.getDistrictId());

        // Validate input
        if (votesDTO.getDistrictId() == null) {
            throw new IllegalArgumentException("District ID cannot be null");
        }

        // Get district
        District district = districtRepository.findById(votesDTO.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + votesDTO.getDistrictId()));

        // Check if votes already exist for this district
        Optional<InputDistrictVotes> existingVotes = repository.findByDistrict(district);
        if (existingVotes.isPresent()) {
            throw new IllegalStateException("Votes already exist for district ID: " + votesDTO.getDistrictId());
        }

        // Map DTO to entity
        InputDistrictVotes votes = modelMapper.map(votesDTO, InputDistrictVotes.class);
        votes.setDistrict(district);

        // Save and return
        InputDistrictVotes savedVotes = repository.save(votes);
        logger.info("Successfully created district votes with ID: {}", savedVotes.getDetailId());
        return modelMapper.map(savedVotes, InputDistrictVotesDTO.class);
    }

    @Override
    public InputDistrictVotesDTO updateDistrictVotes(Integer id, InputDistrictVotesDTO votesDTO) {
        logger.info("Updating district votes with ID: {}", id);

        // Find existing votes
        InputDistrictVotes existingVotes = repository.findById(id.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("District votes not found with id: " + id));

        // Validate district if changed
        if (!existingVotes.getDistrict().getDistrictId().equals(votesDTO.getDistrictId())) {
            District newDistrict = districtRepository.findById(votesDTO.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("New district not found with id: " + votesDTO.getDistrictId()));
            existingVotes.setDistrict(newDistrict);
        }

        // Update other fields
        existingVotes.setRejectedVote(votesDTO.getRejectedVote());
        existingVotes.setValidVote(votesDTO.getValidVote());
        existingVotes.setAllocatedSeats(votesDTO.getAllocatedSeats());

        // Save and return
        InputDistrictVotes updatedVotes = repository.save(existingVotes);
        logger.info("Successfully updated district votes with ID: {}", id);
        return modelMapper.map(updatedVotes, InputDistrictVotesDTO.class);
    }

    @Override
    public InputDistrictVotesDTO getDistrictVotesById(Integer id) {
        InputDistrictVotes votes = repository.findById(id.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("District votes not found with id: " + id));
        return modelMapper.map(votes, InputDistrictVotesDTO.class);
    }

    @Override
    public InputDistrictVotesDTO getDistrictVotesWithDetails(Integer districtId) {
        InputDistrictVotes votes = repository.findFirstByDistrictDistrictId(districtId);
        if (votes == null) {
            throw new ResourceNotFoundException("District votes not found for districtId: " + districtId);
        }
        return modelMapper.map(votes, InputDistrictVotesDTO.class);
    }

    @Override
    public List<InputDistrictVotesDTO> getAllDistrictVotes() {
        return repository.findAll().stream()
                .map(votes -> modelMapper.map(votes, InputDistrictVotesDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDistrictVotes(Integer id) {
        if (!repository.existsById(id.longValue())) {
            throw new ResourceNotFoundException("District votes not found with id: " + id);
        }
        repository.deleteById(id.longValue());
    }

    @Override
    public List<InputDistrictVotesDTO> getDistrictVotesByDistrictId(Integer districtId) {
        List<InputDistrictVotes> votesList = repository.findByDistrictDistrictId(districtId);
        if (votesList.isEmpty()) {
            throw new ResourceNotFoundException("No votes found for districtId: " + districtId);
        }
        return votesList.stream()
                .map(votes -> modelMapper.map(votes, InputDistrictVotesDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public InputDistrictVotesDTO calculateAndSaveResults(Integer districtId) {
        InputDistrictVotes votes = repository.findFirstByDistrictDistrictId(districtId);
        if (votes == null) {
            throw new ResourceNotFoundException("District votes not found for districtId: " + districtId);
        }

        // Example calculation logic - modify as needed
        int totalVotes = (votes.getValidVote() != null ? votes.getValidVote() : 0) +
                (votes.getRejectedVote() != null ? votes.getRejectedVote() : 0);

        // Update values
        votes.setValidVote(votes.getValidVote() != null ? votes.getValidVote() : 0);
        votes.setRejectedVote(votes.getRejectedVote() != null ? votes.getRejectedVote() : 0);

        // Save and return
        InputDistrictVotes updatedVotes = repository.save(votes);
        return modelMapper.map(updatedVotes, InputDistrictVotesDTO.class);
    }
}