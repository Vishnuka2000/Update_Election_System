package com.election.fullstack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DistrictID")
    private Integer districtId;

    @Column(name = "DistrictName", nullable = false, length = 30)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "ProvinceID")
    private Province province;

    @OneToOne(mappedBy = "district", cascade = CascadeType.ALL)
    private InputDistrictVotes inputDistrictVotes;
}