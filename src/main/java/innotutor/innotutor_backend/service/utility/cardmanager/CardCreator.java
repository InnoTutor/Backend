package innotutor.innotutor_backend.service.utility.cardmanager;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.session.Subject;
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
import innotutor.innotutor_backend.service.utility.card.CardType;
import innotutor.innotutor_backend.service.utility.saver.SessionFormatsCardSaver;
import innotutor.innotutor_backend.service.utility.saver.SessionTypesCardSaver;
import innotutor.innotutor_backend.service.utility.saver.UserCardRelationSaver;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.SessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.SessionFormatEntityConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.SessionTypeConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.SessionTypeEntityConverter;

import java.util.List;

public class CardCreator {
    private final transient CardDTO cardDTO;
    private final transient CardType type;
    private final transient User creator;
    private final transient Subject subject;
    private final transient List<SessionFormat> sessionFormats;
    private final transient List<SessionType> sessionTypes;
    private final transient CardRepository cardRepository;
    private final transient ServiceRepository serviceRepository;
    private final transient RequestRepository requestRepository;
    private final transient CardSessionFormatRepository cardSessionFormatRepository;
    private final transient CardSessionTypeRepository cardSessionTypeRepository;

    public CardCreator(final CardDTO cardDTO,
                       final CardType type,
                       final CardRepository cardRepository,
                       final UserRepository userRepository,
                       final SubjectRepository subjectRepository,
                       final SessionFormatRepository sessionFormatRepository,
                       final SessionTypeRepository sessionTypeRepository,
                       final ServiceRepository serviceRepository,
                       final RequestRepository requestRepository,
                       final CardSessionFormatRepository cardSessionFormatRepository,
                       final CardSessionTypeRepository cardSessionTypeRepository) {
        this.cardDTO = cardDTO;
        this.type = type;
        this.cardRepository = cardRepository;
        this.serviceRepository = serviceRepository;
        this.requestRepository = requestRepository;
        this.cardSessionFormatRepository = cardSessionFormatRepository;
        this.cardSessionTypeRepository = cardSessionTypeRepository;
        creator = userRepository.findById(cardDTO.getCreatorId()).orElse(null);
        subject = subjectRepository.findSubjectByName(cardDTO.getSubject());
        sessionFormats = new SessionFormatEntityConverter(cardDTO.getSessionFormat(), sessionFormatRepository)
                .toEntityList();
        sessionTypes = new SessionTypeEntityConverter(cardDTO.getSessionType(), sessionTypeRepository).toEntityList();
    }

    public CardDTO postCard() {
        if (creator != null && subject != null && !sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
            return createCard();
        }
        return null;
    }

    private CardDTO createCard() {
        try {
            if (this.isUniquePair()) {
                Card card = cardRepository.save(new Card(
                        subject.getSubjectId(),
                        cardDTO.getDescription(),
                        false,
                        subject)
                );
                new UserCardRelationSaver(creator, card, type, serviceRepository, requestRepository).save();
                card = new SessionFormatsCardSaver(card, sessionFormats, cardSessionFormatRepository).save();
                card = new SessionTypesCardSaver(card, sessionTypes, cardSessionTypeRepository).save();
                return new CardDTO(
                        card.getCardId(),
                        creator.getUserId(),
                        subject.getName(),
                        null,
                        0,
                        cardDTO.getDescription(),
                        false,
                        new SessionFormatConverter(sessionFormats).stringList(),
                        new SessionTypeConverter(sessionTypes).stringList());
            }
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private boolean isUniquePair() throws IllegalAccessException {
        switch (type) {
            case SERVICE:
                for (final Card card : cardRepository.findBySubjectId(subject.getSubjectId())) {
                    if (card.getServiceByCardId() != null && card.getServiceByCardId().getTutorId().equals(creator.getUserId())) {
                        return false;
                    }
                }
                return true;
            case REQUEST:
                return true;
            default:
                throw new IllegalAccessException("The card type is not specified");
        }
    }
}
