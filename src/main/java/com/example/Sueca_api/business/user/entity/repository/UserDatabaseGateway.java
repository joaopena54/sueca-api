package com.example.Sueca_api.business.user.entity.repository;

import com.example.Sueca_api.business.user.dto.UserProfileDTO;
import com.example.Sueca_api.business.user.entity.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDatabaseGateway {

  private final UserRepository userRepository;

  public Optional<UserProfileDTO> getUserProfile(String userId) {

    return userRepository.getUserProfile(userId);
  }

  public UserProfileDTO create(String id, String username, String email) {

    User user = new User(id, username, email);
    userRepository.save(user);
    return new UserProfileDTO(id, username, email);
  }
}
