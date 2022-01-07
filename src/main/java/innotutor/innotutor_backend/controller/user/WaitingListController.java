package innotutor.innotutor_backend.controller.user;

import innotutor.innotutor_backend.dto.enrollment.WaitingListDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/waiting-list", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
public class WaitingListController {
    private final transient CardEnrollService cardEnrollService;
    private final transient UserService userService;

    public WaitingListController(final CardEnrollService cardEnrollService, final UserService userService) {
        this.cardEnrollService = cardEnrollService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WaitingListDTO> getWaitingList(@AuthenticationPrincipal final CustomPrincipal user) {
        final WaitingListDTO waitingList = cardEnrollService.getWaitingList(userService.getUserId(user));
        return waitingList == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(waitingList, HttpStatus.OK);
    }
}
