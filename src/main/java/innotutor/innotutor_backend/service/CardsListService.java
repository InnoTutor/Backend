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

    public List<CardDTO> getServices(final Long userId) { //NOPMD - suppressed ReturnEmptyCollectionRatherThanNull - null indicates that such collection doesn't exist
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

    public List<CardDTO> getRequests(final Long userId) { //NOPMD - suppressed ReturnEmptyCollectionRatherThanNull - null indicates that such collection doesn't exist
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
