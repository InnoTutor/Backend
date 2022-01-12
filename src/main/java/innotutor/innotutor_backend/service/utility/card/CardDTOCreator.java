package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.searcher.StudentRequestDTO;
import innotutor.innotutor_backend.dto.searcher.TutorCvDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.service.CardEnrollService;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;

public class CardDTOCreator {
    private final transient Card card;
    private final transient User creator;
    private final transient CardEnrollService cardEnrollService;
    private final transient Long userId;

    public CardDTOCreator(final Card card, final User creator,
                          final CardEnrollService cardEnrollService, final Long userId) {
        this.card = card;
        this.creator = creator;
        this.cardEnrollService = cardEnrollService;
        this.userId = userId;
    }

    public CardDTO create() {
        final Ratings ratings = new Ratings(creator, card.getSubjectId());
        return new CardDTO(
                card.getCardId(),
                creator.getUserId(),
                card.getSubjectBySubjectId().getName(),
                ratings.averageRating(),
                ratings.countVoted(),
                card.getDescription(),
                card.getHidden(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
        );
    }

    public TutorCvDTO createTutorCvDTO() {
        final Long cardId = card.getCardId();
        final Ratings ratings = new Ratings(creator, card.getSubjectId());
        return new TutorCvDTO(
                creator.getUserId(),
                creator.getName(),
                creator.getSurname(),
                cardId,
                ratings.averageRating(),
                ratings.countVoted(),
                card.getDescription(),
                card.getSubjectBySubjectId().getName(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList(),
                cardEnrollService.isEnrolled(cardId, userId)
        );
    }

    public StudentRequestDTO createStudentRequestDTO() {
        final Long cardId = card.getCardId();
        return new StudentRequestDTO(
                creator.getUserId(),
                creator.getName(),
                creator.getSurname(),
                cardId,
                card.getDescription(),
                card.getSubjectBySubjectId().getName(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList(),
                cardEnrollService.isEnrolled(cardId, userId)
        );
    }
}
