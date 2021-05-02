package org.sadoke.repository;

import org.sadoke.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("Select u from User u where lower(u.email)=lower(s)")
	public User findByEmail(@Param("s") String email);

	@Query("Select u from User u where lower(u.userId)=lower(s)")
	public User findByUserId(@Param("s") String userId);

}
