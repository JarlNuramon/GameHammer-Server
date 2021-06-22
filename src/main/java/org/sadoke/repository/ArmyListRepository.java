package org.sadoke.repository;

import org.sadoke.entities.ArmyList;
import org.sadoke.entities.MatchDetails;
import org.sadoke.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmyListRepository extends JpaRepository<ArmyList, Long> {
	
	@Query("Select u from ArmyList u where lower(u.name)=lower(:armyName) and u.owner = :owner")
	public ArmyList getArmyByName(@Param("armyName") String armyName, @Param("owner")  User u);
}
