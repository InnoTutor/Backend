package innotutor.innotutor_backend.service.utility.saver;

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.Service;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
import innotutor.innotutor_backend.service.utility.card.CardType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserCardRelationSaver {
    private final User creator;
    private final Card card;
    private final CardType type;
    private final ServiceRepository serviceRepository;
    private final RequestRepository requestRepository;

    public void save() {
        switch (type) {
            case SERVICE:
                serviceRepository.save(new Service(creator.getUserId(), card.getCardId(), creator, card));
                break;
            case REQUEST:
                requestRepository.save(new Request(creator.getUserId(), card.getCardId(), creator, card));
                break;
            default:
                break;
        }
    }
}
