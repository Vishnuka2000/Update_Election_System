package com.election.fullstack.Service;

import com.election.fullstack.Dto.ResultDTO;
import com.election.fullstack.Entity.*;
import com.election.fullstack.exception.ResourceNotFoundException;
import com.election.fullstack.Repository.*;
import com.election.fullstack.Service.ResultService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final DistrictRepository districtRepository;
    private final PartyRepository partyRepository;
    private final InputPartyVotesRepository partyVotesRepository;
    private final InputDistrictVotesRepository districtVotesRepository;
    private final ModelMapper modelMapper;

    public ResultServiceImpl(ResultRepository resultRepository,
                             DistrictRepository districtRepository,
                             PartyRepository partyRepository,
                             InputPartyVotesRepository partyVotesRepository,
                             InputDistrictVotesRepository districtVotesRepository,
                             ModelMapper modelMapper) {
        this.resultRepository = resultRepository;
        this.districtRepository = districtRepository;
        this.partyRepository = partyRepository;
        this.partyVotesRepository = partyVotesRepository;
        this.districtVotesRepository = districtVotesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultDTO createResult(ResultDTO resultDTO) {
        District district = districtRepository.findById(resultDTO.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + resultDTO.getDistrictId()));

        Party party = partyRepository.findById(resultDTO.getPartyId())
                .orElseThrow(() -> new ResourceNotFoundException("Party not found with id: " + resultDTO.getPartyId()));

        Result result = modelMapper.map(resultDTO, Result.class);
        result.setDistrict(district);
        result.setParty(party);

        Result savedResult = resultRepository.save(result);
        return modelMapper.map(savedResult, ResultDTO.class);
    }

    @Override
    public ResultDTO getResultById(Integer id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found with id: " + id));
        return modelMapper.map(result, ResultDTO.class);
    }

    @Override
    public List<ResultDTO> getAllResults() {
        return resultRepository.findAll().stream()
                .map(result -> modelMapper.map(result, ResultDTO.class))
                .toList();
    }

    @Override
    public List<ResultDTO> getResultsByDistrict(Integer districtId) {
        return resultRepository.findByDistrictDistrictId(districtId).stream()
                .map(result -> modelMapper.map(result, ResultDTO.class))
                .toList();
    }

    @Override
    public List<ResultDTO> getResultsByParty(Integer partyId) {
        return resultRepository.findByPartyPartyId(partyId).stream()
                .map(result -> modelMapper.map(result, ResultDTO.class))
                .toList();
    }

    @Override
    public List<ResultDTO> calculateElectionResults(Integer districtId) {
        // Step 1: Get district and party votes
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + districtId));

        InputDistrictVotes districtVotes = districtVotesRepository.findFirstByDistrictDistrictId(districtId);
        if (districtVotes == null) {
            throw new ResourceNotFoundException("District Votes not found with districtId: " + districtId);
        }

        List<InputPartyVotes> partyVotesList = partyVotesRepository.findByDistrictDistrictId(districtId);

        // Step 2: Calculate total valid votes and 5% threshold
        int totalValidVotes = districtVotes.getValidVote();
        double threshold = totalValidVotes * 0.05; // 5% threshold

        // Step 3: Filter parties that meet the threshold
        List<InputPartyVotes> qualifiedParties = partyVotesList.stream()
                .filter(pv -> pv.getPartyTotalVotes() >= threshold)
                .collect(Collectors.toList());

        // Step 4: Calculate remaining votes after removing unqualified parties
        int qualifiedVotes = qualifiedParties.stream()
                .mapToInt(InputPartyVotes::getPartyTotalVotes)
                .sum();

        int remainingVotes = totalValidVotes - qualifiedVotes;

        // Step 5: Calculate votes per seat
        int allocatedSeats = districtVotes.getAllocatedSeats();
        double votesPerSeat = (double) remainingVotes / allocatedSeats;

        // Step 6: First allocation based on integer division
        Map<Party, Integer> firstAllocation = new HashMap<>();
        Map<Party, Double> remainders = new HashMap<>();

        for (InputPartyVotes pv : qualifiedParties) {
            double seats = pv.getPartyTotalVotes() / votesPerSeat;
            int allocated = (int) Math.floor(seats);
            firstAllocation.put(pv.getParty(), allocated);
            remainders.put(pv.getParty(), seats - allocated);
        }

        // Step 7: Second allocation based on highest remainders
        int totalAllocated = firstAllocation.values().stream().mapToInt(Integer::intValue).sum();
        int remainingSeats = allocatedSeats - totalAllocated;

        remainders.entrySet().stream()
                .sorted(Map.Entry.<Party, Double>comparingByValue().reversed())
                .limit(remainingSeats)
                .forEach(entry -> firstAllocation.merge(entry.getKey(), 1, Integer::sum));

        // Step 8: Save results and calculate percentages
        List<Result> results = new ArrayList<>();
        for (InputPartyVotes pv : qualifiedParties) {
            Party party = pv.getParty();
            int votes = pv.getPartyTotalVotes();
            double percentage = (double) votes / totalValidVotes * 100;

            Result result = new Result();
            result.setDistrict(district);
            result.setParty(party);
            result.setValidVoted(votes);
            result.setFirstAllocatedSeat(firstAllocation.getOrDefault(party, 0));
            result.setSecondAllocatedSeat(0); // Can be calculated if needed
            result.setVotePercentage(percentage);

            results.add(result);
        }

        // Clear existing results and save new ones
        resultRepository.deleteByDistrictDistrictId(districtId);
        List<Result> savedResults = resultRepository.saveAll(results);

        return savedResults.stream()
                .map(result -> {
                    ResultDTO dto = modelMapper.map(result, ResultDTO.class);
                    dto.setPartyName(result.getParty().getPartyName());
                    dto.setDistrictName(result.getDistrict().getDistrictName());
                    return dto;
                })
                .toList();
    }
}
