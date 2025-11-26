package com.example.Sueca_api.business.user.control;

import com.example.Sueca_api.business.player_statistic.entity.repository.PlayerStatisticsGateway;
import com.example.Sueca_api.business.user.dto.UserProfileDTO;
import com.example.Sueca_api.business.user.entity.repository.UserDatabaseGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserProfileInfoUseCase {

  private final UserDatabaseGateway userDatabaseGateway;
  private final PlayerStatisticsGateway playerStatisticsGateway;

  public UserProfileDTO execute(Jwt jwt) {
    String userId = jwt.getSubject();

    return userDatabaseGateway.getUserProfile(userId).orElse(createUser(jwt));
  }

  @Transactional
  public UserProfileDTO createUser(Jwt jwt) {
    String id = jwt.getSubject();
    String firstName = jwt.getClaimAsString("given_name");
    String lastName = jwt.getClaimAsString("family_name");
    String email = jwt.getClaimAsString("email");

    String username = firstName + " " + lastName;

    UserProfileDTO userProfileDTO = userDatabaseGateway.create(id, username, email);
    playerStatisticsGateway.create(id);
    return userProfileDTO;
  }
}

