package innotutor.innotutor_backend.controller.user;

import innotutor.innotutor_backend.dto.UserDTO;
import innotutor.innotutor_backend.security.CustomPrincipal;
import innotutor.innotutor_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.PUT})
public class UserController {

    private final transient UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> homePage(@AuthenticationPrincipal final CustomPrincipal user) {
        return this.getUserProfile(user);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long userId, @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<UserDTO> response;
        if (userService.isUserEqualsId(user, userId)) {
            response = this.getUserProfile(user);
        } else {
            final UserDTO userDTO = userService.getUserById(userId);
            response = userDTO == null
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserProfile(@AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<UserDTO> response;
        final String email = user.getEmail();
        final UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            if (userService.addUserToDatabase(user)) {
                response = new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            response = new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return response;
    }

    @PutMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody final UserDTO userDTO,
                                                     @AuthenticationPrincipal final CustomPrincipal user) {
        ResponseEntity<UserDTO> response;
        if (userDTO == null) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            userDTO.setUserId(userService.getUserId(user));
            userDTO.setPicture(user.getPicture());
            final UserDTO result = userService.updateUserProfile(userDTO);
            response = result == null
                    ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }
}
