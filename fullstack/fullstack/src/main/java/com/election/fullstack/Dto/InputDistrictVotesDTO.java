package com.election.fullstack.Dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class InputDistrictVotesDTO {
    private Integer detailId;
    private Integer districtId;
    private String districtName;
    private Integer rejectedVote;
    private Integer validVote;
    private Integer allocatedSeats;
    private List<InputPartyVotesDTO> partyVotes;

    //  Calculates total votes (valid + rejected)
    @JsonIgnore
    public Integer getTotalVotes() {
        return (validVote != null ? validVote : 0) +
                (rejectedVote != null ? rejectedVote : 0);
    }
}