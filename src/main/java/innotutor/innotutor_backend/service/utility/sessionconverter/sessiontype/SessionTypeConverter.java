package innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype;

import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.service.utility.sessionconverter.SessionConverter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SessionTypeConverter implements SessionConverter {
    private final List<SessionType> sessionType;

    @Override
    public List<String> stringList() {
        final List<String> typesNames = new ArrayList<>();
        for (final SessionType type : sessionType) {
            typesNames.add(type.getName());
        }
        return typesNames;
    }
}
