package com.election.fullstack.Service;



import com.election.fullstack.Dto.ResultDTO;
import java.util.List;

public interface ResultService {
    ResultDTO createResult(ResultDTO resultDTO);
    ResultDTO getResultById(Integer id);
    List<ResultDTO> getAllResults();
    List<ResultDTO> getResultsByDistrict(Integer districtId);
    List<ResultDTO> getResultsByParty(Integer partyId);
    List<ResultDTO> calculateElectionResults(Integer districtId);
}
