package innotutor.innotutor_backend.controller;

import innotutor.innotutor_backend.DTO.enrollment.RequestedStudentsListDTO;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.StudentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/my-students", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class UserStudentsController {
    private final StudentsService studentsService;
    private final CardEnrollService cardEnrollService;

    public UserStudentsController(StudentsService studentsService, CardEnrollService cardEnrollService) {
        this.studentsService = studentsService;
        this.cardEnrollService = cardEnrollService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestedStudentsListDTO> getUserStudentsList() {
        //todo identify user's id who sent this request
        Long userId = 2L;
        RequestedStudentsListDTO students = studentsService.getUserStudentsList(userId);
        return students == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PutMapping(value = "accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptStudent(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //todo identify user's id who sent this request
        Long userId = 2L;
        return cardEnrollService.acceptStudent(userId, id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeStudent(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //todo identify user's id who sent this request
        Long userId = 2L;
        return cardEnrollService.removeStudent(userId, id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
