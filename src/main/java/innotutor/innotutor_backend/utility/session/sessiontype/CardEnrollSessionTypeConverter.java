package innotutor.innotutor_backend.utility.session.sessiontype;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionType;
import innotutor.innotutor_backend.utility.session.SessionConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CardEnrollSessionTypeConverter implements SessionConverter {
    private final Collection<CardEnrollSessionType> types;

    public CardEnrollSessionTypeConverter(Collection<CardEnrollSessionType> types) {
        this.types = types;
    }

    @Override
    public List<String> stringList() {
        List<String> formatsNames = new ArrayList<>();
        for (CardEnrollSessionType type : types) {
            formatsNames.add(type.getSessionTypeBySessionTypeId().getName());
        }
        return formatsNames;
    }
}
