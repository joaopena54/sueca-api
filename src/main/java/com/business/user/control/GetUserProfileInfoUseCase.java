package com.business.user.control;

import com.business.player_statistic.entity.model.PlayerStatistic;
import com.business.player_statistic.entity.repository.PlayerStatisticRepository;
import com.business.player_statistic.entity.repository.PlayerStatisticsGateway;
import com.business.user.dto.UserProfileDTO;
import com.business.user.entity.repository.UserDatabaseGateway;
import com.business.user.entity.repository.UserRepository;
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

        return userDatabaseGateway.getUserProfile(userId)
                .orElse(createUser(jwt));
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
