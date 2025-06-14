package com.election.fullstack.Service;

import com.election.fullstack.Dto.ProvinceDTO;
import com.election.fullstack.Entity.District;
import com.election.fullstack.Entity.Province;
import com.election.fullstack.exception.ResourceNotFoundException;
import com.election.fullstack.exception.DuplicateResourceException;
import com.election.fullstack.Repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Override
    public ProvinceDTO createProvince(ProvinceDTO provinceDTO) {
        if (provinceRepository.existsByProvinceName(provinceDTO.getProvinceName())) {
            throw new DuplicateResourceException("Province '" + provinceDTO.getProvinceName() + "' exists");
        }

        Province province = new Province();
        province.setProvinceName(provinceDTO.getProvinceName());

        return toDto(provinceRepository.save(province));
    }

    @Override
    public ProvinceDTO getProvinceById(Integer id) {
        return provinceRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found: " + id));
    }

    @Override
    public List<ProvinceDTO> getAllProvinces() {
        return provinceRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProvinceDTO updateProvince(Integer id, ProvinceDTO provinceDTO) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found: " + id));

        if (provinceRepository.existsByProvinceNameAndProvinceIdNot(provinceDTO.getProvinceName(), id)) {
            throw new DuplicateResourceException("Province '" + provinceDTO.getProvinceName() + "' exists");
        }

        province.setProvinceName(provinceDTO.getProvinceName());
        return toDto(provinceRepository.save(province));
    }

    @Override
    public void deleteProvince(Integer id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found: " + id));

        if (!province.getDistricts().isEmpty()) {
            throw new IllegalStateException("Cannot delete province with districts: " + id);
        }

        provinceRepository.delete(province);
    }

    private ProvinceDTO toDto(Province province) {
        List<String> districtNames = province.getDistricts().stream()
                .map(District::getDistrictName)
                .collect(Collectors.toList());

        return new ProvinceDTO(
                province.getProvinceId(),
                province.getProvinceName(),
                districtNames
        );
    }
}