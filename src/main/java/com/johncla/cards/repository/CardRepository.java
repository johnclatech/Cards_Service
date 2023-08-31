package com.johncla.cards.repository;

import com.johncla.cards.model.Card;
import com.johncla.cards.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Page<Card> findAll(Specification<Card> spec, Pageable pageable);

    List<Card> findByCreatedBy(Optional<User> createdBy);

    Optional<Card> findByIdAndCreatedBy(Long id, User createdBy);

    List<Card> findByColor(String color);

    List<Card> findByStatus(String status);

    List<Card> findByCreationDate(String creationDate);

    List<Card> findByCreatedByAndNameContainingAndColorAndStatus(
            User createdBy, String name, String color, String status);

    Page<Card> findByCreatedBy(Specification<Card> spec, Pageable pageable, User user);

    @Query("FROM Card c WHERE c.name=?1 OR c.color = ?2 OR c.status=?3 OR c.createdBy= ?4 OR c.creationDate=?5")
    Page<Card> findBySimpleCriteria(String name, String color, String status, String createdBy, String creationDate, Pageable pageable);
}
