package innotutor.innotutor_backend.controller.user;

import innotutor.innotutor_backend.dto.enrollment.tutor.RespondedTutorsListInfoDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.TutorsService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/my-tutors", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class MyTutorsController {
    private final transient TutorsService tutorsService;
    private final transient CardEnrollService cardEnrollService;
    private final transient UserService userService;

    public MyTutorsController(final TutorsService tutorsService,
                              final CardEnrollService cardEnrollService,
                              final UserService userService) {
        this.tutorsService = tutorsService;
        this.cardEnrollService = cardEnrollService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespondedTutorsListInfoDTO> getUserTutorsList(
            @AuthenticationPrincipal final CustomPrincipal user) {
        final RespondedTutorsListInfoDTO tutors
                = tutorsService.getUserTutorsListFullInfo(userService.getUserId(user));
        return tutors == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(tutors, HttpStatus.OK);
    }

    @PutMapping(value = "accept/{enrollmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptTutor(@PathVariable final Long enrollmentId,
                                         @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (enrollmentId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = cardEnrollService.acceptTutor(userService.getUserId(user), enrollmentId)
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping(value = "remove/{enrollmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeTutor(@PathVariable final Long enrollmentId,
                                         @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (enrollmentId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = cardEnrollService.removeTutor(userService.getUserId(user), enrollmentId)
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
