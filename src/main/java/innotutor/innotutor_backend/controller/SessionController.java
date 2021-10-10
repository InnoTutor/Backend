package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.dto.UserDTO;
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
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
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
        final List<UserDTO> students = sessionService.filterStudentsForSession(userService.getUserId(user), subject, format, type);
        return students == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionDTO> postTutorCardEnroll(@RequestBody final SessionDTO sessionDTO,
                                                          @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<SessionDTO> response;
        if (sessionDTO == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            sessionDTO.setTutorId(userService.getUserId(user));
            final SessionDTO result = sessionService.postSession(sessionDTO);
            response = result == null
                    ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return response;
    }
}
