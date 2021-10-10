package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.entity.card.Card;

public class CardCreatorId {
    private final transient Card card;

    public CardCreatorId(final Card card) {
        this.card = card;
    }

    public Long creatorId() {
        if (card.getServiceByCardId() != null) {
            return card.getServiceByCardId().getTutorId();
        }
        if (card.getRequestByCardId() != null) {
            return card.getRequestByCardId().getStudentId();
        }
        return null;
    }
}
