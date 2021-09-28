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

import innotutor.innotutor_backend.DTO.card.SubjectDTO;
import innotutor.innotutor_backend.DTO.session.sessionsettings.SessionFormatDTO;
import innotutor.innotutor_backend.DTO.session.sessionsettings.SessionTypeDTO;
import innotutor.innotutor_backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
public class SessionController {
    @Autowired
    SessionService sessionService;

    @GetMapping(value = "/session-formats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SessionFormatDTO>> getSessionFormats() {
        return new ResponseEntity<>(sessionService.getSessionFormats(), HttpStatus.OK);
    }

    @GetMapping(value = "/session-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SessionTypeDTO>> getSessionTypes() {
        return new ResponseEntity<>(sessionService.getSessionTypes(), HttpStatus.OK);
    }

    @GetMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        return new ResponseEntity<>(sessionService.getSubjects(), HttpStatus.OK);
    }
}