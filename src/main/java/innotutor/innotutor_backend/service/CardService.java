package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.searcher.UserCard;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.card.CardSessionFormatRepository;
import innotutor.innotutor_backend.repository.card.CardSessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.utility.card.CardCreatorId;
import innotutor.innotutor_backend.service.utility.card.CardDTOCreator;
import innotutor.innotutor_backend.service.utility.card.CardType;
import innotutor.innotutor_backend.service.utility.cardmanager.CardCreator;
import innotutor.innotutor_backend.service.utility.cardmanager.CardUpdater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {
    private final CardEnrollService cardEnrollService;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final SubjectRepository subjectRepository;
    private final ServiceRepository serviceRepository;
    private final RequestRepository requestRepository;
    private final CardSessionFormatRepository cardSessionFormatRepository;
    private final CardSessionTypeRepository cardSessionTypeRepository;

    public CardDTO getCardById(final Long cardId, final Long userId) {
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            final Card card = cardOptional.get();
            final Long creatorId = new CardCreatorId(card).creatorId();
            if (!card.getHidden() || userId.equals(creatorId)) {
                return new CardDTOCreator(card, creatorId, cardEnrollService, userId).create();
            }
        }
        return null;
    }

    public UserCard getCardFullInfoById(final Long cardId, final Long userId) {
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            final Card card = cardOptional.get();
            final Long creatorId = new CardCreatorId(card).creatorId();
            if (!card.getHidden() || userId.equals(creatorId)) {
                innotutor.innotutor_backend.entity.user.Service service = card.getServiceByCardId();
                Request request = card.getRequestByCardId();
                if (service != null) {
                    return new CardDTOCreator(card, creatorId, cardEnrollService, userId).createTutorCvDTO();
                } else if (request != null) {
                    return new CardDTOCreator(card, creatorId, cardEnrollService, userId).createStudentRequestDTO();
                }
            }
        }
        return null;
    }

    public CardDTO postCvCard(final CardDTO cardDTO) {
        return new CardCreator(cardDTO,
                CardType.SERVICE,
                cardRepository,
                userRepository,
                subjectRepository,
                sessionFormatRepository,
                sessionTypeRepository,
                serviceRepository,
                requestRepository,
                cardSessionFormatRepository,
                cardSessionTypeRepository).postCard();
    }

    public CardDTO postRequestCard(final CardDTO cardDTO) {
        return new CardCreator(cardDTO,
                CardType.REQUEST,
                cardRepository,
                userRepository,
                subjectRepository,
                sessionFormatRepository,
                sessionTypeRepository,
                serviceRepository,
                requestRepository,
                cardSessionFormatRepository,
                cardSessionTypeRepository).postCard();
    }

    public CardDTO putCvCard(final CardDTO cardDTO) {
        return new CardUpdater(cardDTO,
                CardType.SERVICE,
                cardDTO.getCreatorId(),
                cardRepository,
                userRepository,
                subjectRepository,
                sessionFormatRepository,
                sessionTypeRepository,
                serviceRepository,
                requestRepository,
                cardSessionFormatRepository,
                cardSessionTypeRepository,
                cardEnrollService).putCard();
    }

    public CardDTO putRequestCard(final CardDTO cardDTO) {
        return new CardUpdater(cardDTO,
                CardType.REQUEST,
                cardDTO.getCreatorId(),
                cardRepository,
                userRepository,
                subjectRepository,
                sessionFormatRepository,
                sessionTypeRepository,
                serviceRepository,
                requestRepository,
                cardSessionFormatRepository,
                cardSessionTypeRepository,
                cardEnrollService).putCard();
    }

    public boolean deleteCardById(final Long userId, final Long cardId) {
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        final Optional<User> userOptional = userRepository.findById(userId);
        if (!cardOptional.isPresent() || !userOptional.isPresent()) {
            return false;
        }
        final innotutor.innotutor_backend.entity.user.Service service = serviceRepository.findByCardId(cardId);
        if (service != null && service.getTutorId().equals(userId)) {
            cardRepository.deleteById(cardId);
            return true;
        }
        final Request request = requestRepository.findByCardId(cardId);
        if (request != null && request.getStudentId().equals(userId)) {
            cardRepository.deleteById(cardId);
            return true;
        }
        return false;
    }
}
