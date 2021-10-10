package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.dto.searcher.TutorCvDTO;
import innotutor.innotutor_backend.dto.searcher.UserCard;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardRating;
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
    private final CardRepository cardRepository;

    public List<TutorCvDTO> getTutorCvDTOList(final String specifiedSubject,
                                              final String specifiedFormat,
                                              final String specifiedType,
                                              final String sorting,
                                              final Long userId) {
        List<TutorCvDTO> tutors = new ArrayList<>();
        for (final UserCard user : this.filterCards(
                new ArrayList<>(this.getAllTutorCvDTOList()),
                userId,
                specifiedSubject,
                specifiedFormat,
                specifiedType)) {
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
                                                            final Long userId) {
        final List<StudentRequestDTO> students = new ArrayList<>();
        for (final UserCard user : this.filterCards(
                new ArrayList<>(this.getAllStudentRequestDTOList()),
                userId,
                specifiedSubject,
                specifiedFormat,
                specifiedType)) {
            students.add((StudentRequestDTO) user);
        }
        return students;
    }

    private List<TutorCvDTO> getAllTutorCvDTOList() {
        final List<TutorCvDTO> tutors = new ArrayList<>();
        for (final Card card : cardRepository.findByHidden(false)) {
            if (card.getServiceByCardId() != null) {
                final Collection<CardRating> ratings = card.getCardRatingsByCardId();
                tutors.add(
                        new TutorCvDTO(
                                card.getServiceByCardId().getTutorId(),
                                card.getCardId(),
                                new AverageCardRating(ratings).averageRating(),
                                ratings.size(),
                                card.getDescription(),
                                card.getSubjectBySubjectId().getName(),
                                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
                        )
                );
            }
        }
        return tutors;
    }

    private List<StudentRequestDTO> getAllStudentRequestDTOList() {
        final List<StudentRequestDTO> students = new ArrayList<>();
        for (final Card card : cardRepository.findByHidden(false)) {
            if (card.getRequestByCardId() != null) {
                students.add(
                        new StudentRequestDTO(
                                card.getRequestByCardId().getStudentId(),
                                card.getCardId(),
                                card.getDescription(),
                                card.getSubjectBySubjectId().getName(),
                                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
                        )
                );
            }
        }
        return students;
    }

    private List<UserCard> filterCards(final List<UserCard> cards,
                                       final Long userId,
                                       final String specifiedSubject,
                                       final String specifiedFormat,
                                       final String specifiedType) {
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
        return result;
    }
}
