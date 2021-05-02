package org.sadoke.repository;

import org.sadoke.entities.MatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends JpaRepository<MatchDetails, Long> {

}
