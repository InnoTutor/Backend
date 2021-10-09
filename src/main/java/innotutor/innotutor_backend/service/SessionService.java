/*
MIT License

Copyright (c) 2021 InnoTutor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.DTO.UserDTO;
import innotutor.innotutor_backend.DTO.card.CardDTO;
import innotutor.innotutor_backend.DTO.card.SubjectDTO;
import innotutor.innotutor_backend.DTO.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.DTO.session.SessionDTO;
import innotutor.innotutor_backend.DTO.session.sessionsettings.SessionFormatDTO;
import innotutor.innotutor_backend.DTO.session.sessionsettings.SessionTypeDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.session.Session;
import innotutor.innotutor_backend.entity.session.SessionFormat;
import innotutor.innotutor_backend.entity.session.SessionType;
import innotutor.innotutor_backend.entity.session.Subject;
import innotutor.innotutor_backend.entity.user.SessionStudent;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.CardRepository;
import innotutor.innotutor_backend.repository.session.SessionFormatRepository;
import innotutor.innotutor_backend.repository.session.SessionRepository;
import innotutor.innotutor_backend.repository.session.SessionTypeRepository;
import innotutor.innotutor_backend.repository.session.SubjectRepository;
import innotutor.innotutor_backend.repository.user.SessionStudentRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionService {
    private final CardsListService cardsListService;
    private final UserService userService;
    private final SessionRepository sessionRepository;
    private final SessionFormatRepository sessionFormatRepository;
    private final SessionTypeRepository sessionTypeRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SessionStudentRepository sessionStudentRepository;
    private final CardRepository cardRepository;
    private final StudentsService studentsService;

    public List<SessionFormatDTO> getSessionFormats() {
        List<SessionFormatDTO> sessionFormats = new ArrayList<>();
        for (SessionFormat format : sessionFormatRepository.findAll()) {
            sessionFormats.add(new SessionFormatDTO(format.getSessionFormatId(), format.getName()));
        }
        return sessionFormats;
    }

    public List<SessionTypeDTO> getSessionTypes() {
        List<SessionTypeDTO> sessionTypes = new ArrayList<>();
        for (SessionType type : sessionTypeRepository.findAll()) {
            sessionTypes.add(new SessionTypeDTO(type.getSessionTypeId(), type.getName()));
        }
        return sessionTypes;
    }

    public List<SubjectDTO> getSubjects() {
        List<SubjectDTO> subjects = new ArrayList<>();
        for (Subject subject : subjectRepository.findAll()) {
            subjects.add(new SubjectDTO(subject.getSubjectId(), subject.getName()));
        }
        return subjects;
    }

    public List<SubjectDTO> getAvailableServiceSubjects(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return this.getAvailableSubjects(cardsListService.getServices(userId));
        }
        return null;
    }

    public List<SubjectDTO> getAvailableRequestSubjects(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return this.getAvailableSubjects(cardsListService.getRequests(userId));
        }
        return null;
    }

    public SessionDTO postSession(SessionDTO sessionDTO) {
        SessionFormat sessionFormat = sessionFormatRepository.findSessionFormatByName(sessionDTO.getSessionFormat());
        SessionType sessionType = sessionTypeRepository.findSessionTypeByName(sessionDTO.getSessionType());
        Optional<User> userOptional = userRepository.findById(sessionDTO.getTutorId());
        if (userOptional.isPresent() && sessionFormat != null && sessionType != null) {
            User tutor = userOptional.get();
            Optional<Card> cardOptional = tutor.getServicesByUserId().stream()
                    .map(innotutor.innotutor_backend.entity.user.Service::getCardByCardId)
                    .filter(card -> card.getSubjectBySubjectId().getName().equals(sessionDTO.getSubject()))
                    .findAny();
            if (cardOptional.isPresent()) {
                return this.createSession(tutor, cardOptional.get(), sessionDTO, sessionFormat, sessionType);
            }
        }
        return null;
    }

    public List<UserDTO> filterStudentsForSession(Long tutorId,
                                                  String specifiedSubject,
                                                  String specifiedFormat,
                                                  String specifiedType) {
        List<EnrollmentDTO> students = studentsService.getUserStudentsList(tutorId).getAcceptedStudentsList();
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
        List<UserDTO> result = new ArrayList<>();
        students.forEach(student -> result.add(userService.getUserById(student.getEnrollerId())));
        return result;
    }

    private SessionDTO createSession(User tutor, Card card, SessionDTO sessionDTO,
                                     SessionFormat sessionFormat, SessionType sessionType) {
        List<User> students = this.getValidStudents(tutor.getUserId(), sessionDTO.getStudentIDsList(),
                card.getSubjectBySubjectId().getName(), sessionFormat.getName(), sessionType.getName());
        if (!students.isEmpty()) {
            if ("private".equals(sessionType.getName()) && students.size() > 1) {
                return null;
            }
            Session session = sessionRepository.save(
                    new Session(tutor.getUserId(),
                            card.getSubjectId(),
                            sessionFormat.getSessionFormatId(),
                            sessionType.getSessionTypeId(),
                            sessionDTO.getDate(),
                            sessionDTO.getStartTime(),
                            sessionDTO.getEndTime(),
                            sessionDTO.getDescription(),
                            tutor,
                            card.getSubjectBySubjectId(),
                            sessionFormat,
                            sessionType));
            this.saveSessionStudentList(session, students);
            List<Long> studentIDsList = new ArrayList<>();
            students.forEach(student -> studentIDsList.add(student.getUserId()));
            return new SessionDTO(session.getSessionId(),
                    tutor.getUserId(),
                    studentIDsList,
                    card.getSubjectBySubjectId().getName(),
                    session.getDate(),
                    session.getStartTime(),
                    session.getEndTime(),
                    sessionFormat.getName(),
                    sessionType.getName(),
                    session.getDescription());
        }
        return null;
    }

    private List<User> getValidStudents(Long tutorId, List<Long> studentIDsList, String subject, String sessionFormat,
                                        String sessionType) {
        List<User> validStudents = new ArrayList<>();
        List<UserDTO> allTutorStudents = this.filterStudentsForSession(tutorId, subject, sessionFormat, sessionType); //NOPMD - suppressed DataflowAnomalyAnalysis
        List<User> students = new ArrayList<>();
        studentIDsList.forEach(studentId -> userRepository.findById(studentId).ifPresent(students::add));
        for (User student : students) {
            if (allTutorStudents.stream().anyMatch(studentDTO -> studentDTO.getUserId().equals(student.getUserId()))) {
                validStudents.add(student);
            }
        }
        return validStudents;
    }

    private void saveSessionStudentList(Session session, List<User> students) {
        List<SessionStudent> sessionStudentList = new ArrayList<>();
        students.forEach(student -> sessionStudentList.add(new SessionStudent(session.getSessionId(), student.getUserId(),
                session, student)));
        sessionStudentRepository.saveAll(sessionStudentList);
    }

    private List<SubjectDTO> getAvailableSubjects(List<CardDTO> userCards) {
        List<SubjectDTO> result = new ArrayList<>();
        for (SubjectDTO subject : this.getSubjects()) {
            boolean available = true; //NOPMD - suppressed DataflowAnomalyAnalysis
            for (CardDTO card : userCards) {
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
}
