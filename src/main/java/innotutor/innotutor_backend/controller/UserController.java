package innotutor.innotutor_backend.controller;

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

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> homePage(@AuthenticationPrincipal final CustomPrincipal user) {
        return this.getUserProfile(user);
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable final Long userId, @AuthenticationPrincipal final CustomPrincipal user) {
        if (userService.getUserId(user).equals(userId)) {
            return this.getUserProfile(user);
        }
        final UserDTO userDTO = userService.getUserById(userId);
        return userDTO == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserProfile(@AuthenticationPrincipal final CustomPrincipal user) {
        final String email = user.getEmail();
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null) {
            userDTO = userService.addUserToDatabase(user) ? userService.getUserByEmail(email) : null;
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody final UserDTO userDTO,
                                                     @AuthenticationPrincipal final CustomPrincipal user) {
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userDTO.setUserId(userService.getUserId(user));
        userDTO.setPicture(user.getPicture());
        final UserDTO result = userService.updateUserProfile(userDTO);
        return result == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(result, HttpStatus.OK);
    }
}
