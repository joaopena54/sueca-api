package com.business.player_statistic.entity.repository;

import com.business.player_statistic.entity.model.PlayerStatistic;
import com.business.user.entity.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerStatisticsGateway {

    private final PlayerStatisticRepository playerStatisticRepository;
    private final EntityManager entityManager;

    public void create(String userId) {

        User user = entityManager.getReference(User.class, userId);
        PlayerStatistic playerStatistic = new PlayerStatistic(user);
        playerStatisticRepository.save(playerStatistic);

    }
}
