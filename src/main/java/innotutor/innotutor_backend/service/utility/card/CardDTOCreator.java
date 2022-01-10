package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.dto.searcher.TutorCvDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardRating;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;

import java.util.Collection;

public class CardDTOCreator {
    private final transient Card card;
    private final transient Long creatorId;
    private final transient CardEnrollService cardEnrollService;
    private final transient Long userId;

    public CardDTOCreator(final Card card, final Long creatorId,
                          final CardEnrollService cardEnrollService, final Long userId) {
        this.card = card;
        this.creatorId = creatorId;
        this.cardEnrollService = cardEnrollService;
        this.userId = userId;
    }

    public CardDTO create() {
        final Collection<CardRating> ratings = card.getCardRatingsByCardId();
        return new CardDTO(
                card.getCardId(),
                creatorId,
                card.getSubjectBySubjectId().getName(),
                new AverageCardRating(ratings).averageRating(),
                ratings.size(),
                card.getDescription(),
                card.getHidden(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
        );
    }

    public TutorCvDTO createTutorCvDTO() {
        final Collection<CardRating> ratings = card.getCardRatingsByCardId();
        final User tutor = card.getServiceByCardId().getUserByTutorId();
        final Long cardId = card.getCardId();
        return new TutorCvDTO(
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
        );
    }

    public StudentRequestDTO createStudentRequestDTO() {
        final User student = card.getRequestByCardId().getUserByStudentId();
        final Long cardId = card.getCardId();
        return new StudentRequestDTO(
                student.getUserId(),
                student.getName(),
                student.getSurname(),
                cardId,
                card.getDescription(),
                card.getSubjectBySubjectId().getName(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList(),
                cardEnrollService.isEnrolled(cardId, userId)
        );
    }
}
