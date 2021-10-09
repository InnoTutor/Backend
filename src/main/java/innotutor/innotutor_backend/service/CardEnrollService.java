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
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardEnrollRepository cardEnrollRepository;
    private final CardEnrollSessionFormatRepository cardEnrollSessionFormatRepository;
    private final CardEnrollSessionTypeRepository cardEnrollSessionTypeRepository;
    private final EnrollmentStatusRepository enrollmentStatusRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;

    public EnrollmentDTO postCardEnroll(EnrollmentDTO enrollmentDTO) {
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
                            enrollmentStatusRepository.findEnrollmentStatusByStatus("requested")
                    );
                    this.saveCardEnrollSessionFormat(cardEnroll, sessionFormats);
                    this.saveCardEnrollSessionType(cardEnroll, sessionTypes);
                    return new EnrollmentDTO(cardEnroll.getCardEnrollId(), enrollerId, cardId, sessionFormats, sessionTypes);
                }
            }
        }
        return null;
    }

    public boolean acceptStudent(Long tutorId, Long enrollmentId) {
        final Optional<CardEnroll> cardEnrollOptional = cardEnrollRepository.findById(enrollmentId);
        if (cardEnrollOptional.isPresent()) {
            final CardEnroll cardEnroll = cardEnrollOptional.get();
            final innotutor.innotutor_backend.entity.user.Service service = cardEnroll.getCardByCardId().getServiceByCardId();
            final String previousStatus = cardEnroll.getEnrollmentStatusByStatusId().getStatus();
            if (service != null && service.getTutorId().equals(tutorId) && previousStatus.equals("requested")) {
                final EnrollmentStatus enrollmentStatus = enrollmentStatusRepository.findEnrollmentStatusByStatus("accepted");
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
            if ("requested".equals(status) || "accepted".equals(status)) {
                return this.removeStudentCvCard(tutorId, cardEnroll) || this.removeStudentRequestCard(tutorId, cardEnroll);
            }
        }
        return false;
    }

    private boolean removeStudentCvCard(Long tutorId, CardEnroll cardEnroll) {
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

    private boolean removeStudentRequestCard(Long tutorId, CardEnroll cardEnroll) {
        final Request request = cardEnroll.getCardByCardId().getRequestByCardId();
        if (request != null && cardEnroll.getUserId().equals(tutorId)) {
            cardEnrollRepository.delete(cardEnroll);
            return true;
        }
        return false;
    }

    private boolean isUniquePair(Long enrollerId, Long cardId) {
        final List<CardEnroll> cards = cardEnrollRepository.findByUserId(enrollerId);
        for (final CardEnroll cardEnroll : cards) {
            if (cardEnroll.getCardId().equals(cardId)) {
                return false;
            }
        }
        return true;
    }

    private CardEnroll saveCardEnroll(Card card, User tutor, EnrollmentStatus enrollmentStatus) {
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

    private void saveCardEnrollSessionFormat(CardEnroll cardEnroll, List<String> sessionFormats) {
        for (final String format : sessionFormats) {
            final SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(format);
            cardEnrollSessionFormatRepository.save(
                    new CardEnrollSessionFormat(
                            cardEnroll.getCardEnrollId(),
                            sessionFormat.getSessionFormatId(),
                            cardEnroll,
                            sessionFormat
                    )
            );
        }
    }

    private void saveCardEnrollSessionType(CardEnroll cardEnroll, List<String> sessionTypes) {
        for (final String type : sessionTypes) {
            final SessionType sessionType = sessionTypeRepository.findSessionTypeByName(type);
            cardEnrollSessionTypeRepository.save(
                    new CardEnrollSessionType(
                            cardEnroll.getCardEnrollId(),
                            sessionType.getSessionTypeId(),
                            cardEnroll,
                            sessionType
                    )
            );
        }
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
