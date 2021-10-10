package innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat;

import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SessionFormatEntityConverter {
    private final List<String> formats;
    private final SessionFormatRepository sessionFormatRepository;

    public List<SessionFormat> toEntityList() {
        final List<SessionFormat> sessionFormats = new ArrayList<>();
        for (final String formatName : formats) {
            final SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(formatName);
            if (sessionFormat != null) {
                sessionFormats.add(sessionFormat);
            }
        }
        return sessionFormats;
    }
}
