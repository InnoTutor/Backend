package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.dto.enrollment.RequestedStudentsListDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.StudentsService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/my-students", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class MyStudentsController {
    private final transient StudentsService studentsService;
    private final transient CardEnrollService cardEnrollService;
    private final transient UserService userService;

    public MyStudentsController(final StudentsService studentsService, final CardEnrollService cardEnrollService, final UserService userService) {
        this.studentsService = studentsService;
        this.cardEnrollService = cardEnrollService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestedStudentsListDTO> getUserStudentsList(@AuthenticationPrincipal final CustomPrincipal user) {
        final RequestedStudentsListDTO students = studentsService.getUserStudentsList(userService.getUserId(user));
        return students == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PutMapping(value = "accept/{enrollmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptStudent(@PathVariable final Long enrollmentId, @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (enrollmentId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = cardEnrollService.acceptStudent(userService.getUserId(user), enrollmentId)
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping(value = "remove/{enrollmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeStudent(@PathVariable final Long enrollmentId, @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<?> response;
        if (enrollmentId == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            response = cardEnrollService.removeStudent(userService.getUserId(user), enrollmentId)
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
