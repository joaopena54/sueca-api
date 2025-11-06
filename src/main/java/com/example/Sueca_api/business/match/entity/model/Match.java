package com.example.Sueca_api.business.match.entity.model;

import com.example.Sueca_api.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "match")
@NoArgsConstructor
@AllArgsConstructor
public class Match extends BaseEntity {

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    @Column(name = "team_a_score", nullable = false)
    private int teamAScore = 0;

    @Column(name = "team_b_score", nullable = false)
    private int teamBScore = 0;
}
