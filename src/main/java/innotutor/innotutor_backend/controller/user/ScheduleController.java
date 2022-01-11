package innotutor.innotutor_backend.controller.user;

import innotutor.innotutor_backend.dto.session.ScheduleDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.SessionService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
public class ScheduleController {
    private final transient SessionService sessionService;
    private final transient UserService userService;

    public ScheduleController(final SessionService sessionService, final UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleDTO> getSchedule(@AuthenticationPrincipal final CustomPrincipal user) {
        final ScheduleDTO schedule = sessionService.getSchedule(userService.getUserId(user));
        return schedule == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(schedule, HttpStatus.OK);
    }
}
