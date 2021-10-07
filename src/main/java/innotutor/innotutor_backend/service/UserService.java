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

package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.DTO.UserDTO;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.security.CustomPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId).map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
    }

    public Long getUserId(CustomPrincipal user) {
        String email = user.getEmail();
        UserDTO userDTO = getUserByEmail(email);
        return userDTO.getUserId();
    }

    public boolean addUserToDatabase(CustomPrincipal user) {
        if (user != null) {
            String email = user.getEmail();
            String fullName = user.getFullName();
            String[] nameSurname = fullName.split(" ");
            String name = nameSurname[0];
            String surname = nameSurname[1];
            String picture = user.getPicture();
            User userToInsert = new User();
            userToInsert.setEmail(email);
            userToInsert.setName(name);
            userToInsert.setSurname(surname);
            userToInsert.setPicture(picture);
            userRepository.save(userToInsert);
            return true;
        }
        return false;
    }

    public UserDTO updateUserProfile(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
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
