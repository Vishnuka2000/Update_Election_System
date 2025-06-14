package com.election.fullstack.Controller;

import com.election.fullstack.Dto.InputPartyVotesDTO;
import com.election.fullstack.Service.InputPartyVotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/party-votes")
public class InputPartyVotesController {

    private final InputPartyVotesService inputPartyVotesService;

    @Autowired
    public InputPartyVotesController(InputPartyVotesService votesService) {
        this.inputPartyVotesService = votesService;
    }

    @PostMapping
    public ResponseEntity<InputPartyVotesDTO> createVoteRecord(@RequestBody InputPartyVotesDTO votesDTO) {
        InputPartyVotesDTO createdRecord = inputPartyVotesService.createVoteRecord(votesDTO);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputPartyVotesDTO> getVoteRecordById(@PathVariable Integer id) {
        return ResponseEntity.ok(inputPartyVotesService.getVoteRecordById(id));
    }

    @GetMapping
    public ResponseEntity<List<InputPartyVotesDTO>> getAllVoteRecords() {
        return ResponseEntity.ok(inputPartyVotesService.getAllVoteRecords());
    }

    @GetMapping("/by-district/{districtId}")
    public ResponseEntity<List<InputPartyVotesDTO>> getVotesByDistrict(@PathVariable Integer districtId) {
        return ResponseEntity.ok(inputPartyVotesService.getVotesByDistrict(districtId));
    }

    @GetMapping("/by-party/{partyId}")
    public ResponseEntity<List<InputPartyVotesDTO>> getVotesByParty(@PathVariable Integer partyId) {
        return ResponseEntity.ok(inputPartyVotesService.getVotesByParty(partyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InputPartyVotesDTO> updateVoteRecord(
            @PathVariable Integer id,
            @RequestBody InputPartyVotesDTO votesDTO) {
        return ResponseEntity.ok(inputPartyVotesService.updateVoteRecord(id, votesDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoteRecord(@PathVariable Integer id) {
        inputPartyVotesService.deleteVoteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
