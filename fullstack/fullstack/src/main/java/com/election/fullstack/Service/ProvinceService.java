package com.election.fullstack.Service;

import com.election.fullstack.Dto.ProvinceDTO;
import java.util.List;

public interface ProvinceService {
    ProvinceDTO createProvince(ProvinceDTO provinceDTO);
    ProvinceDTO getProvinceById(Integer id);
    List<ProvinceDTO> getAllProvinces();
    ProvinceDTO updateProvince(Integer id, ProvinceDTO provinceDTO);
    void deleteProvince(Integer id);
}