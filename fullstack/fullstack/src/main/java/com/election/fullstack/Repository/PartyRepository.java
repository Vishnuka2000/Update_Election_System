package com.election.fullstack.Repository;


import com.election.fullstack.Entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Integer> {
    List<Party> findByPartyNameContainingIgnoreCase(String partyName);
}