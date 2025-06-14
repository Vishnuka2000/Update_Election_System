package com.election.fullstack.Dto;

import lombok.*;

import java.util.List;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {
    private Integer provinceId;
    private String provinceName;
    private List<String> districtNames;
}