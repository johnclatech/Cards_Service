package com.johncla.cards.dao;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchRequest {
    private String name;
    private String color;
    private String status;
    private String createdBy;
    private String creationDate;
}
