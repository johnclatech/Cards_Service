package com.johncla.cards.dto;
import jakarta.annotation.Nullable;

import lombok.*;
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
