package com.example.Sueca_api.business.user.entity.repository;

import com.example.Sueca_api.business.user.dto.UserProfileDTO;
import com.example.Sueca_api.business.user.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.example.Sueca_api.business.user.dto.UserProfileDTO(u.id, u.username, u.email) from User u where u.id = :userId")
    Optional<UserProfileDTO> getUserProfile(String userId);

}
