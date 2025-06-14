package com.election.fullstack.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {
    private Integer resultId;
    private Integer districtId;
    private Integer partyId;
    private Integer validVoted;
    private Integer firstAllocatedSeat;
    private Integer secondAllocatedSeat;
    private Double votePercentage;
    private String partyName;  // For display purposes
    private String districtName; // For display purposes


}