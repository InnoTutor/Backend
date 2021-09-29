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

import innotutor.innotutor_backend.DTO.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionFormat;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionType;
import innotutor.innotutor_backend.entity.card.enrollment.EnrollmentStatus;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.card.enrollment.CardEnrollRepository;
import innotutor.innotutor_backend.repository.card.enrollment.CardEnrollSessionFormatRepository;
import innotutor.innotutor_backend.repository.card.enrollment.CardEnrollSessionTypeRepository;
import innotutor.innotutor_backend.repository.card.enrollment.EnrollmentStatusRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.utility.session.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.utility.session.sessiontype.CardSessionTypeConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardEnrollService {
    final CardRepository cardRepository;
    final UserRepository userRepository;
    final CardEnrollRepository cardEnrollRepository;
    final CardEnrollSessionFormatRepository cardEnrollSessionFormatRepository;
    final CardEnrollSessionTypeRepository cardEnrollSessionTypeRepository;
    final EnrollmentStatusRepository enrollmentStatusRepository;
    final SessionFormatRepository sessionFormatRepository;
    final SessionTypeRepository sessionTypeRepository;

    public CardEnrollService(CardRepository cardRepository,
                             UserRepository userRepository,
                             CardEnrollRepository cardEnrollRepository,
                             CardEnrollSessionFormatRepository cardEnrollSessionFormatRepository,
                             CardEnrollSessionTypeRepository cardEnrollSessionTypeRepository,
                             EnrollmentStatusRepository enrollmentStatusRepository,
                             SessionFormatRepository sessionFormatRepository,
                             SessionTypeRepository sessionTypeRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.cardEnrollRepository = cardEnrollRepository;
        this.cardEnrollSessionFormatRepository = cardEnrollSessionFormatRepository;
        this.cardEnrollSessionTypeRepository = cardEnrollSessionTypeRepository;
        this.enrollmentStatusRepository = enrollmentStatusRepository;
        this.sessionFormatRepository = sessionFormatRepository;
        this.sessionTypeRepository = sessionTypeRepository;
    }

    public EnrollmentDTO postCardEnroll(EnrollmentDTO enrollmentDTO) {
        Long enrollerId = enrollmentDTO.getEnrollerId();
        Long cardId = enrollmentDTO.getCardId();
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        Optional<User> userOptional = userRepository.findById(enrollerId);
        if (cardOptional.isPresent() && userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getUserId().equals(enrollerId)) {
                Card card = cardOptional.get();
                if (isUniquePair(enrollerId, cardId)) {
                    List<String> sessionFormats = this.getSessionFormats(card, enrollmentDTO);
                    List<String> sessionTypes = this.getSessionTypes(card, enrollmentDTO);
                    if (!sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
                        CardEnroll cardEnroll = this.saveCardEnroll(
                                card,
                                user,
                                enrollmentStatusRepository.findEnrollmentStatusByStatus("requested")
                        );
                        this.saveCardEnrollSessionFormat(cardEnroll, sessionFormats);
                        this.saveCardEnrollSessionType(cardEnroll, sessionTypes);
                        return new EnrollmentDTO(enrollerId, cardId, sessionFormats, sessionTypes);
                    }
                }
            }
        }
        return null;
    }

    private boolean isUniquePair(Long enrollerId, Long cardId) {
        List<CardEnroll> cards = cardEnrollRepository.findByUserId(enrollerId);
        for (CardEnroll cardEnroll : cards) {
            if (cardEnroll.getCardId().equals(cardId)) {
                return false;
            }
        }
        return true;
    }

    private List<String> getSessionFormats(Card card, EnrollmentDTO enrollmentDTO) {
        return new CardSessionFormatConverter(card.getCardSessionFormatsByCardId())
                .stringList()
                .stream()
                .distinct()
                .filter(enrollmentDTO.getSessionFormat()::contains)
                .collect(Collectors.toList());
    }

    private List<String> getSessionTypes(Card card, EnrollmentDTO enrollmentDTO) {
        return new CardSessionTypeConverter(card.getCardSessionTypesByCardId())
                .stringList()
                .stream()
                .distinct()
                .filter(enrollmentDTO.getSessionType()::contains)
                .collect(Collectors.toList());
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
        for (String format : sessionFormats) {
            SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(format);
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
        for (String type : sessionTypes) {
            SessionType sessionType = sessionTypeRepository.findSessionTypeByName(type);
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
}
