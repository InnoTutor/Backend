package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.dto.searcher.TutorCvDTO;
import innotutor.innotutor_backend.dto.searcher.UserCard;
import innotutor.innotutor_backend.entity.card.CardRating;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.service.utility.card.AverageCardRating;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearcherService {
    private final CardEnrollService cardEnrollService;
    private final CardRepository cardRepository;

    public List<TutorCvDTO> getTutorCvDTOList(final String specifiedSubject,
                                              final String specifiedFormat,
                                              final String specifiedType,
                                              final String sorting,
                                              final Boolean requested,
                                              final Long userId) {
        List<TutorCvDTO> tutors = new ArrayList<>();
        for (final UserCard user : this.filterCards(
                new ArrayList<>(this.getAllTutorCvDTOList(userId)),
                userId,
                specifiedSubject,
                specifiedFormat,
                specifiedType,
                requested)) {
            tutors.add((TutorCvDTO) user);
        }
        if (sorting != null) {
            final Map<Boolean, List<TutorCvDTO>> tutorsSplitted =
                    tutors.stream().collect(Collectors.partitioningBy(tutor -> tutor.getRating() != null));
            switch (sorting) {
                case "ascending":
                    tutorsSplitted.get(true).sort(Comparator.comparing(TutorCvDTO::getRating));
                    break;
                case "descending":
                    tutorsSplitted.get(true).sort(Comparator.comparing(TutorCvDTO::getRating).reversed());
                    break;
                default:
                    break;
            }
            tutors = tutorsSplitted.get(true);
            tutors.addAll(tutorsSplitted.get(false));
        }
        return tutors;
    }

    public List<StudentRequestDTO> getStudentRequestDTOList(final String specifiedSubject,
                                                            final String specifiedFormat,
                                                            final String specifiedType,
                                                            final Boolean offered,
                                                            final Long userId) {
        final List<StudentRequestDTO> students = new ArrayList<>();
        for (final UserCard user : this.filterCards(
                new ArrayList<>(this.getAllStudentRequestDTOList(userId)),
                userId,
                specifiedSubject,
                specifiedFormat,
                specifiedType,
                offered)) {
            students.add((StudentRequestDTO) user);
        }
        return students;
    }

    private List<TutorCvDTO> getAllTutorCvDTOList(final Long userId) {
        final List<TutorCvDTO> tutors = new ArrayList<>();
        cardRepository.findByHidden(false).forEach(card -> {
            if (card.getServiceByCardId() != null) {
                final Collection<CardRating> ratings = card.getCardRatingsByCardId();
                final User tutor = card.getServiceByCardId().getUserByTutorId();
                final Long cardId = card.getCardId();
                tutors.add(
                        new TutorCvDTO(
                                tutor.getUserId(),
                                tutor.getName(),
                                tutor.getSurname(),
                                cardId,
                                new AverageCardRating(ratings).averageRating(),
                                ratings.size(),
                                card.getDescription(),
                                card.getSubjectBySubjectId().getName(),
                                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList(),
                                cardEnrollService.isEnrolled(cardId, userId)
                        )
                );
            }
        });
        return tutors;
    }

    private List<StudentRequestDTO> getAllStudentRequestDTOList(final Long userId) {
        final List<StudentRequestDTO> students = new ArrayList<>();
        cardRepository.findByHidden(false).forEach(card -> {
            if (card.getRequestByCardId() != null) {
                final User student = card.getRequestByCardId().getUserByStudentId();
                final Long cardId = card.getCardId();
                students.add(
                        new StudentRequestDTO(
                                student.getUserId(),
                                student.getName(),
                                student.getSurname(),
                                cardId,
                                card.getDescription(),
                                card.getSubjectBySubjectId().getName(),
                                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList(),
                                cardEnrollService.isEnrolled(cardId, userId)
                        )
                );
            }
        });
        return students;
    }

    private List<UserCard> filterCards(final List<UserCard> cards,
                                       final Long userId,
                                       final String specifiedSubject,
                                       final String specifiedFormat,
                                       final String specifiedType,
                                       final Boolean requested) {
        List<UserCard> result = cards.stream().filter(userCard -> !userCard.getCreatorId().equals(userId))
                .collect(Collectors.toList());
        if (specifiedSubject != null) {
            result = result.stream().filter(userCard -> userCard.getSubject().equals(specifiedSubject))
                    .collect(Collectors.toList());
        }
        if (specifiedFormat != null) {
            result = result.stream().filter(userCard -> userCard.getSessionFormat().contains(specifiedFormat))
                    .collect(Collectors.toList());
        }
        if (specifiedType != null) {
            result = result.stream().filter(userCard -> userCard.getSessionType().contains(specifiedType))
                    .collect(Collectors.toList());
        }
        if (requested != null) {
            result = result.stream().filter(userCard -> userCard.isEnrolled().equals(requested))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
