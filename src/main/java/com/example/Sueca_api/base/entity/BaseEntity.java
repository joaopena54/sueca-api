package com.example.Sueca_api.base.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sueca_id_seq")
  @SequenceGenerator(name = "sueca_id_seq", sequenceName = "sueca_id_seq", allocationSize = 50)
  private long id;

  @CreationTimestamp
  @Column(name = "created_date")
  private Instant createdDate;

  @UpdateTimestamp
  @Column(name = "updated_date")
  private Instant updatedDate;
}
