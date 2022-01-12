package innotutor.innotutor_backend.controller.card;

import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.dto.searcher.UserCard;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.CardService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class CardController {

    private final transient UserService userService;
    private final transient CardService cardService;
    private final transient CardEnrollService cardEnrollService;

    public CardController(final UserService userService, final CardService cardService, final CardEnrollService cardEnrollService) {
        this.userService = userService;
        this.cardService = cardService;
        this.cardEnrollService = cardEnrollService;
    }

    @GetMapping(value = "/card/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCard> getCard(@PathVariable final Long cardId, @AuthenticationPrincipal final CustomPrincipal user) {
        if (cardId == null) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final UserCard card = cardService.getCardFullInfoById(cardId, userService.getUserId(user));
        return card == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping(value = "/enroll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrollmentDTO> postTutorCardEnroll(@RequestBody final EnrollmentDTO enrollmentDTO,
                                                             @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<EnrollmentDTO> responseEntity;
        if (enrollmentDTO == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            enrollmentDTO.setEnrollerId(userService.getUserId(user));
            final EnrollmentDTO result = cardEnrollService.postCardEnroll(enrollmentDTO);
            responseEntity = result == null
                    ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/unenroll/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCardEnrollByCardId(@PathVariable final Long cardId,
                                                      @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (cardId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = cardEnrollService.deleteCardEnrollByCardId(userService.getUserId(user), cardId)
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public ResponseEntity<?> deleteCardById(final Long cardId, final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (cardId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = cardService.deleteCardById(cardId, userService.getUserId(user))
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
