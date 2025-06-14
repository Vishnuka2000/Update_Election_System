package com.election.fullstack.Repository;

import com.election.fullstack.Entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByDistrictDistrictId(Integer districtId);
    List<Result> findByPartyPartyId(Integer partyId);

    @Query("SELECT r FROM Result r WHERE r.district.districtId = :districtId ORDER BY r.firstAllocatedSeat DESC, r.secondAllocatedSeat DESC")
    List<Result> findResultsByDistrictOrdered(@Param("districtId") Integer districtId);

    void deleteByDistrictDistrictId(Integer districtId);
}
