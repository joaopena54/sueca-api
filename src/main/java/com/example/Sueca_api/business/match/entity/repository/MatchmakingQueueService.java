package com.example.Sueca_api.business.match.entity.repository;

import java.util.List;
import java.util.logging.Level;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@RequiredArgsConstructor
public class MatchmakingQueueService {

  private static final int REQUIRED_PLAYERS = 4;

  private final MatchRedisGateway matchRedisGateway;

  public List<String> enqueueAndCheckForMatch(String userId) {

    List<String> players = matchRedisGateway.addToQueueAndPopIfReady(userId);

    if (players.size() == REQUIRED_PLAYERS) {
      log.log(Level.INFO, "Found %d players ready for match: %s".formatted( players.size(), players));
    } else {
      long queueSize = matchRedisGateway.getMatchmakingQueueSize();
      log.log(Level.FINE, "Player %s added to queue. Queue size: %d/%d".formatted(userId, queueSize,REQUIRED_PLAYERS));
    }

    return players;
  }
}

