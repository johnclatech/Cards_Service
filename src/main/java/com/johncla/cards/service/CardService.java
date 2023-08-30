package com.johncla.cards.service;

import com.johncla.cards.dto.CardDto;
import com.johncla.cards.dto.SearchDto;
import com.johncla.cards.model.Card;
import com.johncla.cards.model.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CardService {

    List<Card> getAllCards();
    Page<Card> searchCards(User user, SearchDto searchDto);

    List<Card> getSearchCards(User user, SearchDto searchDto);
    Card getCardByIdAndUser(Long cardId, User user);

    List<Card> getCardsByCreatedBy(Optional<User> createdBy);

    Optional<Card> getCardByIdAndCreatedBy(Long id, User createdBy);

    List<Card> getCardsByColor(String color);

    List<Card> getCardsByStatus(String status);
    List<Card> getCardsByCreationDate(String creationDate);

    Card createCard(User user, CardDto cardDto);
    Card updateCard(Long cardId, User user, CardDto cardDto);
    void deleteCard(Long cardId, User user);
}
