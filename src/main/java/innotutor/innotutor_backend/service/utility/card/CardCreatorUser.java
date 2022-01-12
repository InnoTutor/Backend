package innotutor.innotutor_backend.service.utility.card;

import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.user.User;

public class CardCreatorUser {
    private final transient Card card;

    public CardCreatorUser(final Card card) {
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

    public User creator() {
        if (card.getServiceByCardId() != null) {
            return card.getServiceByCardId().getUserByTutorId();
        }
        if (card.getRequestByCardId() != null) {
            return card.getRequestByCardId().getUserByStudentId();
        }
        return null;
    }
}
