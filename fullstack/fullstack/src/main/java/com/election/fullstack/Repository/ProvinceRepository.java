package com.election.fullstack.Repository;



import com.election.fullstack.Entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    boolean existsByProvinceName(String provinceName);
    boolean existsByProvinceNameAndProvinceIdNot(String provinceName, Integer provinceId);
}