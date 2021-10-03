package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.DTO.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.DTO.enrollment.RequestedStudentsListDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.user.Request;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.enrollment.EnrollmentStatusRepository;
import innotutor.innotutor_backend.repository.user.RequestRepository;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.utility.session.sessionformat.CardEnrollSessionFormatConverter;
import innotutor.innotutor_backend.utility.session.sessionformat.CardSessionFormatConverter;
import innotutor.innotutor_backend.utility.session.sessiontype.CardEnrollSessionTypeConverter;
import innotutor.innotutor_backend.utility.session.sessiontype.CardSessionTypeConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EnrollmentStatusRepository enrollmentStatusRepository;

    public StudentsService(UserRepository userRepository, RequestRepository requestRepository,
                           EnrollmentStatusRepository enrollmentStatusRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.enrollmentStatusRepository = enrollmentStatusRepository;
    }

    public RequestedStudentsListDTO getUserStudentsList(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Long requestedStatusId = enrollmentStatusRepository.findEnrollmentStatusByStatus("requested").getStatusId();
            Long acceptedStatusId = enrollmentStatusRepository.findEnrollmentStatusByStatus("accepted").getStatusId();
            List<EnrollmentDTO> acceptedStudentsList = this.getStudentsListByStatusId(user, acceptedStatusId);
            acceptedStudentsList.addAll(this.getStudentsToWhomRequested(user, acceptedStatusId));
            List<EnrollmentDTO> newStudentsList = this.getStudentsListByStatusId(user, requestedStatusId);
            return new RequestedStudentsListDTO(newStudentsList, acceptedStudentsList);
        }
        return null;
    }

    private List<EnrollmentDTO> getStudentsListByStatusId(User user, Long statusId) {
        List<EnrollmentDTO> studentsList = new ArrayList<>();
        for (innotutor.innotutor_backend.entity.user.Service service : user.getServicesByUserId()) {
            Card card = service.getCardByCardId();
            for (CardEnroll cardEnroll : card.getCardEnrollsByCardId()) {
                if (cardEnroll.getStatusId().equals(statusId)) {
                    studentsList.add(new EnrollmentDTO(
                            cardEnroll.getCardEnrollId(),
                            cardEnroll.getUserId(),
                            cardEnroll.getCardId(),
                            new CardEnrollSessionFormatConverter(cardEnroll.getCardEnrollSessionFormatsByCardId()).stringList(),
                            new CardEnrollSessionTypeConverter(cardEnroll.getCardEnrollSessionTypesByCardId()).stringList()
                    ));
                }
            }
        }
        return studentsList;
    }

    private List<EnrollmentDTO> getStudentsToWhomRequested(User tutor, Long acceptedStatusId) {
        List<EnrollmentDTO> studentsList = new ArrayList<>();
        for (Request request : requestRepository.findAll()) {
            Card card = request.getCardByCardId();
            for (CardEnroll cardEnroll : card.getCardEnrollsByCardId()) {
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
