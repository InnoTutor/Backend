package innotutor.innotutor_backend.controller.card;

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
@RequestMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ServicesController {

    private final transient CardController cardController;
    private final transient CardService cardService;
    private final transient CardsListService cardsListService;
    private final transient UserService userService;
    private final transient SessionService sessionService;

    public ServicesController(final CardController cardController, final CardService cardService,
                              final CardsListService cardsListService, final UserService userService,
                              final SessionService sessionService) {
        this.cardController = cardController;
        this.cardService = cardService;
        this.cardsListService = cardsListService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardDTO>> getServices(@AuthenticationPrincipal final CustomPrincipal user) {
        final List<CardDTO> services = cardsListService.getServices(userService.getUserId(user));
        return services == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectDTO>> getAvailableSubjects(@AuthenticationPrincipal final CustomPrincipal user) {
        final List<SubjectDTO> result = sessionService.getAvailableServiceSubjects(userService.getUserId(user));
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardDTO>> getUserServicesById(@PathVariable final Long userId,
                                                             @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<List<CardDTO>> response;
        if (userService.isUserEqualsId(user, userId)) {
            response = this.getServices(user);
        } else {
            final List<CardDTO> result = cardsListService.getUserServices(userId);
            response = result == null
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> postCvCard(@RequestBody final CardDTO cardDTO,
                                              @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<CardDTO> response;
        if (cardDTO == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            final CardDTO result = cardService.postCvCard(cardDTO, userService.getUserId(user));
            response = result == null
                    ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return response;
    }

    @PutMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> putCvCard(@PathVariable final Long cardId,
                                             @RequestBody final CardDTO cardDTO,
                                             @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<CardDTO> response;
        if (cardDTO == null || cardId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            final CardDTO result = cardService.putCvCard(cardId, cardDTO, userService.getUserId(user));
            if (result == null) {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                response = new ResponseEntity<>(
                        result,
                        result.getCardId().equals(cardDTO.getCardId())
                                ? HttpStatus.OK
                                : HttpStatus.CREATED
                );
            }
        }
        return response;
    }

    @DeleteMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCvCardById(@PathVariable final Long cardId,
                                              @AuthenticationPrincipal final CustomPrincipal user) {
        return cardController.deleteCardById(cardId, user);
    }
}
