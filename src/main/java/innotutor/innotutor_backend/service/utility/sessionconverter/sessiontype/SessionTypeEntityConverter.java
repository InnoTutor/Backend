package innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype;

import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SessionTypeEntityConverter {

    private final List<String> types;
    private final SessionTypeRepository sessionTypeRepository;

    public List<SessionType> toEntityList() {
        final List<SessionType> sessionTypes = new ArrayList<>();
        for (final String typeName : types) {
            final SessionType sessionType = sessionTypeRepository.findSessionTypeByName(typeName);
            if (sessionType != null) {
                sessionTypes.add(sessionType);
            }
        }
        return sessionTypes;
    }
}
