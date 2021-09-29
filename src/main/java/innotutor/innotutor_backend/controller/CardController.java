package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/card", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST})
public class CardController {
    @Autowired
    CardService cardService;

    @PostMapping(value = "/cv-card", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> postCvCard(@RequestBody CardDTO cardDTO) {
        if (cardDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CardDTO result = cardService.postCvCard(cardDTO);
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(value = "/request-card", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> postRequestCard(@RequestBody CardDTO cardDTO) {
        if (cardDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CardDTO result = cardService.postRequestCard(cardDTO);
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
