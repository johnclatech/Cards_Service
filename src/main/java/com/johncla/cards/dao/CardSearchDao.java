package com.johncla.cards.dao;

import com.johncla.cards.model.Card;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class CardSearchDao {

    private SearchRequest searchRequest = new SearchRequest();
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

    public Page<Card> findAllByCriteria(SearchRequest searchRequest){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> criteriaQuery = criteriaBuilder.createQuery(Card.class);
        List<Predicate> predicates = new ArrayList<>();

        //Select * from cards
        Root<Card> root = criteriaQuery.from(Card.class);
        log.info("Contents of searchRequest : \n");
        log.info("searchRequest.getName() : " + searchRequest.getName());
        log.info("searchRequest.getColor() : " + searchRequest.getColor());
        log.info("searchRequest.getStatus() : " + searchRequest.getStatus());
        log.info("searchRequest.getCreatedBy() : " + searchRequest.getCreatedBy());
        log.info("searchRequest.getCreationDate() : " + searchRequest.getCreationDate());

        Pageable pageable = searchRequest.getPageable();
        if(searchRequest.getName() != null) {
            Predicate namePredicate = criteriaBuilder
                    .like(root.get("name"), "%" + searchRequest.getName() + "%");
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

        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        Predicate finalPredicate = criteriaBuilder.or(predicatesArray);
//        criteriaQuery.where(
//                criteriaBuilder.or(
//                        predicates.toArray(new Predicate[0]))
//                );


        TypedQuery<Card> cardTypedQuery = entityManager.createQuery(criteriaQuery);
        cardTypedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        cardTypedQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(cardTypedQuery.getResultList(), pageable, getCount(criteriaQuery, finalPredicate));
    }

    private Long getCount(CriteriaQuery<Card> query, Predicate predicate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Card.class)));
        countQuery.where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
