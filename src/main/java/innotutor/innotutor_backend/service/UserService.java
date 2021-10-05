package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.DTO.UserDTO;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return modelMapper.map(user, UserDTO.class);
        }
        return null;
    }
}
