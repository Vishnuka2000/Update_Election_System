package com.election.fullstack.Service;

import com.election.fullstack.Dto.DistrictDTO;
import com.election.fullstack.Dto.ProvinceDTO;
import com.election.fullstack.Entity.District;
import com.election.fullstack.Entity.Province;
import com.election.fullstack.exception.ResourceNotFoundException;
import com.election.fullstack.Repository.DistrictRepository;
import com.election.fullstack.Repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public DistrictDTO createDistrict(DistrictDTO DistrictDTO) {
        Province province = provinceRepository.findById(DistrictDTO.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found with id: " + DistrictDTO.getProvinceId()));

        District district = new District();
        district.setDistrictName(DistrictDTO.getDistrictName());
        district.setProvince(province);

        District savedDistrict = districtRepository.save(district);
        return new DistrictDTO(
                savedDistrict.getDistrictId(),
                savedDistrict.getDistrictName(),
                province.getProvinceId(),
                province.getProvinceName()
        );
    }

    @Override
    public List<DistrictDTO> getAllDistricts() {
        return districtRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DistrictDTO updateDistrict(Integer id, DistrictDTO districtDTO) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + id));

        Province province = provinceRepository.findById(districtDTO.getProvinceId())
                .orElseThrow(() -> new ResourceNotFoundException("Province not found with id: " + districtDTO.getProvinceId()));

        district.setDistrictName(districtDTO.getDistrictName());
        district.setProvince(province);

        District updatedDistrict = districtRepository.save(district);
        return convertToResponseDTO(updatedDistrict);
    }

    @Override
    public void deleteDistrict(Integer id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + id));
        districtRepository.delete(district);
    }

    @Override
    public DistrictDTO getDistrictById(Integer id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id: " + id));
        return convertToResponseDTO(district);
    }

    private DistrictDTO convertToResponseDTO(District district) {
        return new DistrictDTO(
                district.getDistrictId(),
                district.getDistrictName(),
                district.getProvince() != null ? district.getProvince().getProvinceId() : null,
                district.getProvince() != null ? district.getProvince().getProvinceName() : null
        );
    }

    private ProvinceDTO convertToResponseDTO(Province province) {
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