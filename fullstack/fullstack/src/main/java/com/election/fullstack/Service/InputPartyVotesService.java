package com.election.fullstack.Service;

import com.election.fullstack.Dto.InputPartyVotesDTO;
import java.util.List;

public interface InputPartyVotesService {
    InputPartyVotesDTO createVoteRecord(InputPartyVotesDTO votesDTO);
    InputPartyVotesDTO getVoteRecordById(Integer id);
    List<InputPartyVotesDTO> getAllVoteRecords();
    List<InputPartyVotesDTO> getVotesByDistrict(Integer districtId);
    List<InputPartyVotesDTO> getVotesByParty(Integer partyId);
    InputPartyVotesDTO updateVoteRecord(Integer id, InputPartyVotesDTO votesDTO);
    void deleteVoteRecord(Integer id);
}
