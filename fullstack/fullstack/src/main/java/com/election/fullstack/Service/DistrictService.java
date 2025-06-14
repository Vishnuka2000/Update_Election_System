package com.election.fullstack.Service;

import com.election.fullstack.Dto.DistrictDTO;
import java.util.List;

public interface DistrictService {
    DistrictDTO createDistrict(DistrictDTO districtDTO);
    DistrictDTO getDistrictById(Integer id);
    List<DistrictDTO> getAllDistricts();
    DistrictDTO updateDistrict(Integer id, DistrictDTO districtDTO);
    void deleteDistrict(Integer id);
}
