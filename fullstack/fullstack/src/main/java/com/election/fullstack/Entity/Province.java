package com.election.fullstack.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProvinceID")
    private Integer provinceId;

    @Column(name = "ProvinceName", nullable = false, length = 30)
    private String provinceName;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<District> districts = new ArrayList<>();


}

//   public List<District> getDistricts() {
//       return districts;
//   }
//
//   public void setDistricts(List<District> districts) {
//       this.districts = districts;
//   }
//
//   // Helper methods for bidirectional relationship
//   public void addDistrict(District district) {
//       districts.add(district);
//       district.setProvince(this);
//   }
//
//   public void removeDistrict(District district) {
//       districts.remove(district);
//       district.setProvince(null);
//   }
//
//   public Integer getProvinceId() {
//       return provinceId;
//   }
//   public String getProvinceName() {
//       return provinceName;
//   }
//
//   public void setProvinceName(String provinceName) {
//       this.provinceName = provinceName;
//   }

