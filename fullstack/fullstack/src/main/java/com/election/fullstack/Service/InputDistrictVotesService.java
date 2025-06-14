package com.election.fullstack.Service;



import com.election.fullstack.Dto.InputDistrictVotesDTO;
import java.util.List;

public interface InputDistrictVotesService {
    InputDistrictVotesDTO createDistrictVotes(InputDistrictVotesDTO votesDTO);
    InputDistrictVotesDTO getDistrictVotesById(Integer id);
    InputDistrictVotesDTO getDistrictVotesWithDetails(Integer districtId);
    List<InputDistrictVotesDTO> getAllDistrictVotes();
    InputDistrictVotesDTO updateDistrictVotes(Integer id, InputDistrictVotesDTO votesDTO);
    void deleteDistrictVotes(Integer id);

    List<InputDistrictVotesDTO> getDistrictVotesByDistrictId(Integer districtId);

    InputDistrictVotesDTO calculateAndSaveResults(Integer districtId);
}