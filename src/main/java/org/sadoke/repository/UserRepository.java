package org.sadoke.repository;

import org.sadoke.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("Select Count(u) from User u where lower(u.email)=lower(:email)")
	public int emailExists(@Param("email") String email);

	@Query("Select Count(u) from User u where lower(u.userId)=lower(:id)")
	public int userExists(@Param("id") String id);

}
