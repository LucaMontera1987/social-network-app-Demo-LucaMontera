package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.id =:id")
	Optional<User> findUserById(@Param("id") Long id);
	
	@Query("SELECT u FROM User u WHERE u.email =:param OR u.username =:param")
	Optional<User> findUserByEmailOrUsername(@Param("param") String email);
	
	
    Optional <User> findByEmail(@Param("username") String username);

	Optional<User>findByprofiloUser_id(@Param("idProfile")Long idProfile);
	
	@Query("SELECT u FROM User u WHERE LOWER (u.username) LIKE LOWER(CONCAT('%', :termine, '%'))")
	List<User> getAllUser(@Param("termine") String termine);
	

}
