package innotutor.innotutor_backend.service.utility.saver;

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardSessionType;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.repository.card.CardSessionTypeRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor

public class SessionTypesCardSaver {
    private final Card card;
    private final List<SessionType> sessionTypes;
    private final CardSessionTypeRepository cardSessionTypeRepository;

    public Card save() {
        final Collection<CardSessionType> cardSessionTypesByCardId = new ArrayList<>();
        for (final SessionType type : sessionTypes) {
            final CardSessionType cardSessionType = new CardSessionType(card.getCardId(), type.getSessionTypeId(), card, type);
            cardSessionTypeRepository.save(cardSessionType);
            cardSessionTypesByCardId.add(cardSessionType);
        }
        card.setCardSessionTypesByCardId(cardSessionTypesByCardId);
        return card;
    }
}
