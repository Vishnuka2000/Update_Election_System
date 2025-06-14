package com.election.fullstack.Repository;

import com.election.fullstack.Entity.District;
import com.election.fullstack.Entity.InputDistrictVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InputDistrictVotesRepository extends JpaRepository<InputDistrictVotes, Long> {
    List<InputDistrictVotes> findByDistrictDistrictId(Integer districtId);

    Optional<InputDistrictVotes> findByDistrict(District district);

    default InputDistrictVotes findFirstByDistrictDistrictId(Integer districtId) {
        List<InputDistrictVotes> results = findByDistrictDistrictId(districtId);
        return results.isEmpty() ? null : results.get(0);
    }
}