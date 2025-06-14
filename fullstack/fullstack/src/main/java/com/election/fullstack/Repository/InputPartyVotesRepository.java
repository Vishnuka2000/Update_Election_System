package com.election.fullstack.Repository;

import com.election.fullstack.Entity.InputPartyVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InputPartyVotesRepository extends JpaRepository<InputPartyVotes, Integer> {
    List<InputPartyVotes> findByDistrictDistrictId(Integer districtId);
    List<InputPartyVotes> findByPartyPartyId(Integer partyId);

//    @Query("SELECT ipv FROM InputPartyVotes ipv WHERE ipv.district.districtId = :districtId AND ipv.party.partyId = :partyId")
//    List<InputPartyVotes> findByDistrictAndParty(
//            @Param("districtId") Integer districtId,
//            @Param("partyId") Integer partyId);
}
