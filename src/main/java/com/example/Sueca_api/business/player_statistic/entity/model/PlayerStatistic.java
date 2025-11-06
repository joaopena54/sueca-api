package com.example.Sueca_api.business.player_statistic.entity.model;

import com.example.Sueca_api.base.entity.BaseEntity;
import com.example.Sueca_api.business.user.entity.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "player_statistic")
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatistic extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "total_matches")
    private int totalMatches = 0;

    @Column(name = "matches_won")
    private int matchesWon = 0;

    @Column(name = "matches_lost")
    private int matchesLost = 0;

    public PlayerStatistic(User user) {
        this.user = user;
    }
}


