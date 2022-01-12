package innotutor.innotutor_backend.service.utility.cardmanager;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.card.CardSessionFormatRepository;
import innotutor.innotutor_backend.repository.card.CardSessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.utility.card.CardCreatorId;
import innotutor.innotutor_backend.service.utility.card.CardDTOCreator;
import innotutor.innotutor_backend.service.utility.card.CardType;
import innotutor.innotutor_backend.service.utility.saver.SessionFormatsCardSaver;
import innotutor.innotutor_backend.service.utility.saver.SessionTypesCardSaver;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.SessionFormatEntityConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.SessionTypeEntityConverter;

import java.util.List;


public class CardUpdater {
    private final transient CardDTO cardDTO;
    private final transient CardType type;
    private final transient Card card;
    private final transient List<SessionFormat> sessionFormats;
    private final transient List<SessionType> sessionTypes;
    private final transient Long userId;
    private final transient CardRepository cardRepository;
    private final transient UserRepository userRepository;
    private final transient SubjectRepository subjectRepository;
    private final transient SessionFormatRepository sessionFormatRepository;
    private final transient SessionTypeRepository sessionTypeRepository;
    private final transient ServiceRepository serviceRepository;
    private final transient RequestRepository requestRepository;
    private final transient CardSessionFormatRepository cardSessionFormatRepository;
    private final transient CardSessionTypeRepository cardSessionTypeRepository;
    private final transient CardEnrollService cardEnrollService;

    public CardUpdater(final CardDTO cardDTO,
                       final CardType type,
                       final Long userId,
                       final CardRepository cardRepository,
                       final UserRepository userRepository,
                       final SubjectRepository subjectRepository,
                       final SessionFormatRepository sessionFormatRepository,
                       final SessionTypeRepository sessionTypeRepository,
                       final ServiceRepository serviceRepository,
                       final RequestRepository requestRepository,
                       final CardSessionFormatRepository cardSessionFormatRepository,
                       final CardSessionTypeRepository cardSessionTypeRepository,
                       final CardEnrollService cardEnrollService) {
        this.cardDTO = cardDTO;
        this.type = type;
        this.userId = userId;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.sessionFormatRepository = sessionFormatRepository;
        this.sessionTypeRepository = sessionTypeRepository;
        this.serviceRepository = serviceRepository;
        this.requestRepository = requestRepository;
        this.cardSessionFormatRepository = cardSessionFormatRepository;
        this.cardSessionTypeRepository = cardSessionTypeRepository;
        this.cardEnrollService = cardEnrollService;
        card = cardRepository.findById(cardDTO.getCardId()).orElse(null);
        sessionFormats = new SessionFormatEntityConverter(
                cardDTO.getSessionFormat(),
                sessionFormatRepository
        ).toEntityList();
        sessionTypes = new SessionTypeEntityConverter(
                cardDTO.getSessionType(),
                sessionTypeRepository
        ).toEntityList();
    }

    public CardDTO putCard() {
        if (card == null) {
            return new CardCreator(cardDTO,
                    type,
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
        if (cardDTO.getCreatorId().equals(new CardCreatorId(card).creatorId())
                && !sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
            return this.updateCard();
        }
        return null;
    }

    private CardDTO updateCard() {
        card.setDescription(cardDTO.getDescription());
        card.setHidden(cardDTO.isHidden());
        Card savedCard = cardRepository.save(card);
        savedCard = this.updateSessionFormats(savedCard);
        savedCard = this.updateSessionTypes(savedCard);
        return new CardDTOCreator(savedCard, cardDTO.getCreatorId(), cardEnrollService, userId).create();
    }

    private Card updateSessionFormats(final Card card) {
        cardSessionFormatRepository.deleteAll(card.getCardSessionFormatsByCardId());
        return new SessionFormatsCardSaver(card, sessionFormats, cardSessionFormatRepository).save();
    }

    private Card updateSessionTypes(final Card card) {
        cardSessionTypeRepository.deleteAll(card.getCardSessionTypesByCardId());
        return new SessionTypesCardSaver(card, sessionTypes, cardSessionTypeRepository).save();
    }
}
