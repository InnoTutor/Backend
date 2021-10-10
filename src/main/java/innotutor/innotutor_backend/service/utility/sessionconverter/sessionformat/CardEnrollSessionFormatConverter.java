package innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionFormat;
import innotutor.innotutor_backend.service.utility.sessionconverter.SessionConverter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CardEnrollSessionFormatConverter implements SessionConverter {
    private final Collection<CardEnrollSessionFormat> formats;

    @Override
    public List<String> stringList() {
        final List<String> formatsNames = new ArrayList<>();
        for (final CardEnrollSessionFormat format : formats) {
            formatsNames.add(format.getSessionFormatBySessionFormatId().getName());
        }
        return formatsNames;
    }
}
