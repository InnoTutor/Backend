package innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat;

import innotutor.innotutor_backend.entity.card.CardSessionFormat;
import innotutor.innotutor_backend.service.utility.sessionconverter.SessionConverter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CardSessionFormatConverter implements SessionConverter {
    private final Collection<CardSessionFormat> formats;

    @Override
    public List<String> stringList() {
        final List<String> formatsNames = new ArrayList<>();
        for (final CardSessionFormat format : formats) {
            formatsNames.add(format.getSessionFormatBySessionFormatId().getName());
        }
        return formatsNames;
    }
}
