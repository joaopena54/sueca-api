package com.example.Sueca_api.business.match.control;

import com.example.Sueca_api.business.match.entity.model.UserStatus;
import com.example.Sueca_api.business.match.entity.repository.MatchRedisGateway;
import com.example.Sueca_api.business.match.entity.repository.MatchmakingQueueService;
import com.example.Sueca_api.business.match.exception.UserAlreadyInMatchException;
import com.example.Sueca_api.business.match.exception.UserNotFoundException;
import com.example.Sueca_api.business.user.entity.repository.UserDatabaseGateway;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@RequiredArgsConstructor
public class JoinMatchUseCase {

  private final UserDatabaseGateway userDatabaseGateway;
  private final MatchRedisGateway matchRedisGateway;
  private final MatchmakingQueueService matchmakingQueueService;

  public void execute(String userId) {
    if (!userDatabaseGateway.exists(userId)) {
      throw new UserNotFoundException();
    }

    UserStatus userStatus = matchRedisGateway.getUserStatus(userId);
    if (!userStatus.equals(UserStatus.IDLE)) {
      log.log(Level.WARNING, "Already in idle");
      throw new UserAlreadyInMatchException();
    }

    matchRedisGateway.setUserStatus(userId, UserStatus.IN_LOBBY);

    List<String> players = matchmakingQueueService.enqueueAndCheckForMatch(userId);

    if (players.size() == 4) {

      String matchId = UUID.randomUUID().toString();
      log.log(Level.INFO, "Creating match %s with players: %s".formatted(matchId, players));
    }
  }
}
