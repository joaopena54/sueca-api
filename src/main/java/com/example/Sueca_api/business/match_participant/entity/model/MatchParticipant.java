package com.example.Sueca_api.business.match_participant.entity.model;

import com.example.Sueca_api.base.entity.BaseEntity;
import com.example.Sueca_api.business.match.entity.model.Match;
import com.example.Sueca_api.business.match.entity.model.Team;
import com.example.Sueca_api.business.user.entity.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "match_participant")
@NoArgsConstructor
@AllArgsConstructor
public class MatchParticipant extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "match_id", nullable = false)
  private Match match;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Team team;

  @Column private Boolean won;
}
