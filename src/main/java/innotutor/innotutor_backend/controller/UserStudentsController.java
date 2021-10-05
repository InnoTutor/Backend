package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.DTO.UserDTO;
import innotutor.innotutor_backend.DTO.enrollment.RequestedStudentsListDTO;
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
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class UserStudentsController {
    private final StudentsService studentsService;
    private final CardEnrollService cardEnrollService;
    private final UserService userService;
    public UserStudentsController(StudentsService studentsService, CardEnrollService cardEnrollService, UserService userService) {
        this.studentsService = studentsService;
        this.cardEnrollService = cardEnrollService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestedStudentsListDTO> getUserStudentsList(@AuthenticationPrincipal CustomPrincipal user) {
        String email = user.getEmail();
        UserDTO userDTO = userService.getUserByEmail(email);
        Long userId = userDTO.getUserId();
        RequestedStudentsListDTO students = studentsService.getUserStudentsList(userId);
        return students == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PutMapping(value = "accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptStudent(@PathVariable Long id, @AuthenticationPrincipal CustomPrincipal user) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String email = user.getEmail();
        UserDTO userDTO = userService.getUserByEmail(email);
        Long userId = userDTO.getUserId();
        return cardEnrollService.acceptStudent(userId, id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeStudent(@PathVariable Long id, @AuthenticationPrincipal CustomPrincipal user) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String email = user.getEmail();
        UserDTO userDTO = userService.getUserByEmail(email);
        Long userId = userDTO.getUserId();
        return cardEnrollService.removeStudent(userId, id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
