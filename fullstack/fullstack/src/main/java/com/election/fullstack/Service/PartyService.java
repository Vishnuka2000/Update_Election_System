package com.election.fullstack.Service;



import com.election.fullstack.Dto.PartyDTO;
import java.util.List;

public interface PartyService {
    PartyDTO createParty(PartyDTO partyDTO);
    PartyDTO getPartyById(Integer id);
    List<PartyDTO> getAllParties();
    List<PartyDTO> searchPartiesByName(String name);
    PartyDTO updateParty(Integer id, PartyDTO partyDTO);
    void deleteParty(Integer id);
}
