package com.johncla.cards.controller;

import com.johncla.cards.dto.CardDto;
import com.johncla.cards.dto.SearchDto;
import com.johncla.cards.exceptions.UserExceptions;
import com.johncla.cards.model.Card;
import com.johncla.cards.model.User;
import com.johncla.cards.service.CardService;
import com.johncla.cards.serviceimpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CardController {
    private final CardService cardService;

    private final UserServiceImpl userService;

    @PostMapping("/create-card")
    public  ResponseEntity<Card> createCard(@RequestBody CardDto cardDto){
        Card createdCard = cardService.createCard(getUserContext(),cardDto);
        return ResponseEntity.ok(createdCard);
    }

    @GetMapping("/fetch-card/{email}")
    public ResponseEntity<List<Card>> getUserCards(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        List<Card> cards;
        if (user.isPresent()) {
            log.info("user ispresent" + user.get().getRole().toString());
            if(user.get().getRole().toString().equals(("ADMIN"))){
                // Admins have access to all cards
                cards = cardService.getAllCards(); // Fetch all cards
            }else if(user.get().getRole().toString().equals(("MEMBER"))){
                // Members have access to cards created by them
                cards = cardService.getCardsByCreatedBy(user); // Fetch cards
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return Forbidden status for members
            }
        } else {
            throw new UserExceptions.UserNotFoundException("User Not Found");
        }

        return ResponseEntity.ok(cards);
    }


    @RequestMapping(value = "/search-card", method = RequestMethod.PUT)
    public List<Card> searchCards(@RequestBody SearchDto searchDto) {
//        return cardService.searchCards(getUserContext(), searchDto);

        return  cardService.getSearchCards(getUserContext(),searchDto);
    }

    @GetMapping("/filterby-idanduser/{cardId}")
    public Card getCard(@PathVariable Long cardId) {

        return cardService.getCardByIdAndUser(cardId, getUserContext());
    }

    @PutMapping("/update-card/{cardId}")
    public Card updateCard(@PathVariable Long cardId, @RequestBody CardDto cardDto) {
        return cardService.updateCard(cardId, getUserContext(), cardDto);
    }

    @DeleteMapping("/delete-card/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId, getUserContext());
        return ResponseEntity.ok().build();
    }

    private User getUserContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}