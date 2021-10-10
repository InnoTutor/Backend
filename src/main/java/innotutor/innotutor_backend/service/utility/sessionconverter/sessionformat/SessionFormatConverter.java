package innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat;

import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.service.utility.sessionconverter.SessionConverter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SessionFormatConverter implements SessionConverter {

    private final List<SessionFormat> sessionFormat;

    @Override
    public List<String> stringList() {
        final List<String> formatsNames = new ArrayList<>();
        for (final SessionFormat format : sessionFormat) {
            formatsNames.add(format.getName());
        }
        return formatsNames;
    }
}
