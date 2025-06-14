package com.election.fullstack.Entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "input_district_votes")
@Data
public class InputDistrictVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="detailId")
    private Integer detailId;

    private Integer rejectedVote;
    private Integer validVote;
    private Integer allocatedSeats;

    @ManyToOne
    @JoinColumn(name = "district_id",nullable = false)
    private District district;

    // constructors, getters, and setters would be here
    // (handled by @Data from Lombok in this example)
}