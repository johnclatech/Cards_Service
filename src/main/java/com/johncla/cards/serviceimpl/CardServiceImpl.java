package com.johncla.cards.serviceimpl;

import com.johncla.cards.dao.CardSearchDao;
import com.johncla.cards.dao.SearchRequest;
import com.johncla.cards.dto.CardDto;
import com.johncla.cards.dto.SearchDto;
import com.johncla.cards.exceptions.CardsExceptions;
import com.johncla.cards.model.Card;
import com.johncla.cards.model.User;
import com.johncla.cards.repository.CardRepository;
import com.johncla.cards.service.CardService;
import com.johncla.cards.utility.Utility;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    private final Utility utility;

    private final CardSearchDao cardSearchDao;

    private final SearchRequest searchRequest;
    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> searchCards(User user, SearchDto searchDto) {
        log.info("filter : OrderBy - " +searchDto.getOrderByColumn());
        log.info("filter : page  | size - " +searchDto.getPage() +" | " + searchDto.getSize());
        Sort sortBy = Sort.by(Sort.Direction.ASC, searchDto.getOrderByColumn());
//
//        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize(), sortBy);
//
//        Specification<Card> spec = Specification.where(CardSpecifications.userHasAccess(user))
//                .and(CardSpecifications.nameContains(searchDto.getName()))
//                .and(CardSpecifications.colorEquals(searchDto.getColor()))
//                .and(CardSpecifications.statusEquals(searchDto.getStatus()))
//                .and(CardSpecifications.creationDateEquals(searchDto.getCreationDate()));
//
//        Page<Card> cards;

            String role = user.getRole().toString();
            log.info("user present : " + role);
            if(role.equals(("ADMIN")) || role.equals(("MEMBER"))){
                // Admins have access to all cards
                return cardSearchDao.findAllByCriteria(searchRequest);
//                cards = cardRepository.findAll(spec, pageable); // Fetch all cards
//            }else if(role.equals(("MEMBER"))){
//                // Members have access to cards created by them
//                cards = cardRepository.findByCreatedBy(spec, pageable,user); // Fetch all cards
//
            }else{
                throw  new CardsExceptions.ResourceNotFoundException("Card Details Not Found");
            }

    }

    @Override
    public List<Card> getSearchCards(User user, SearchDto searchDto) {

        return cardSearchDao.findAllBySimpleQuery(
                searchDto.getName(),searchDto.getColor(),searchDto.getStatus(),searchDto.getCreatedBy(),searchDto.getCreationDate()
        );
    }

    @Override
    public Card getCardByIdAndUser(Long cardId, User user) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id: " + cardId));
        // Check if the user has access to the card
        if (!card.getCreatedBy().equals(user)) {
            throw new CardsExceptions.AccessDeniedException("You do not have access to this card");
        }
        return card;
    }

    @Override
    public List<Card> getCardsByCreatedBy(Optional<User> createdBy) {
        return cardRepository.findByCreatedBy(createdBy);
    }

    @Override
    public Optional<Card> getCardByIdAndCreatedBy(Long id, User createdBy) {
        return cardRepository.findByIdAndCreatedBy(id, createdBy);
    }

    @Override
    public List<Card> getCardsByColor(String color) {
        return cardRepository.findByColor(color);
    }

    @Override
    public List<Card> getCardsByStatus(String status) {
        return cardRepository.findByStatus(status);
    }

    @Override
    public List<Card> getCardsByCreationDate(String creationDate) {
        return cardRepository.findByCreationDate(creationDate);
    }


    @Override
    public Card createCard(User user, CardDto cardDto) {
        log.info("create by : " + user.getRole());
            validateCardDto(cardDto);
            Card card = new Card();
            card.setName(cardDto.getName());
            card.setDescription(cardDto.getDescription());
            card.setColor(cardDto.getColor());
            card.setStatus("To Do");
            card.setCreatedBy(user);
            card.setCreationDate(utility.dateConverter());

            return cardRepository.save(card);
    }

    private void validateCardDto(CardDto cardDto) {
        validateColor(cardDto.getColor()); // Validate color format
    }

    private void validateColor(String color) {

        if (color != null && !color.matches("^#[a-fA-F0-9]{6}$")) {
            throw new CardsExceptions.InvalidColorFormatException("Color should be 6 alphanumeric characters prefixed with #");
        }
    }

    @Override
    public Card updateCard(Long cardId, User user, CardDto cardDto) {
        log.info("updated by : " + user.getRole());
        validateCardDto(cardDto);
        Card card = getCardByIdAndUser(cardId, user);
        card.setName(cardDto.getName());
        card.setDescription(cardDto.getDescription());
        card.setColor(cardDto.getColor());
        card.setStatus(cardDto.getStatus());

        return cardRepository.save(card);
    }

    @Override
    public void deleteCard(Long cardId, User user) {
        Card card = getCardByIdAndUser(cardId, user);
        cardRepository.delete(card);
    }

    private class CardSpecifications {
        public static Specification<Card> userHasAccess(User user) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), user);
        }

        public static Specification<Card> nameContains(String name) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
        }

        public static Specification<Card> colorEquals(String color) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("color"), color);
        }

        public static Specification<Card> statusEquals(String status) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
        }

        public static Specification<Card> creationDateEquals(String creationDate) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("creationDate"), creationDate);
        }
    }
}
