package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.dto.UserDTO;
import innotutor.innotutor_backend.dto.card.SessionRatingDTO;
import innotutor.innotutor_backend.dto.card.SubjectDTO;
import innotutor.innotutor_backend.dto.session.SessionDTO;
import innotutor.innotutor_backend.dto.session.sessionsettings.SessionFormatDTO;
import innotutor.innotutor_backend.dto.session.sessionsettings.SessionTypeDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.SessionService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SessionController {
    private final transient SessionService sessionService;
    private final transient UserService userService;

    public SessionController(final SessionService sessionService, final UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping(value = "/formats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SessionFormatDTO>> getSessionFormats() {
        return new ResponseEntity<>(sessionService.getSessionFormats(), HttpStatus.OK);
    }

    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SessionTypeDTO>> getSessionTypes() {
        return new ResponseEntity<>(sessionService.getSessionTypes(), HttpStatus.OK);
    }

    @GetMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        return new ResponseEntity<>(sessionService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getStudents(
            @RequestParam(name = "subject", required = false) final String subject,
            @RequestParam(name = "format", required = false) final String format,
            @RequestParam(name = "type", required = false) final String type,
            @AuthenticationPrincipal final CustomPrincipal user) {
        final List<UserDTO> students
                = sessionService.filterStudentsForSession(userService.getUserId(user), subject, format, type);
        return students == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionDTO> postSession(@RequestBody final SessionDTO sessionDTO,
                                                  @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<SessionDTO> response;
        if (sessionDTO == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            final SessionDTO result = sessionService.postSession(sessionDTO, userService.getUserId(user));
            response = result == null
                    ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return response;
    }

    @DeleteMapping(value = "cancel/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelSession(@PathVariable final Long sessionId,
                                           @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (sessionId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Boolean result = sessionService.cancelSession(sessionId, userService.getUserId(user));
            if (result == null) {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                response = result
                        ? new ResponseEntity<>(HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return response;
    }

    @PutMapping(value = "/rate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionRatingDTO> rateSession(@RequestBody final SessionRatingDTO sessionRatingDTO,
                                                        @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<SessionRatingDTO> response;
        if (sessionRatingDTO == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            final SessionRatingDTO result = sessionService.rateSession(sessionRatingDTO, userService.getUserId(user));
            if (result == null) {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        return response;
    }
}
