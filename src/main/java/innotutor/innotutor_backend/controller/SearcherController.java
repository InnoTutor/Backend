package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.dto.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.dto.searcher.TutorCvDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.SearcherService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
public class SearcherController {

    private final SearcherService searcherService;
    private final UserService userService;

    public SearcherController(final SearcherService searcherService, final UserService userService) {
        this.searcherService = searcherService;
        this.userService = userService;
    }

    @GetMapping(value = "/tutors-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TutorCvDTO>> getTutorsList(
            @RequestParam(name = "subject", required = false) final String subject,
            @RequestParam(name = "format", required = false) final String format,
            @RequestParam(name = "type", required = false) final String type,
            @RequestParam(name = "sorting", required = false) final String sorting,
            @AuthenticationPrincipal final CustomPrincipal user) {
        final List<TutorCvDTO> tutors = searcherService.getTutorCvDTOList(subject, format, type, sorting, userService.getUserId(user));
        return tutors == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(tutors, HttpStatus.OK);
    }

    @GetMapping(value = "/students-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentRequestDTO>> getStudentsList(
            @RequestParam(name = "subject", required = false) final String subject,
            @RequestParam(name = "format", required = false) final String format,
            @RequestParam(name = "type", required = false) final String type,
            @AuthenticationPrincipal final CustomPrincipal user) {
        final List<StudentRequestDTO> students = searcherService.getStudentRequestDTOList(subject, format, type,
                userService.getUserId(user));
        return students == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }
}
