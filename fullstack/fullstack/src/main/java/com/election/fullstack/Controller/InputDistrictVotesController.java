package com.election.fullstack.Controller;

import com.election.fullstack.Dto.InputDistrictVotesDTO;
import com.election.fullstack.Service.InputDistrictVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/district-votes")
public class InputDistrictVotesController {

    private final InputDistrictVotesService districtVotesService;

    @Autowired
    public InputDistrictVotesController(InputDistrictVotesService districtVotesService) {
        this.districtVotesService = districtVotesService;
    }

    @PostMapping
    public ResponseEntity<InputDistrictVotesDTO> createDistrictVotes(@RequestBody InputDistrictVotesDTO votesDTO) {
        InputDistrictVotesDTO responseDTO = districtVotesService.createDistrictVotes(votesDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InputDistrictVotesDTO>> getAllDistrictVotes() {
        List<InputDistrictVotesDTO> votes = districtVotesService.getAllDistrictVotes();
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputDistrictVotesDTO> getDistrictVotesById(@PathVariable Integer id) {
        InputDistrictVotesDTO votes = districtVotesService.getDistrictVotesById(id);
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/by-district/{districtId}")
    public ResponseEntity<List<InputDistrictVotesDTO>> getDistrictVotesByDistrictId(
            @PathVariable Integer districtId) {
        List<InputDistrictVotesDTO> votes = districtVotesService.getDistrictVotesByDistrictId(districtId);
        return ResponseEntity.ok(votes);
    }

    @GetMapping("/with-details/{districtId}")
    public ResponseEntity<InputDistrictVotesDTO> getDistrictVotesWithDetails(
            @PathVariable Integer districtId) {
        InputDistrictVotesDTO votes = districtVotesService.getDistrictVotesWithDetails(districtId);
        return ResponseEntity.ok(votes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InputDistrictVotesDTO> updateDistrictVotes(
            @PathVariable Integer id,
            @RequestBody InputDistrictVotesDTO votesDTO) {
        InputDistrictVotesDTO updatedVotes = districtVotesService.updateDistrictVotes(id, votesDTO);
        return ResponseEntity.ok(updatedVotes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrictVotes(@PathVariable Integer id) {
        districtVotesService.deleteDistrictVotes(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculate/{districtId}")
    public ResponseEntity<InputDistrictVotesDTO> calculateAndSaveResults(
            @PathVariable Integer districtId) {
        InputDistrictVotesDTO result = districtVotesService.calculateAndSaveResults(districtId);
        return ResponseEntity.ok(result);
    }
}