package com.business.player_statistic.entity.repository;

import com.business.player_statistic.entity.model.PlayerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStatisticRepository extends JpaRepository<PlayerStatistic, Long> {
}
