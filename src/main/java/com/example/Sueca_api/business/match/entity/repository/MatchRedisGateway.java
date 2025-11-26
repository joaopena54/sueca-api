package com.example.Sueca_api.business.match.entity.repository;

import com.example.Sueca_api.business.match.entity.model.UserStatus;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchRedisGateway {

  private static final String MATCHMAKING_QUEUE_KEY = "matchmaking:queue";
  private static final int REQUIRED_PLAYERS = 4;

  private final RedisTemplate<String, Object> redisTemplate;
  private DefaultRedisScript<List> atomicPopScript;

  @PostConstruct
  public void init() {

    String luaScript =
        "local queueKey = KEYS[1]\n"
            + "local playerId = ARGV[1]\n"
            + "local requiredPlayers = 4\n"
            + "\n"
            + "-- Add player to queue\n"
            + "redis.call('SADD', queueKey, playerId)\n"
            + "redis.call('EXPIRE', queueKey, 3600)\n"
            + "\n"
            + "-- Get queue size\n"
            + "local queueSize = tonumber(redis.call('SCARD', queueKey))\n"
            + "\n"
            + "-- If we have enough players, pop exactly requiredPlayers\n"
            + "if queueSize and queueSize >= requiredPlayers then\n"
            + "  return redis.call('SPOP', queueKey, requiredPlayers)\n"
            + "else\n"
            + "  return {}\n"
            + "end";

    atomicPopScript = new DefaultRedisScript<>();
    atomicPopScript.setScriptText(luaScript);
    atomicPopScript.setResultType(List.class);
  }

  // User Status Methods
  public void setUserStatus(String userId, UserStatus status) {
    String key = "user:" + userId + ":status";
    redisTemplate.opsForValue().set(key, status.name(), 2, TimeUnit.HOURS);
  }

  public UserStatus getUserStatus(String userId) {
    String key = "user:" + userId + ":status";
    String status = (String) redisTemplate.opsForValue().get(key);
    return status != null ? UserStatus.valueOf(status) : UserStatus.IDLE;
  }

  public void clearUserStatus(String userId) {
    redisTemplate.delete("user:" + userId + ":status");
  }

  public boolean isUserInAnyLobby(String userId) {
    return getUserStatus(userId) == UserStatus.IN_LOBBY;
  }

  // Matchmaking Queue Methods
  /**
   * Atomically adds a player to the queue and returns 4 players if enough are ready.
   * This is thread-safe and prevents race conditions.
   *
   * @param userId The user ID to add to the queue
   * @return List of 4 player IDs if enough players are ready, empty list otherwise
   */
  public List<String> addToQueueAndPopIfReady(String userId) {
    List<Object> result =
        redisTemplate.execute(
            atomicPopScript,
            Collections.singletonList(MATCHMAKING_QUEUE_KEY),
            userId);

    if (result == null || result.isEmpty()) {
      return List.of();
    }

    return result.stream().map(Object::toString).collect(Collectors.toList());
  }

  public long getMatchmakingQueueSize() {
    Long size = redisTemplate.opsForSet().size(MATCHMAKING_QUEUE_KEY);
    return size != null ? size : 0;
  }

  public void removeFromMatchmakingQueue(String userId) {
    redisTemplate.opsForSet().remove(MATCHMAKING_QUEUE_KEY, userId);
  }

  public boolean isInMatchmakingQueue(String userId) {
    return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(MATCHMAKING_QUEUE_KEY, userId));
  }
}
