package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardRating;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;

import java.util.Collection;

public class CardDTOCreator {
    private final Card card;
    private final Long creatorId;
    private final SubjectRepository subjectRepository;

    public CardDTOCreator(final Card card, final Long creatorId, final SubjectRepository subjectRepository) {
        this.card = card;
        this.creatorId = creatorId;
        this.subjectRepository = subjectRepository;
    }

    public CardDTO create() {
        final Collection<CardRating> ratings = card.getCardRatingsByCardId();
        return new CardDTO(
                card.getCardId(),
                creatorId,
                subjectRepository.getById(card.getSubjectId()).getName(),
                new AverageCardRating(ratings).averageRating(),
                ratings.size(),
                card.getDescription(),
                card.getHidden(),
                new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
        );
    }
}
