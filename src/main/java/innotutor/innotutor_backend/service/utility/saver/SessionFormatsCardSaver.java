package innotutor.innotutor_backend.service.utility.saver;

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.CardSessionFormat;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.repository.card.CardSessionFormatRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SessionFormatsCardSaver {
    private final Card card;
    private final List<SessionFormat> sessionFormats;
    private final CardSessionFormatRepository cardSessionFormatRepository;

    public Card save() {
        final Collection<CardSessionFormat> cardSessionFormatsByCardId = new ArrayList<>();
        for (final SessionFormat format : sessionFormats) {
            final CardSessionFormat cardSessionFormat = new CardSessionFormat(
                    card.getCardId(), format.getSessionFormatId(), card, format
            );
            cardSessionFormatRepository.save(cardSessionFormat);
            cardSessionFormatsByCardId.add(cardSessionFormat);
        }
        card.setCardSessionFormatsByCardId(cardSessionFormatsByCardId);
        return card;
    }
}
