package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.UserDTO;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.security.CustomPrincipal;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDTO getUserById(final Long userId) {
        return userRepository.findById(userId).map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    public UserDTO getUserByEmail(final String email) {
        return userRepository.findByEmail(email).map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    public Long getUserId(final CustomPrincipal user) {
        final String email = user.getEmail();
        final UserDTO userDTO = getUserByEmail(email);
        return userDTO.getUserId();
    }

    public boolean addUserToDatabase(final CustomPrincipal user) {
        if (user != null) {
            final String email = user.getEmail();
            final String fullName = user.getFullName();
            final String[] nameSurname = fullName.split(" ");
            final String name = nameSurname[0];
            final String surname = nameSurname[1];
            final String picture = user.getPicture();
            final User userToInsert = new User();
            userToInsert.setEmail(email);
            userToInsert.setName(name);
            userToInsert.setSurname(surname);
            userToInsert.setPicture(picture);
            userRepository.save(userToInsert);
            return true;
        }
        return false;
    }

    public UserDTO updateUserProfile(final UserDTO userDTO) {
        final Optional<User> userOptional = userRepository.findById(userDTO.getUserId());
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setContacts(userDTO.getContacts());
            user.setDescription(userDTO.getDescription());
            user.setPicture(userDTO.getPicture());
            return modelMapper.map(userRepository.save(user), UserDTO.class);
        }
        return null;
    }
}
