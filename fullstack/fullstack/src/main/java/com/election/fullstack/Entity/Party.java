package com.election.fullstack.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "party")
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PartyID")
    private Integer partyId;

    @Column(name = "PartyName", nullable = false, length = 30)
    private String partyName;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InputPartyVotes> partyVotes = new ArrayList<>();

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results = new ArrayList<>();


//
//   public List<InputPartyVotes> getPartyVotes() {
//       return partyVotes;
//   }
//
//   public List<Result> getResults() {
//       return results;
//   }
//
//    public Integer getPartyId() {
//        return partyId;
//    }
//
//    public void setPartyId(Integer partyId) {
//        this.partyId = partyId;
//    }
//
//    public String getPartyName() {
//        return partyName;
//    }
//
//    public void setPartyName(String partyName) {
//        this.partyName = partyName;
//    }
//
//    public Party() {}
//
//    public Party(Integer partyId, String partyName, List<InputPartyVotes> partyVotes, List<Result> results) {
//        this.partyId = partyId;
//        this.partyName = partyName;
//        this.partyVotes = partyVotes;
//        this.results = results;
//    }
}