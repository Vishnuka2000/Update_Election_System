package com.election.fullstack.Controller;


import com.election.fullstack.Dto.ProvinceDTO;
import com.election.fullstack.Service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @PostMapping
    public ResponseEntity<ProvinceDTO> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        ProvinceDTO responseDTO = provinceService.createProvince(provinceDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDTO> getProvinceById(@PathVariable Integer id) {
        ProvinceDTO responseDTO = provinceService.getProvinceById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinceDTO> updateProvince(
            @PathVariable Integer id, @RequestBody ProvinceDTO provinceDTO) {
        ProvinceDTO responseDTO = provinceService.updateProvince(id, provinceDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Integer id) {
        provinceService.deleteProvince(id);
        return ResponseEntity.noContent().build();
    }
}