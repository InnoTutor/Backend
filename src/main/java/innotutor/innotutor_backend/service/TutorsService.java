package innotutor.innotutor_backend.service;

import innotutor.innotutor_backend.dto.UserDTO;
import innotutor.innotutor_backend.dto.card.CardDTO;
import innotutor.innotutor_backend.dto.enrollment.EnrollmentDTO;
import innotutor.innotutor_backend.dto.enrollment.MyTutorDTO;
import innotutor.innotutor_backend.dto.enrollment.RespondedTutorsListDTO;
import innotutor.innotutor_backend.dto.enrollment.RespondedTutorsListInfoDTO;
import innotutor.innotutor_backend.entity.card.Card;
import innotutor.innotutor_backend.entity.card.enrollment.CardEnroll;
import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.card.enrollment.EnrollmentStatusRepository;
import innotutor.innotutor_backend.repository.user.ServiceRepository;
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
public class TutorsService {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private final EnrollmentStatusRepository enrollmentStatusRepository;
    private final UserService userService;
    private final CardService cardService;

    public RespondedTutorsListDTO getUserTutorsList(final Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            final List<EnrollmentDTO> newTutorsList = this.getNewTutorsList(user);
            final List<EnrollmentDTO> acceptedTutorsList = this.getAcceptedTutorsList(user);
            return new RespondedTutorsListDTO(newTutorsList, acceptedTutorsList);
        }
        return null;
    }

    public RespondedTutorsListInfoDTO getUserTutorsListFullInfo(final Long userId) {
        RespondedTutorsListDTO tutors = this.getUserTutorsList(userId);
        if (tutors != null) {
            List<MyTutorDTO> newTutors = tutors.getNewTutors().stream()
                    .map(this::convertEnrollmentDTOToEnrollmentInfoDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            List<MyTutorDTO> acceptedTutors = tutors.getAcceptedTutors().stream()
                    .map(this::convertEnrollmentDTOToEnrollmentInfoDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return new RespondedTutorsListInfoDTO(newTutors, acceptedTutors);
        }
        return null;
    }

    private List<EnrollmentDTO> getNewTutorsList(User user) {
        final Long requestedStatusId = enrollmentStatusRepository.findEnrollmentStatusByStatus("requested").getStatusId();
        return this.getTutorsListByStatusId(user, requestedStatusId);
    }

    private List<EnrollmentDTO> getAcceptedTutorsList(User user) {
        final Long acceptedStatusId = enrollmentStatusRepository.findEnrollmentStatusByStatus("accepted").getStatusId();
        final List<EnrollmentDTO> acceptedTutorsList = this.getTutorsListByStatusId(user, acceptedStatusId);
        acceptedTutorsList.addAll(this.getTutorsToWhomRequested(user, acceptedStatusId));
        return acceptedTutorsList;
    }

    private List<EnrollmentDTO> getTutorsListByStatusId(final User user, final Long statusId) {
        final List<EnrollmentDTO> tutorsList = new ArrayList<>();
        user.getRequestsByUserId().forEach(request -> request.getCardByCardId().getCardEnrollsByCardId()
                .forEach(cardEnroll -> {
                    if (cardEnroll.getStatusId().equals(statusId)) {
                        tutorsList.add(new EnrollmentDTO(
                                cardEnroll.getCardEnrollId(),
                                cardEnroll.getUserId(),
                                cardEnroll.getCardId(),
                                cardEnroll.getDescription(),
                                new CardEnrollSessionFormatConverter(cardEnroll.getCardEnrollSessionFormatsByCardId()).stringList(),
                                new CardEnrollSessionTypeConverter(cardEnroll.getCardEnrollSessionTypesByCardId()).stringList()
                        ));
                    }
                }));
        return tutorsList;
    }

    private List<EnrollmentDTO> getTutorsToWhomRequested(final User student, final Long acceptedStatusId) {
        final List<EnrollmentDTO> tutorsList = new ArrayList<>();
        for (final innotutor.innotutor_backend.entity.user.Service service : serviceRepository.findAll()) {
            final Card card = service.getCardByCardId();
            for (final CardEnroll cardEnroll : card.getCardEnrollsByCardId()) {
                if (cardEnroll.getUserId().equals(student.getUserId()) && cardEnroll.getStatusId().equals(acceptedStatusId)) {
                    tutorsList.add(new EnrollmentDTO(
                            cardEnroll.getCardEnrollId(),
                            service.getTutorId(),
                            card.getCardId(),
                            cardEnroll.getDescription(),
                            new CardSessionFormatConverter(card.getCardSessionFormatsByCardId()).stringList(),
                            new CardSessionTypeConverter(card.getCardSessionTypesByCardId()).stringList()
                    ));
                    break;
                }
            }
        }
        return tutorsList;
    }

    private MyTutorDTO convertEnrollmentDTOToEnrollmentInfoDTO(EnrollmentDTO enrollmentDTO) {
        Long enrollerId = enrollmentDTO.getEnrollerId();
        Long cardId = enrollmentDTO.getCardId();
        UserDTO enroller = userService.getUserById(enrollerId);
        CardDTO card = cardService.getCardById(cardId, enrollerId);
        if (enroller != null && card != null) {
            return new MyTutorDTO(
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
                    card.getDescription(),
                    card.isHidden(),
                    enrollmentDTO.getDescription(),
                    enrollmentDTO.getSessionFormat(),
                    enrollmentDTO.getSessionType()
            );
        }
        return null;
    }
}
