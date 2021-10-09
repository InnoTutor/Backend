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

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.utility.card.CardDTOCreator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardsListService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public List<CardDTO> getServices(final Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            final List<CardDTO> services = new ArrayList<>();
            user.getServicesByUserId().forEach(service ->
                    services.add(
                            new CardDTOCreator(service.getCardByCardId(), user.getUserId(), subjectRepository).create()
                    )
            );
            return services;
        }
        return null;
    }

    public List<CardDTO> getUserServices(final Long userId) {
        return userRepository.findById(userId).isPresent()
                ? this.getServices(userId).stream().filter(card -> !card.isHidden()).collect(Collectors.toList())
                : null;
    }

    public List<CardDTO> getRequests(final Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            final List<CardDTO> requests = new ArrayList<>();
            user.getRequestsByUserId().forEach(request ->
                    requests.add(
                            new CardDTOCreator(request.getCardByCardId(), user.getUserId(), subjectRepository).create()
                    )
            );
            return requests;
        }
        return null;
    }

    public List<CardDTO> getUserRequests(final Long userId) {
        return userRepository.findById(userId).isPresent()
                ? this.getRequests(userId).stream().filter(card -> !card.isHidden()).collect(Collectors.toList())
                : null;
    }
}
