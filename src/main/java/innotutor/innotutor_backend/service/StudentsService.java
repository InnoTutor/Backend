package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.UserDTO;
import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.dto.enrollment.MyStudentDTO;
import innotutor.innotutor_backend.dto.enrollment.RequestedStudentsListDTO;
import innotutor.innotutor_backend.dto.enrollment.RequestedStudentsListInfoDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.enrollment.EnrollmentStatusRepository;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardEnrollSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardEnrollSessionTypeConverter;
import innotutor.innotutor_backend.service.utility.sessionconverter.sessiontype.CardSessionTypeConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentsService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EnrollmentStatusRepository enrollmentStatusRepository;
    private final UserService userService;
    private final CardService cardService;

    public RequestedStudentsListDTO getUserStudentsList(final Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            final Long requestedStatusId = enrollmentStatusRepository.findEnrollmentStatusByStatus("requested").getStatusId();
            final Long acceptedStatusId = enrollmentStatusRepository.findEnrollmentStatusByStatus("accepted").getStatusId();
            final List<EnrollmentDTO> acceptedStudentsList = this.getStudentsListByStatusId(user, acceptedStatusId);
            acceptedStudentsList.addAll(this.getStudentsToWhomRequested(user, acceptedStatusId));
            final List<EnrollmentDTO> newStudentsList = this.getStudentsListByStatusId(user, requestedStatusId);
            return new RequestedStudentsListDTO(newStudentsList, acceptedStudentsList);
        }
        return null;
    }

    public RequestedStudentsListInfoDTO getUserStudentsListFullInfo(final Long userId) {
        RequestedStudentsListDTO students = this.getUserStudentsList(userId);
        if (students != null) {
            List<MyStudentDTO> newStudents = students.getNewStudents().stream()
                    .map(this::convertEnrollmentDTOToEnrollmentInfoDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            List<MyStudentDTO> acceptedStudents = students.getAcceptedStudents().stream()
                    .map(this::convertEnrollmentDTOToEnrollmentInfoDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return new RequestedStudentsListInfoDTO(newStudents, acceptedStudents);
        }
        return null;
    }

    private MyStudentDTO convertEnrollmentDTOToEnrollmentInfoDTO(EnrollmentDTO enrollmentDTO) {
        Long enrollerId = enrollmentDTO.getEnrollerId();
        Long cardId = enrollmentDTO.getCardId();
        UserDTO enroller = userService.getUserById(enrollerId);
        CardDTO card = cardService.getCardById(cardId, enrollerId);
        if (enroller != null && card != null) {
            return new MyStudentDTO(
                    enrollmentDTO.getEnrollmentId(),
                    enrollerId,
                    enroller.getName(),
                    enroller.getSurname(),
                    enroller.getEmail(),
                    enroller.getContacts(),
                    enroller.getDescription(),
                    enroller.getPicture(),
                    cardId,
                    card.getSubject(),
                    card.getRating(),
                    card.getCountVoted(),
                    card.getDescription(),
                    card.isHidden(),
                    enrollmentDTO.getSessionFormat(),
                    enrollmentDTO.getSessionType()
            );
        }
        return null;
    }

    private List<EnrollmentDTO> getStudentsListByStatusId(final User user, final Long statusId) {
        final List<EnrollmentDTO> studentsList = new ArrayList<>();
        user.getServicesByUserId().forEach(service -> service.getCardByCardId().getCardEnrollsByCardId()
                .forEach(cardEnroll -> {
                    if (cardEnroll.getStatusId().equals(statusId)) {
                        studentsList.add(new EnrollmentDTO(
                                cardEnroll.getCardEnrollId(),
                                cardEnroll.getUserId(),
                                cardEnroll.getCardId(),
                                new CardEnrollSessionFormatConverter(cardEnroll.getCardEnrollSessionFormatsByCardId()).stringList(),
                                new CardEnrollSessionTypeConverter(cardEnroll.getCardEnrollSessionTypesByCardId()).stringList()
                        ));
                    }
                }));
        return studentsList;
    }

    private List<EnrollmentDTO> getStudentsToWhomRequested(final User tutor, final Long acceptedStatusId) {
        final List<EnrollmentDTO> studentsList = new ArrayList<>();
        for (final Request request : requestRepository.findAll()) {
            final Card card = request.getCardByCardId();
            for (final CardEnroll cardEnroll : card.getCardEnrollsByCardId()) {
                if (cardEnroll.getUserId().equals(tutor.getUserId()) && cardEnroll.getStatusId().equals(acceptedStatusId)) {
                    studentsList.add(new EnrollmentDTO(
                            cardEnroll.getCardEnrollId(),
                            request.getStudentId(),
                            card.getCardId(),
                            new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                            new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
                    ));
                    break;
                }
            }
        }
        return studentsList;
    }
}
