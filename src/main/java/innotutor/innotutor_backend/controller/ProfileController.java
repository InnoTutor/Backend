package innotutor.innotutor_backend.controller;


import innotutor.innotutor_backend.DTO.UserDTO;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.security.CustomPrincipal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import innotutor.innotutor_backend.service.UserService;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET})
public class ProfileController {


    private final UserRepository userRepository;
    private final UserService userService;
    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserProfile(@AuthenticationPrincipal CustomPrincipal user) {
        String email = user.getEmail();
        UserDTO userDTO = userService.getUserByEmail(email);
        return userDTO == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void checkUserProfile(@AuthenticationPrincipal CustomPrincipal user) {
        String email = user.getEmail();
        UserDTO userDTO = userService.getUserByEmail(email);
        if (userDTO == null){
            addUserToDatabase(user);
        }
    }


    private void addUserToDatabase(CustomPrincipal user){
        String email = user.getEmail();
        String fullName = user.getFullName();
        String[] nameSurname = fullName.split(" ");
        String name = nameSurname[0];
        String surname = nameSurname[1];
        User userToInsert = new User();
        userToInsert.setEmail(email);
        userToInsert.setName(name);
        userToInsert.setSurname(surname);
        userRepository.save(userToInsert);
    }
}

