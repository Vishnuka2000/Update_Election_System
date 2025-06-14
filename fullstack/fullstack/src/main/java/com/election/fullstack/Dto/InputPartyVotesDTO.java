package com.election.fullstack.Dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputPartyVotesDTO {
    private Integer id;
    private Integer partyTotalVotes;
    private Integer districtId;
    private Integer provinceId;
    private Integer partyId;



}
