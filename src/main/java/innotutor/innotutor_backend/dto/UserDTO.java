package innotutor.innotutor_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String contacts;
    private String description;
    private String picture;
    // Upcoming events
    //private SessionsListDTO sessionsList;
    // You provide help via creating own CV cards
    //private List<CardDTO> servicesList;
    // Students who requested to your CV cards
    //private RequestedStudentsListDTO myStudentsList;
    // You ask for help via creating own Request Card
    //private List<CardDTO> requestsList;
    // Tutors who responded to your Request cards
    //private RespondedTutorsListDTO myTutorsList;
    // Cards to which you assigned or asked to be
    //private AssignedCardsDTO assignedCardsList;
}
