package com.johncla.cards.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.lang.NonNullFields;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  SearchDto {
    @NonNull
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String color;
    @Nullable
    private String status;
    @Nullable
    private String createdBy;
    @Nullable
    private String creationDate;
    @Nullable
    @JsonProperty(defaultValue = "name")
    private String orderByColumn;
    @Nullable
    @JsonProperty(defaultValue = "0")
    private Integer page;
    @Nullable
    @JsonProperty(defaultValue = "10")
    private Integer size;

}
