package com.election.fullstack.Controller;



import com.election.fullstack.Dto.ResultDTO;
import com.election.fullstack.Service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<ResultDTO> createResult(@RequestBody ResultDTO resultDTO) {
        ResultDTO createdResult = resultService.createResult(resultDTO);
        return new ResponseEntity<>(createdResult, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAllResults() {
        return ResponseEntity.ok(resultService.getAllResults());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getResultById(@PathVariable Integer id) {
        return ResponseEntity.ok(resultService.getResultById(id));
    }

    @GetMapping("/by-district/{districtId}")
    public ResponseEntity<List<ResultDTO>> getResultsByDistrict(
            @PathVariable Integer districtId) {
        return ResponseEntity.ok(resultService.getResultsByDistrict(districtId));
    }

    @GetMapping("/by-party/{partyId}")
    public ResponseEntity<List<ResultDTO>> getResultsByParty(
            @PathVariable Integer partyId) {
        return ResponseEntity.ok(resultService.getResultsByParty(partyId));
    }

    @PostMapping("/calculate/{districtId}")
    public ResponseEntity<List<ResultDTO>> calculateResults(
            @PathVariable Integer districtId) {
        return ResponseEntity.ok(resultService.calculateElectionResults(districtId));
    }
}
