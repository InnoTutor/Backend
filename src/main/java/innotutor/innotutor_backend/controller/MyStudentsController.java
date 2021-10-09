/*
MIT License

Copyright (c) 2021 InnoTutor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
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
    private final StudentsService studentsService;
    private final CardEnrollService cardEnrollService;
    private final UserService userService;

    public MyStudentsController(final StudentsService studentsService, final CardEnrollService cardEnrollService, UserService userService) {
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
    public ResponseEntity<?> acceptStudent(@PathVariable final Long enrollmentId, @AuthenticationPrincipal CustomPrincipal user) {
        if (enrollmentId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return cardEnrollService.acceptStudent(userService.getUserId(user), enrollmentId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "remove/{enrollmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeStudent(@PathVariable final Long enrollmentId, @AuthenticationPrincipal CustomPrincipal user) {
        if (enrollmentId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return cardEnrollService.removeStudent(userService.getUserId(user), enrollmentId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
