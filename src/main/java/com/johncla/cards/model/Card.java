package com.johncla.cards.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "card",uniqueConstraints = { @UniqueConstraint(name = "UniqueCardName", columnNames = { "name"}) })
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String color;
    @Nullable
    private String status;
//    @Temporal(TemporalType.TIMESTAMP)
    private String creationDate;
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

}
