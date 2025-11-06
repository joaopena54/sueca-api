package com.example.Sueca_api.business.user.entity.repository;

import com.example.Sueca_api.business.user.dto.UserProfileDTO;
import com.example.Sueca_api.business.user.entity.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query(
      "SELECT new com.example.Sueca_api.business.user.dto.UserProfileDTO(u.id, u.username, u.email) FROM User u WHERE u.id = :userId")
  Optional<UserProfileDTO> getUserProfile(String userId);
}
