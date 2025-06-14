package com.election.fullstack.Controller;



import com.election.fullstack.Dto.PartyDTO;
import com.election.fullstack.Service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping
    public ResponseEntity<PartyDTO> createParty(@RequestBody PartyDTO partyDTO) {
        PartyDTO createdParty = partyService.createParty(partyDTO);
        return new ResponseEntity<>(createdParty, HttpStatus.CREATED);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<PartyDTO> getPartyById(@PathVariable Integer id) {
    //     return ResponseEntity.ok(partyService.getPartyById(id));
    // }

  @GetMapping
   public ResponseEntity<List<PartyDTO>> getAllParties() {
      return ResponseEntity.ok(partyService.getAllParties());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PartyDTO>> searchPartiesByName(
            @RequestParam String name) {
        return ResponseEntity.ok(partyService.searchPartiesByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartyDTO> updateParty(
            @PathVariable Integer id,
            @RequestBody PartyDTO partyDTO) {
        return ResponseEntity.ok(partyService.updateParty(id, partyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Integer id) {
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }
}