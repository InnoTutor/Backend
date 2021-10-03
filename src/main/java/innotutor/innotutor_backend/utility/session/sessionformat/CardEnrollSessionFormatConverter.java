package innotutor.innotutor_backend.utility.session.sessionformat;

import innotutor.innotutor_backend.entity.card.enrollment.CardEnrollSessionFormat;
import innotutor.innotutor_backend.utility.session.SessionConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CardEnrollSessionFormatConverter implements SessionConverter {
    private final Collection<CardEnrollSessionFormat> formats;

    public CardEnrollSessionFormatConverter(Collection<CardEnrollSessionFormat> formats) {
        this.formats = formats;
    }

    @Override
    public List<String> stringList() {
        List<String> formatsNames = new ArrayList<>();
        for (CardEnrollSessionFormat format : formats) {
            formatsNames.add(format.getSessionFormatBySessionFormatId().getName());
        }
        return formatsNames;
    }
}
