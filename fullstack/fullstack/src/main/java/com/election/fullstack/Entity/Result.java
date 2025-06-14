package com.election.fullstack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResultID")
    private Integer resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DistrictID", nullable = false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartyID", nullable = false)
    private Party party;

    @Column(name = "ValidVoted")
    private Integer validVoted;

    @Column(name = "FirstAllocatedSeat")
    private Integer firstAllocatedSeat;

    @Column(name = "SecondAllocatedSeat")
    private Integer secondAllocatedSeat;

    @Column(name = "VotePercentage")
    private Double votePercentage;


}
