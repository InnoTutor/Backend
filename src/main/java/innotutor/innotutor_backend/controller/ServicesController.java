package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.DTO.UserDTO;
import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.CardService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
public class ServicesController {

    private final CardService cardService;
    private final UserService userService;

    public ServicesController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardDTO>> getServices(@AuthenticationPrincipal CustomPrincipal user) {
        Long userId = userService.getUserId(user);
        List<CardDTO> services = cardService.getServices(userId);
        return services == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> postCvCard(@RequestBody CardDTO cardDTO, @AuthenticationPrincipal CustomPrincipal user) {
        if (cardDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long userId = userService.getUserId(user);
        cardDTO.setCreatorId(userId);
        CardDTO result = cardService.postCvCard(cardDTO);
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCvCardById(@RequestParam(name = "cardId") Long cardId, @AuthenticationPrincipal CustomPrincipal user) {
        if (cardId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long userId = userService.getUserId(user);
        return cardService.deleteCardById(userId, cardId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> putCvCard(@RequestBody CardDTO cardDTO, @AuthenticationPrincipal CustomPrincipal user) {
        if (cardDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long userId = userService.getUserId(user);
        cardDTO.setCreatorId(userId);
        CardDTO result = cardService.putCvCard(cardDTO);
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
     */
}
