package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.card.SubjectDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.CardService;
import innotutor.innotutor_backend.service.CardsListService;
import innotutor.innotutor_backend.service.SessionService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RequestsController {

    private final CardService cardService;
    private final CardsListService cardsListService;
    private final UserService userService;
    private final SessionService sessionService;

    public RequestsController(final CardService cardService, final CardsListService cardsListService,
                              final UserService userService, final SessionService sessionService) {
        this.cardService = cardService;
        this.cardsListService = cardsListService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardDTO>> getRequests(@AuthenticationPrincipal final CustomPrincipal user) {
        final List<CardDTO> requests = cardsListService.getRequests(userService.getUserId(user));
        return requests == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectDTO>> getAvailableRequestSubjects(@AuthenticationPrincipal final CustomPrincipal user) {
        final List<SubjectDTO> result = sessionService.getAvailableRequestSubjects(userService.getUserId(user));
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardDTO>> getUserRequestsById(@PathVariable final Long userId,
                                                             @AuthenticationPrincipal final CustomPrincipal user) {
        if (userService.getUserId(user).equals(userId)) {
            return this.getRequests(user);
        }
        final List<CardDTO> result = cardsListService.getUserRequests(userId);
        return result == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> postRequestCard(@RequestBody final CardDTO cardDTO,
                                                   @AuthenticationPrincipal final CustomPrincipal user) {
        if (cardDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cardDTO.setCreatorId(userService.getUserId(user));
        final CardDTO result = cardService.postRequestCard(cardDTO);
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> putRequestCard(@PathVariable final Long cardId,
                                                  @RequestBody final CardDTO cardDTO,
                                                  @AuthenticationPrincipal final CustomPrincipal user) {
        if (cardDTO == null || cardId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cardDTO.setCreatorId(userService.getUserId(user));
        cardDTO.setCardId(cardId);
        final CardDTO result = cardService.putRequestCard(cardDTO);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                result,
                result.getCardId().equals(cardDTO.getCardId())
                        ? HttpStatus.OK
                        : HttpStatus.CREATED
        );
    }

    @DeleteMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteRequestCardById(@PathVariable final Long cardId,
                                                   @AuthenticationPrincipal final CustomPrincipal user) {
        if (cardId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return cardService.deleteCardById(userService.getUserId(user), cardId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
