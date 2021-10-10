package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionFormat;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionType;
import innotutor.innotutor_backend.entity.card.enrollment.EnrollmentStatus;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.card.enrollment.CardEnrollRepository;
import innotutor.innotutor_backend.repository.card.enrollment.CardEnrollSessionFormatRepository;
import innotutor.innotutor_backend.repository.card.enrollment.CardEnrollSessionTypeRepository;
import innotutor.innotutor_backend.repository.card.enrollment.EnrollmentStatusRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.utility.card.CardCreatorId;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardEnrollService {
    private final static String REQUESTED = "requested";
    private final static String ACCEPTED = "accepted";
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardEnrollRepository cardEnrollRepository;
    private final CardEnrollSessionFormatRepository cardEnrollSessionFormatRepository;
    private final CardEnrollSessionTypeRepository cardEnrollSessionTypeRepository;
    private final EnrollmentStatusRepository enrollmentStatusRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;

    public EnrollmentDTO postCardEnroll(final EnrollmentDTO enrollmentDTO) {
        final Long enrollerId = enrollmentDTO.getEnrollerId();
        final Long cardId = enrollmentDTO.getCardId();
        final Optional<Card> cardOptional = cardRepository.findById(cardId);
        final Optional<User> userOptional = userRepository.findById(enrollerId);
        if (cardOptional.isPresent() && userOptional.isPresent()) {
            final Card card = cardOptional.get();
            if (!enrollerId.equals(new CardCreatorId(card).creatorId()) && isUniquePair(enrollerId, cardId)) {
                final List<String> sessionFormats = this.getCommonSessionFormats(card, enrollmentDTO);
                final List<String> sessionTypes = this.getCommonSessionTypes(card, enrollmentDTO);
                if (!sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
                    final CardEnroll cardEnroll = this.saveCardEnroll(
                            card,
                            userOptional.get(),
                            enrollmentStatusRepository.findEnrollmentStatusByStatus(REQUESTED)
                    );
                    this.saveCardEnrollSessionFormat(cardEnroll, sessionFormats);
                    this.saveCardEnrollSessionType(cardEnroll, sessionTypes);
                    return new EnrollmentDTO(cardEnroll.getCardEnrollId(), enrollerId, cardId, sessionFormats, sessionTypes);
                }
            }
        }
        return null;
    }

    public boolean acceptStudent(final Long tutorId, final Long enrollmentId) {
        final Optional<CardEnroll> cardEnrollOptional = cardEnrollRepository.findById(enrollmentId);
        if (cardEnrollOptional.isPresent()) {
            final CardEnroll cardEnroll = cardEnrollOptional.get();
            final innotutor.innotutor_backend.entity.user.Service service = cardEnroll.getCardByCardId().getServiceByCardId();
            final String previousStatus = cardEnroll.getEnrollmentStatusByStatusId().getStatus();
            if (service != null && service.getTutorId().equals(tutorId) && previousStatus.equals(REQUESTED)) {
                final EnrollmentStatus enrollmentStatus = enrollmentStatusRepository.findEnrollmentStatusByStatus(ACCEPTED);
                cardEnroll.setEnrollmentStatusByStatusId(enrollmentStatus);
                cardEnroll.setStatusId(enrollmentStatus.getStatusId());
                cardEnrollRepository.save(cardEnroll);
                return true;
            }
        }
        return false;
    }

    public boolean removeStudent(final Long tutorId, final Long enrollmentId) {
        final Optional<CardEnroll> cardEnrollOptional = cardEnrollRepository.findById(enrollmentId);
        if (cardEnrollOptional.isPresent()) {
            final CardEnroll cardEnroll = cardEnrollOptional.get();
            final String status = cardEnroll.getEnrollmentStatusByStatusId().getStatus();
            if (REQUESTED.equals(status) || ACCEPTED.equals(status)) {
                return this.removeStudentCvCard(tutorId, cardEnroll) || this.removeStudentRequestCard(tutorId, cardEnroll);
            }
        }
        return false;
    }

    private boolean removeStudentCvCard(final Long tutorId, final CardEnroll cardEnroll) {
        final innotutor.innotutor_backend.entity.user.Service service = cardEnroll.getCardByCardId().getServiceByCardId();
        if (service != null && service.getTutorId().equals(tutorId)) {
            final EnrollmentStatus enrollmentStatus = enrollmentStatusRepository.findEnrollmentStatusByStatus("rejected");
            cardEnroll.setEnrollmentStatusByStatusId(enrollmentStatus);
            cardEnroll.setStatusId(enrollmentStatus.getStatusId());
            cardEnrollRepository.save(cardEnroll);
            return true;
        }
        return false;
    }

    private boolean removeStudentRequestCard(final Long tutorId, final CardEnroll cardEnroll) {
        final Request request = cardEnroll.getCardByCardId().getRequestByCardId();
        if (request != null && cardEnroll.getUserId().equals(tutorId)) {
            cardEnrollRepository.delete(cardEnroll);
            return true;
        }
        return false;
    }

    private boolean isUniquePair(final Long enrollerId, final Long cardId) {
        final List<CardEnroll> cards = cardEnrollRepository.findByUserId(enrollerId);
        for (final CardEnroll cardEnroll : cards) {
            if (cardEnroll.getCardId().equals(cardId)) {
                return false;
            }
        }
        return true;
    }

    private CardEnroll saveCardEnroll(final Card card, final User tutor, final EnrollmentStatus enrollmentStatus) {
        return cardEnrollRepository.save(
                new CardEnroll(
                        card.getCardId(),
                        tutor.getUserId(),
                        enrollmentStatus.getStatusId(),
                        card,
                        tutor,
                        enrollmentStatus
                )
        );
    }

    private void saveCardEnrollSessionFormat(final CardEnroll cardEnroll, final List<String> sessionFormats) {
        sessionFormats.forEach(format -> {
            final SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(format);
            cardEnrollSessionFormatRepository.save(
                    new CardEnrollSessionFormat(
                            cardEnroll.getCardEnrollId(),
                            sessionFormat.getSessionFormatId(),
                            cardEnroll,
                            sessionFormat
                    )
            );
        });
    }

    private void saveCardEnrollSessionType(final CardEnroll cardEnroll, final List<String> sessionTypes) {
        sessionTypes.forEach(type -> {
            final SessionType sessionType = sessionTypeRepository.findSessionTypeByName(type);
            cardEnrollSessionTypeRepository.save(
                    new CardEnrollSessionType(
                            cardEnroll.getCardEnrollId(),
                            sessionType.getSessionTypeId(),
                            cardEnroll,
                            sessionType
                    )
            );
        });
    }

    private List<String> getCommonSessionFormats(final Card card, final EnrollmentDTO enrollmentDTO) {
        return new CardSessionFormatConverter(card.getCardSessionFormatsByCardId())
                .stringList()
                .stream()
                .distinct()
                .filter(enrollmentDTO.getSessionFormat()::contains)
                .collect(Collectors.toList());
    }

    private List<String> getCommonSessionTypes(final Card card, final EnrollmentDTO enrollmentDTO) {
        return new CardSessionTypeConverter(card.getCardSessionTypesByCardId())
                .stringList()
                .stream()
                .distinct()
                .filter(enrollmentDTO.getSessionType()::contains)
                .collect(Collectors.toList());
    }
}
