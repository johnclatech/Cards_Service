package com.johncla.cards.dao;

import com.johncla.cards.model.Card;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardSearchDao {

    private  final EntityManager entityManager;

    public List<Card> findAllBySimpleQuery(
            String name,
            String color,
            String status,
            String createdBy,
            String creationDate

    ){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);

//        Select * from Card
        Root<Card> root = criteriaQuery.from(Card.class);

        //PREPARE WHERE CLAUSES USING LIKE %%
        Predicate namePredicate = criteriaBuilder
                .like(root.get("name"), "%" + name + "%");
        Predicate colorPredicate = criteriaBuilder
                .like(root.get("color"), "%" + color + "%");
        Predicate statusPredicate = criteriaBuilder
                .like(root.get("status"), "%" + status + "%");
        Predicate createdByPredicate = criteriaBuilder
                .like(root.get("createdBy"), "%" + createdBy + "%");
        Predicate creationDatePredicate = criteriaBuilder
                .like(root.get("creationDate"), "%" + creationDate + "%");
        Predicate orPredicate = criteriaBuilder.or(
                namePredicate,
                colorPredicate,
                statusPredicate,
                createdByPredicate,
                creationDatePredicate
        );
        //Final Query : Select * from Card where name like %value% or status like %value% and cl
        var queryPredicate = criteriaBuilder.and(orPredicate);
        criteriaQuery.where(queryPredicate);
        TypedQuery<Card> query = entityManager.createQuery(criteriaQuery);
        return  query.getResultList();
    }

    public List<Card> findAllByCriteria(
            SearchRequest searchRequest
    ){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);
        List<Predicate> predicates = new ArrayList<>();

        //Select * from cards

        Root<Card> root = criteriaQuery.from(Card.class);

        if(searchRequest.getName() != null){
                Predicate namePredicate = criteriaBuilder
                        .like(root.get("name"), "%" +searchRequest.getName()+ "%");
                predicates.add(namePredicate);
        }
        if(searchRequest.getColor() != null){
            Predicate colorPredicate = criteriaBuilder
                    .like(root.get("color"), "%" +searchRequest.getColor()+ "%");
            predicates.add(colorPredicate);
        }
        if(searchRequest.getStatus() != null){
            Predicate statusPredicate = criteriaBuilder
                    .like(root.get("status"), "%" +searchRequest.getStatus()+ "%");
            predicates.add(statusPredicate);
        }
        if(searchRequest.getCreatedBy() != null){
            Predicate createdByPredicate = criteriaBuilder
                    .like(root.get("createdBy"), "%" +searchRequest.getCreatedBy()+ "%");
            predicates.add(createdByPredicate);
        }
        if(searchRequest.getCreationDate() != null){
            Predicate creationDatePredicate = criteriaBuilder
                    .like(root.get("creationDate"), "%" +searchRequest.getCreationDate()+ "%");
            predicates.add(creationDatePredicate);
        }
        criteriaQuery.where(
                criteriaBuilder.or(
                        predicates.toArray(new Predicate[0]))
                );

        TypedQuery<Card> cardTypedQuery = entityManager.createQuery(criteriaQuery);
        return cardTypedQuery.getResultList();
    }
}
