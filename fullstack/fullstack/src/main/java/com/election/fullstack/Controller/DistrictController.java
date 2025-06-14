package com.election.fullstack.Controller;


import com.election.fullstack.Dto.DistrictDTO;
import com.election.fullstack.Service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @PostMapping
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictDTO districtDTO) {
        DistrictDTO responseDTO = districtService.createDistrict(districtDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

   @GetMapping
  public ResponseEntity<List<DistrictDTO>> getAllDistricts() {
        List<DistrictDTO> districts = districtService.getAllDistricts();
      return ResponseEntity.ok(districts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistrictDTO> updateDistrict(
            @PathVariable Integer id, @RequestBody DistrictDTO districtDTO) {
        DistrictDTO responseDTO = districtService.updateDistrict(id, districtDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Integer id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.noContent().build();
    }

     @GetMapping("/{id}")
     public ResponseEntity<DistrictDTO> getDistrictById(@PathVariable Integer id) {
         DistrictDTO responseDTO = districtService.getDistrictById(id);
         return ResponseEntity.ok(responseDTO);
     }

}