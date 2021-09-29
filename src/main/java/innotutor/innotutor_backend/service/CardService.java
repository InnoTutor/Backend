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

    public EnrollmentDTO postCardEnroll(EnrollmentDTO enrollmentDTO) {
        Long enrollerId = enrollmentDTO.getEnrollerId();
        Long cardId = enrollmentDTO.getCardId();
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<User> user = userRepository.findById(enrollerId);
        if (card.isPresent() && user.isPresent()) {
            List<String> availableFormats = convertSessionFormatsToString(card.get().getCardSessionFormatsByCardId());
            List<String> availableTypes = convertSessionTypesToString(card.get().getCardSessionTypesByCardId());
            List<String> sessionFormats = getIntersection(availableFormats, enrollmentDTO.getSessionFormat());
            List<String> sessionTypes = getIntersection(availableTypes, enrollmentDTO.getSessionType());
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
            if (!card.getServicesByCardId().isEmpty()) {
                Long tutorId = card.getServicesByCardId().iterator().next().getTutorId();
                Long cardId = card.getCardId();
                Double rating = this.calculateAverageRating(card.getCardRatingsByCardId());
                String description = card.getDescription();
                String subject = card.getSubjectBySubjectId().getName();
                List<String> sessionFormat = this.convertSessionFormatsToString(card.getCardSessionFormatsByCardId());
                List<String> sessionType = this.convertSessionTypesToString(card.getCardSessionTypesByCardId());
                tutors.add(new TutorCvDTO(tutorId, cardId, rating, description, subject, sessionFormat, sessionType));
            }
        }
        return tutors;
    }

    private List<StudentRequestDTO> getAllStudentRequestDTOList() {
        List<StudentRequestDTO> students = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            if (!card.getRequestsByCardId().isEmpty()) {
                Long studentId = card.getRequestsByCardId().iterator().next().getStudentId();
                Long cardId = card.getCardId();
                String description = card.getDescription();
                String subject = card.getSubjectBySubjectId().getName();
                List<String> sessionFormat = this.convertSessionFormatsToString(card.getCardSessionFormatsByCardId());
                List<String> sessionType = this.convertSessionTypesToString(card.getCardSessionTypesByCardId());
                students.add(new StudentRequestDTO(studentId, cardId, description, subject, sessionFormat, sessionType));
            }
        }
        return students;
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

    private List<String> getIntersection(List<String> firstList, List<String> secondList) {
        return firstList.stream().distinct().filter(secondList::contains).collect(Collectors.toList());
    }

    private List<String> convertSessionFormatsToString(Collection<CardSessionFormat> formats) {
        List<String> formatsNames = new ArrayList<>();
        for (CardSessionFormat format : formats) {
            formatsNames.add(format.getSessionFormatBySessionFormatId().getName());
        }
        return formatsNames;
    }

    private List<String> convertSessionTypesToString(Collection<CardSessionType> types) {
        List<String> formatsNames = new ArrayList<>();
        for (CardSessionType type : types) {
            formatsNames.add(type.getSessionTypeBySessionTypeId().getName());
        }
        return formatsNames;
    }

    private Double calculateAverageRating(Collection<CardRating> cardRatings) {
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
