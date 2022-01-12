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
import innotutor.innotutor_backend.service.utility.card.CardCreatorUser;
import innotutor.innotutor_backend.service.utility.card.CardDTOCreator;
import innotutor.innotutor_backend.service.utility.card.CardType;
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
            final User creator = new CardCreatorUser(card).creator();
            if (!card.getHidden() || userId.equals(creator.getUserId())) {
                return new CardDTOCreator(card, creator, cardEnrollService, userId).create();
            }
        }
        return null;
    }

    public UserCard getCardFullInfoById(final Long cardId, final Long userId) {
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            final Card card = cardOptional.get();
            final User creator = new CardCreatorUser(card).creator();
            if (!card.getHidden() || userId.equals(creator.getUserId())) {
                innotutor.innotutor_backend.entity.user.Service service = card.getServiceByCardId();
                Request request = card.getRequestByCardId();
                if (service != null) {
                    return new CardDTOCreator(card, creator, cardEnrollService, userId).createTutorCvDTO();
                } else if (request != null) {
                    return new CardDTOCreator(card, creator, cardEnrollService, userId).createStudentRequestDTO();
                }
            }
        }
        return null;
    }

    public CardDTO postCvCard(final CardDTO cardDTO, final Long userId) {
        return this.postCard(CardType.SERVICE, cardDTO, userId);
    }

    public CardDTO postRequestCard(final CardDTO cardDTO, final Long userId) {
        return this.postCard(CardType.REQUEST, cardDTO, userId);
    }

    public CardDTO putCvCard(final Long cardId, final CardDTO cardDTO, final Long userId) {
        return this.putCard(cardId, CardType.SERVICE, cardDTO, userId);
    }

    public CardDTO putRequestCard(final Long cardId, final CardDTO cardDTO, final Long userId) {
        return this.putCard(cardId, CardType.REQUEST, cardDTO, userId);
    }

    public boolean deleteCardById(final Long cardId, final Long userId) {
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

    private CardDTO postCard(final CardType cardType, final CardDTO cardDTO, final Long userId) {
        cardDTO.setCreatorId(userId);
        return new innotutor.innotutor_backend.service.utility.cardmanager.CardCreator(cardDTO,
                cardType,
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

    private CardDTO putCard(final Long cardId, final CardType cardType, final CardDTO cardDTO, final Long userId) {
        cardDTO.setCreatorId(userId);
        cardDTO.setCardId(cardId);
        return new CardUpdater(cardDTO,
                cardType,
                userId,
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
}
