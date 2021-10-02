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

import innotutor.innotutor_backend.DTO.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.service.CardEnrollService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST})
public class CardEnrollController {

    final CardEnrollService cardEnrollService;
    final HttpHeaders responseHeaders;

    public CardEnrollController(CardEnrollService cardEnrollService) {
        this.cardEnrollService = cardEnrollService;
        responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Headers",
                "Accept");
        responseHeaders.set("Access-Control-Allow-Origin",
                "Accept");
    }

    @PostMapping(value = "/students-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrollmentDTO> postStudentCardEnroll(@RequestBody EnrollmentDTO enrollmentDTO) {
        return postCardEnroll(enrollmentDTO);
    }

    @PostMapping(value = "/tutors-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrollmentDTO> postTutorCardEnroll(@RequestBody EnrollmentDTO enrollmentDTO) {
        return postCardEnroll(enrollmentDTO);
    }

    private ResponseEntity<EnrollmentDTO> postCardEnroll(EnrollmentDTO enrollmentDTO) {
        if (enrollmentDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EnrollmentDTO result = cardEnrollService.postCardEnroll(enrollmentDTO);
        return result == null
                ? new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, responseHeaders, HttpStatus.CREATED);
    }
}
