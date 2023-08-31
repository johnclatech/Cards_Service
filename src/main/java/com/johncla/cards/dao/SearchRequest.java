package com.johncla.cards.dao;

import lombok.*;
import org.springframework.data.domain.Pageable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NonNull
public class SearchRequest {
    @NonNull
    private String name;
    private String color;
    private String status;
    private String createdBy;
    private String creationDate;
    private Pageable pageable;

}
