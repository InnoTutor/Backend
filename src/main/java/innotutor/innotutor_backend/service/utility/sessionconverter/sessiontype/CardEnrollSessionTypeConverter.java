package innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionType;
import innotutor.innotutor_backend.service.utility.sessionconverter.SessionConverter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CardEnrollSessionTypeConverter implements SessionConverter {
    private final Collection<CardEnrollSessionType> types;

    @Override
    public List<String> stringList() {
        final List<String> formatsNames = new ArrayList<>();
        for (final CardEnrollSessionType type : types) {
            formatsNames.add(type.getSessionTypeBySessionTypeId().getName());
        }
        return formatsNames;
    }
}
