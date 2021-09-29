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

import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.DTO.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.DTO.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.DTO.searcher.TutorCvDTO;
import innotutor.innotutor_backend.DTO.searcher.UserCard;
import innotutor.innotutor_backend.entity.*;
import innotutor.innotutor_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardEnrollRepository cardEnrollRepository;
    @Autowired
    CardEnrollSessionFormatRepository cardEnrollSessionFormatRepository;
    @Autowired
    CardEnrollSessionTypeRepository cardEnrollSessionTypeRepository;
    @Autowired
    EnrollmentStatusRepository enrollmentStatusRepository;
    @Autowired
    SessionFormatRepository sessionFormatRepository;
    @Autowired
    SessionTypeRepository sessionTypeRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    CardSessionFormatRepository cardSessionFormatRepository;
    @Autowired
    CardSessionTypeRepository cardSessionTypeRepository;


    public List<TutorCvDTO> getTutorCvDTOList(String specifiedSubject,
                                              String specifiedFormat,
                                              String specifiedType) {
        List<TutorCvDTO> tutors = new ArrayList<>();
        for (UserCard user : this.filterCards(
                new ArrayList<>(this.getAllTutorCvDTOList()),
                specifiedSubject,
                specifiedFormat,
                specifiedType)) {
            tutors.add((TutorCvDTO) user);
        }
        return tutors;
    }

    public List<StudentRequestDTO> getStudentRequestDTOList(String specifiedSubject,
                                                            String specifiedFormat,
                                                            String specifiedType) {
        List<StudentRequestDTO> students = new ArrayList<>();
        for (UserCard user : this.filterCards(
                new ArrayList<>(this.getAllStudentRequestDTOList()),
                specifiedSubject,
                specifiedFormat,
                specifiedType)) {
            students.add((StudentRequestDTO) user);
        }
        return students;
    }

    public CardDTO postCvCard(CardDTO cardDTO) {
        return createCard(cardDTO, CardType.SERVICE);
    }

    public CardDTO postRequestCard(CardDTO cardDTO) {
        return createCard(cardDTO, CardType.REQUEST);
    }

    public EnrollmentDTO postCardEnroll(EnrollmentDTO enrollmentDTO) {
        Long enrollerId = enrollmentDTO.getEnrollerId();
        Long cardId = enrollmentDTO.getCardId();
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<User> user = userRepository.findById(enrollerId);
        if (card.isPresent() && user.isPresent()) {
            List<String> availableFormats = convertCardSessionFormatsToString(card.get().getCardSessionFormatsByCardId());
            List<String> availableTypes = convertCardSessionTypesToString(card.get().getCardSessionTypesByCardId());
            List<String> sessionFormats = availableFormats.stream()
                    .distinct().filter(enrollmentDTO.getSessionFormat()::contains).collect(Collectors.toList());
            List<String> sessionTypes = availableTypes.stream()
                    .distinct().filter(enrollmentDTO.getSessionType()::contains).collect(Collectors.toList());
            if (!sessionFormats.isEmpty() && !sessionTypes.isEmpty()) {
                CardEnroll cardEnroll = this.saveCardEnroll(
                        card.get(),
                        user.get(),
                        enrollmentStatusRepository.findEnrollmentStatusByStatus("requested")
                );
                this.saveCardEnrollSessionFormat(sessionFormats, cardEnroll);
                this.saveCardEnrollSessionType(sessionTypes, cardEnroll);
                return new EnrollmentDTO(enrollerId, cardId, sessionFormats, sessionTypes);
            }
        }
        return null;
    }

    private List<TutorCvDTO> getAllTutorCvDTOList() {
        List<TutorCvDTO> tutors = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            if (card.getServiceByCardId() != null) {
                Long tutorId = card.getServiceByCardId().getTutorId();
                Long cardId = card.getCardId();
                Double rating = this.calculateAverageCardRating(card.getCardRatingsByCardId());
                String description = card.getDescription();
                String subject = card.getSubjectBySubjectId().getName();
                List<String> sessionFormat = this.convertCardSessionFormatsToString(card.getCardSessionFormatsByCardId());
                List<String> sessionType = this.convertCardSessionTypesToString(card.getCardSessionTypesByCardId());
                tutors.add(new TutorCvDTO(tutorId, cardId, rating, description, subject, sessionFormat, sessionType));
            }
        }
        return tutors;
    }

    private List<StudentRequestDTO> getAllStudentRequestDTOList() {
        List<StudentRequestDTO> students = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            if (card.getRequestByCardId() != null) {
                Long studentId = card.getRequestByCardId().getStudentId();
                Long cardId = card.getCardId();
                String description = card.getDescription();
                String subject = card.getSubjectBySubjectId().getName();
                List<String> sessionFormat = this.convertCardSessionFormatsToString(card.getCardSessionFormatsByCardId());
                List<String> sessionType = this.convertCardSessionTypesToString(card.getCardSessionTypesByCardId());
                students.add(new StudentRequestDTO(studentId, cardId, description, subject, sessionFormat, sessionType));
            }
        }
        return students;
    }

    private List<UserCard> filterCards(List<UserCard> cards,
                                       String specifiedSubject,
                                       String specifiedFormat,
                                       String specifiedType) {
        List<UserCard> result = cards;
        if (specifiedSubject != null) {
            result = result.stream().filter(x -> x.getSubject().equals(specifiedSubject)).collect(Collectors.toList());
        }
        if (specifiedFormat != null) {
            result = result.stream().filter(x -> x.getSessionFormat().contains(specifiedFormat)).collect(Collectors.toList());
        }
        if (specifiedType != null) {
            result = result.stream().filter(x -> x.getSessionType().contains(specifiedType)).collect(Collectors.toList());
        }
        return result;
    }

    private CardDTO createCard(CardDTO cardDTO, CardType type) {
        Optional<User> creator = userRepository.findById(cardDTO.getCreatorId());
        Subject subject = subjectRepository.findSubjectByName(cardDTO.getSubject());
        List<SessionFormat> sessionFormat = this.convertSessionFormatToEntity(cardDTO.getSessionFormat());
        List<SessionType> sessionType = this.convertSessionTypeToEntity(cardDTO.getSessionType());
        if (creator.isPresent() && subject != null && !sessionFormat.isEmpty() && !sessionType.isEmpty()) {
            Card card = cardRepository.save(new Card(subject.getSubjectId(), cardDTO.getDescription(), subject));
            switch (type) {
                case SERVICE:
                    serviceRepository.save(
                            new innotutor.innotutor_backend.entity.Service(creator.get().getUserId(), card.getCardId(), creator.get(), card)
                    );
                    break;
                case REQUEST:
                    requestRepository.save(new Request(creator.get().getUserId(), card.getCardId(), creator.get(), card));
                    break;
            }
            for (SessionFormat format : sessionFormat) {
                cardSessionFormatRepository.save(
                        new CardSessionFormat(
                                card.getCardId(),
                                format.getSessionFormatId(),
                                card,
                                format
                        )
                );
            }
            for (SessionType sType : sessionType) {
                cardSessionTypeRepository.save(
                        new CardSessionType(
                                card.getCardId(),
                                sType.getSessionTypeId(),
                                card,
                                sType
                        )
                );
            }
            return new CardDTO(
                    card.getCardId(),
                    creator.get().getUserId(),
                    subject.getName(),
                    null,
                    cardDTO.getDescription(),
                    this.convertSessionFormatsToString(sessionFormat),
                    this.convertSessionTypesToString(sessionType));
        }
        return null;
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

    private void saveCardEnrollSessionFormat(List<String> sessionFormats, CardEnroll cardEnroll) {
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

    private void saveCardEnrollSessionType(List<String> sessionTypes, CardEnroll cardEnroll) {
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

    private List<String> convertCardSessionFormatsToString(Collection<CardSessionFormat> formats) {
        List<String> formatsNames = new ArrayList<>();
        for (CardSessionFormat format : formats) {
            formatsNames.add(format.getSessionFormatBySessionFormatId().getName());
        }
        return formatsNames;
    }

    private List<String> convertCardSessionTypesToString(Collection<CardSessionType> types) {
        List<String> formatsNames = new ArrayList<>();
        for (CardSessionType type : types) {
            formatsNames.add(type.getSessionTypeBySessionTypeId().getName());
        }
        return formatsNames;
    }

    private List<String> convertSessionFormatsToString(List<SessionFormat> sessionFormat) {
        List<String> formatsNames = new ArrayList<>();
        for (SessionFormat format : sessionFormat) {
            formatsNames.add(format.getName());
        }
        return formatsNames;
    }

    private List<String> convertSessionTypesToString(List<SessionType> sessionType) {
        List<String> typesNames = new ArrayList<>();
        for (SessionType type : sessionType) {
            typesNames.add(type.getName());
        }
        return typesNames;
    }

    private List<SessionFormat> convertSessionFormatToEntity(List<String> formats) {
        List<SessionFormat> sessionFormats = new ArrayList<>();
        for (String formatName : formats) {
            SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(formatName);
            if (sessionFormat != null) {
                sessionFormats.add(sessionFormat);
            }
        }
        return sessionFormats;
    }

    private List<SessionType> convertSessionTypeToEntity(List<String> types) {
        List<SessionType> sessionTypes = new ArrayList<>();
        for (String typeName : types) {
            SessionType sessionType = sessionTypeRepository.findSessionTypeByName(typeName);
            if (sessionType != null) {
                sessionTypes.add(sessionType);
            }
        }
        return sessionTypes;
    }

    private Double calculateAverageCardRating(Collection<CardRating> cardRatings) {
        if (cardRatings.size() == 0) {
            return null;
        }
        double sum = 0;
        for (CardRating rating : cardRatings) {
            sum += rating.getMark();
        }
        return sum / cardRatings.size();
    }
}
