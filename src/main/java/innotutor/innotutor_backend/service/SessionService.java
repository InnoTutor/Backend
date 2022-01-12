package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.UserDTO;
import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.card.SessionRatingDTO;
import innotutor.innotutor_backend.dto.card.SubjectDTO;
import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.dto.session.ScheduleDTO;
import innotutor.innotutor_backend.dto.session.SessionDTO;
import innotutor.innotutor_backend.dto.session.sessionsettings.SessionFormatDTO;
import innotutor.innotutor_backend.dto.session.sessionsettings.SessionTypeDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.session.*;
import innotutor.innotutor_backend.entity.user.SessionStudent;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.session.*;
import innotutor.innotutor_backend.repository.user.SessionStudentRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.utility.card.ValidSessionRating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionService {
    private final static String PRIVATE_TYPE = "private";
    private final UserService userService;
    private final StudentsService studentsService;
    private final CardsListService cardsListService;
    private final SessionRepository sessionRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final SessionStudentRepository sessionStudentRepository;
    private final SessionRatingRepository sessionRatingRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final SubjectRepository subjectRepository;

    public List<SessionFormatDTO> getSessionFormats() {
        final List<SessionFormatDTO> sessionFormats = new ArrayList<>();
        for (final SessionFormat format : sessionFormatRepository.findAll()) {
            sessionFormats.add(new SessionFormatDTO(format.getSessionFormatId(), format.getName()));
        }
        return sessionFormats;
    }

    public List<SessionTypeDTO> getSessionTypes() {
        final List<SessionTypeDTO> sessionTypes = new ArrayList<>();
        for (final SessionType type : sessionTypeRepository.findAll()) {
            sessionTypes.add(new SessionTypeDTO(type.getSessionTypeId(), type.getName()));
        }
        return sessionTypes;
    }

    public List<SubjectDTO> getSubjects() {
        final List<SubjectDTO> subjects = new ArrayList<>();
        for (final Subject subject : subjectRepository.findAll()) {
            subjects.add(new SubjectDTO(subject.getSubjectId(), subject.getName()));
        }
        return subjects;
    }

    public List<SubjectDTO> getAvailableServiceSubjects(final Long userId) { //NOPMD - suppressed ReturnEmptyCollectionRatherThanNull - null indicates that such collection doesn't exist
        if (userRepository.findById(userId).isPresent()) {
            return this.getAvailableSubjects(cardsListService.getServices(userId));
        }
        return null;
    }

    public List<SubjectDTO> getAvailableRequestSubjects(final Long userId) { //NOPMD - suppressed ReturnEmptyCollectionRatherThanNull - null indicates that such collection doesn't exist
        if (userRepository.findById(userId).isPresent()) {
            return this.getAvailableSubjects(cardsListService.getRequests(userId));
        }
        return null;
    }

    public SessionDTO postSession(final SessionDTO sessionDTO, final Long userId) {
        sessionDTO.setTutorId(userId);
        final SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(sessionDTO.getSessionFormat());
        final SessionType sessionType = sessionTypeRepository.findSessionTypeByName(sessionDTO.getSessionType());
        final Optional<User> userOptional = userRepository.findById(userId);
        if (sessionDTO.getStartTime().compareTo(LocalDateTime.now()) >= 0
                && userOptional.isPresent()
                && sessionFormat != null
                && sessionType != null) {
            final User tutor = userOptional.get();
            final Optional<Card> cardOptional = tutor.getServicesByUserId().stream()
                    .map(innotutor.innotutor_backend.entity.user.Service::getCardByCardId)
                    .filter(card -> card.getSubjectBySubjectId().getName().equals(sessionDTO.getSubject()))
                    .findAny();
            if (cardOptional.isPresent()) {
                return this.createSession(tutor, cardOptional.get(), sessionDTO, sessionFormat, sessionType);
            }
        }
        return null;
    }

    public Boolean cancelSession(final Long sessionId, final Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (sessionOptional.isPresent() && userOptional.isPresent()) {
            Session session = sessionOptional.get();
            User user = userOptional.get();
            if (session.getEndTime().compareTo(LocalDateTime.now()) >= 0) {
                return session.getTutorId().equals(userId)
                        ? this.cancelSessionTutor(session)
                        : this.cancelSessionStudent(sessionId, user);
            }
            return null;
        }
        return false;
    }

    public List<UserDTO> filterStudentsForSession(final Long tutorId,
                                                  final String specifiedSubject,
                                                  final String specifiedFormat,
                                                  final String specifiedType) {
        List<EnrollmentDTO> students = studentsService.getUserStudentsList(tutorId).getAcceptedStudents();
        if (specifiedSubject != null) {
            students = students.stream()
                    .filter(enrollmentDTO -> cardRepository.findById(enrollmentDTO.getCardId()).orElse(new Card())
                            .getSubjectBySubjectId().getName().equals(specifiedSubject))
                    .collect(Collectors.toList());
        }
        if (specifiedFormat != null) {
            students = students.stream()
                    .filter(enrollmentDTO -> enrollmentDTO.getSessionFormat().contains(specifiedFormat))
                    .collect(Collectors.toList());
        }
        if (specifiedType != null) {
            students = students.stream()
                    .filter(enrollmentDTO -> enrollmentDTO.getSessionType().contains(specifiedType))
                    .collect(Collectors.toList());
        }
        final List<UserDTO> result = new ArrayList<>();
        students.forEach(student -> result.add(userService.getUserById(student.getEnrollerId())));
        return result;
    }

    public ScheduleDTO getSchedule(final Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            Collection<Session> studyingSessions = user.getSessionStudentsByUserId()
                    .stream()
                    .map(SessionStudent::getSessionBySessionId)
                    .collect(Collectors.toList());
            Collection<Session> teachingSessions = user.getSessionsByUserId();
            HashMap<String, List<SessionDTO>> studyingSessionsSeparated
                    = this.separateConductedUpcomingSessions(studyingSessions);
            HashMap<String, List<SessionDTO>> teachingSessionsSeparated
                    = this.separateConductedUpcomingSessions(teachingSessions);
            return new ScheduleDTO(
                    studyingSessionsSeparated.get("conducted"),
                    studyingSessionsSeparated.get("upcoming"),
                    teachingSessionsSeparated.get("conducted"),
                    teachingSessionsSeparated.get("upcoming")
            );
        }
        return null;
    }

    public SessionRatingDTO rateSession(final SessionRatingDTO sessionRatingDTO, final Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionRatingDTO.getSessionId());
        Optional<User> userOptional = userRepository.findById(userId);
        if (sessionOptional.isPresent() && userOptional.isPresent()) {
            Session session = sessionOptional.get();
            if (this.isSessionStudentExist(session, userId)) {
                return this.changeSessionRating(sessionRatingDTO, session, userId, userOptional.get());
            }
        }
        return null;
    }

    public boolean deleteRating(final Long sessionId, final Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (sessionOptional.isPresent() && userOptional.isPresent()) {
            Session session = sessionOptional.get();
            if (this.isSessionStudentExist(session, userId)) {
                return this.deleteSessionRating(session, userId);
            }
        }
        return false;
    }

    private SessionDTO createSession(final User tutor, final Card card, final SessionDTO sessionDTO,
                                     final SessionFormat sessionFormat, final SessionType sessionType) {
        final List<User> students = this.getValidStudents(tutor.getUserId(), sessionDTO.getStudentIDsList(),
                card.getSubjectBySubjectId().getName(), sessionFormat.getName(), sessionType.getName());
        if (!students.isEmpty()) {
            if (PRIVATE_TYPE.equals(sessionType.getName()) && students.size() > 1) {
                return null;
            }
            final Session session = sessionRepository.save(
                    new Session(tutor.getUserId(),
                            card.getSubjectId(),
                            sessionFormat.getSessionFormatId(),
                            sessionType.getSessionTypeId(),
                            sessionDTO.getStartTime(),
                            sessionDTO.getEndTime(),
                            sessionDTO.getDescription(),
                            tutor,
                            card.getSubjectBySubjectId(),
                            sessionFormat,
                            sessionType));
            this.saveSessionStudentList(session, students);
            final List<Long> studentIDsList = new ArrayList<>();
            students.forEach(student -> studentIDsList.add(student.getUserId()));
            return new SessionDTO(session.getSessionId(),
                    tutor.getUserId(),
                    studentIDsList,
                    card.getSubjectBySubjectId().getName(),
                    session.getStartTime(),
                    session.getEndTime(),
                    sessionFormat.getName(),
                    sessionType.getName(),
                    session.getDescription());
        }
        return null;
    }

    private boolean cancelSessionTutor(Session session) {
        sessionRepository.delete(session);
        return true;
    }

    private boolean cancelSessionStudent(Long sessionId, User student) {
        Optional<SessionStudent> sessionStudentToDeleteOptional = student.getSessionStudentsByUserId()
                .stream()
                .filter(sessionStudent -> sessionStudent.getSessionId().equals(sessionId))
                .findAny();
        if (sessionStudentToDeleteOptional.isPresent()) {
            SessionStudent sessionStudentToDelete = sessionStudentToDeleteOptional.get();
            sessionStudentRepository.delete(sessionStudentToDelete);
            return true;
        }
        return false;
    }

    private List<User> getValidStudents(final Long tutorId, final List<Long> studentIDsList, final String subject, final String sessionFormat,
                                        final String sessionType) {
        final List<User> validStudents = new ArrayList<>();
        final List<User> students = new ArrayList<>();
        studentIDsList.forEach(studentId -> userRepository.findById(studentId).ifPresent(students::add));
        for (final User student : students) {
            if (this.filterStudentsForSession(tutorId, subject, sessionFormat, sessionType)
                    .stream().anyMatch(studentDTO -> studentDTO.getUserId().equals(student.getUserId()))) {
                validStudents.add(student);
            }
        }
        return validStudents;
    }

    private void saveSessionStudentList(final Session session, final List<User> students) {
        final List<SessionStudent> sessionStudentList = new ArrayList<>();
        students.forEach(student -> sessionStudentList.add(
                new SessionStudent(session.getSessionId(), student.getUserId(), session, student)));
        sessionStudentRepository.saveAll(sessionStudentList);
    }

    private SessionRatingDTO changeSessionRating(final SessionRatingDTO sessionRatingDTO,
                                                 final Session session,
                                                 final Long userId,
                                                 final User student) {
        Long sessionId = sessionRatingDTO.getSessionId();
        Integer mark = sessionRatingDTO.getMark();
        if (new ValidSessionRating(mark).isValid()) {
            Optional<SessionRating> sessionRatingOptional = session.getSessionRatingsBySessionId()
                    .stream()
                    .filter(sessionRating -> sessionRating.getUserId().equals(userId))
                    .findAny();
            String feedback = sessionRatingDTO.getFeedback();
            SessionRating savedSessionRating;
            if (sessionRatingOptional.isPresent()) {
                SessionRating sessionRating = sessionRatingOptional.get();
                sessionRating.setMark(mark);
                sessionRating.setFeedback(feedback);
                savedSessionRating = sessionRatingRepository.save(sessionRating);
            } else {
                savedSessionRating = sessionRatingRepository.save(
                        new SessionRating(sessionId, userId, mark, feedback, session, student));
            }
            return new SessionRatingDTO(
                    savedSessionRating.getSessionId(),
                    savedSessionRating.getMark(),
                    savedSessionRating.getFeedback());
        } else if (mark == null) {
            this.deleteSessionRating(session, userId);
            return sessionRatingDTO;
        }
        return null;
    }

    private boolean deleteSessionRating(final Session session, final Long studentId) {
        Optional<SessionRating> sessionRatingOptional = session.getSessionRatingsBySessionId()
                .stream()
                .filter(sessionRating -> sessionRating.getUserId().equals(studentId))
                .findAny();
        if (sessionRatingOptional.isPresent()){
            sessionRatingRepository.delete(sessionRatingOptional.get());
            return true;
        }
        return false;
    }

    private boolean isSessionStudentExist(final Session session, final Long studentId) {
        return session.getSessionStudentsBySessionId()
                .stream()
                .anyMatch(sessionStudent -> sessionStudent.getStudentId().equals(studentId));
    }

    private List<SubjectDTO> getAvailableSubjects(final List<CardDTO> userCards) {
        final List<SubjectDTO> result = new ArrayList<>();
        for (final SubjectDTO subject : this.getSubjects()) {
            boolean available = true; //NOPMD - suppressed DataflowAnomalyAnalysis
            for (final CardDTO card : userCards) {
                if (card.getSubject().equals(subject.getName())) {
                    available = false;
                    break;
                }
            }
            if (available) {
                result.add(subject);
            }
        }
        return result;
    }

    private HashMap<String, List<SessionDTO>> separateConductedUpcomingSessions(Collection<Session> sessions) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<SessionDTO> conductedSessions = sessions.stream()
                .filter(session -> session.getEndTime().compareTo(currentDateTime) < 0)
                .map(this::convertSessionToSessionDTO)
                .sorted(Comparator.comparing(SessionDTO::getStartTime).reversed())
                .collect(Collectors.toList());
        List<SessionDTO> upcomingSessions = sessions.stream()
                .filter(session -> session.getEndTime().compareTo(currentDateTime) >= 0)
                .map(this::convertSessionToSessionDTO)
                .sorted(Comparator.comparing(SessionDTO::getStartTime))
                .collect(Collectors.toList());
        HashMap<String, List<SessionDTO>> separatedSessions = new HashMap<>(2);
        separatedSessions.put("conducted", conductedSessions);
        separatedSessions.put("upcoming", upcomingSessions);
        return separatedSessions;
    }

    private SessionDTO convertSessionToSessionDTO(Session session) {
        final List<Long> studentIDsList = new ArrayList<>();
        session.getSessionStudentsBySessionId().forEach(student -> studentIDsList.add(student.getStudentId()));
        return new SessionDTO(
                session.getSessionId(),
                session.getTutorId(),
                studentIDsList,
                session.getSubjectBySubjectId().getName(),
                session.getStartTime(),
                session.getEndTime(),
                session.getSessionFormatBySessionFormatId().getName(),
                session.getSessionTypeBySessionTypeId().getName(),
                session.getDescription());
    }
}
