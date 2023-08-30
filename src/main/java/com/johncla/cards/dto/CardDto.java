package com.johncla.cards.dto;

import com.johncla.cards.model.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Nullable
public class CardDto {
    @NonNull
    private String name;
    private String description;
    private String color;
    private String status;
}
