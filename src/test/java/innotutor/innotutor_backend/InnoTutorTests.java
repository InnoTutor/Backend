package innotutor.innotutor_backend;

import innotutor.innotutor_backend.entity.user.User;
import innotutor.innotutor_backend.repository.user.UserRepository;
import innotutor.innotutor_backend.service.SessionService;
import innotutor.innotutor_backend.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.SQLException;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class InnoTutorTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;


    @Test
    @AfterAll
    void testDeleteFromDb() {
        jdbcTemplate.update("DELETE FROM public.user WHERE email = ?", "testing@mail.ru");
        Assertions.assertEquals(userRepository.findByEmail("testing@mail.ru"), Optional.empty());
    }


    @Test
    void testGettingUserFromDb() {
        Optional<User> userOptional = userRepository.findByEmail("testing@mail.ru");
        Assertions.assertNotEquals(userOptional, null);
    }

    @Test
    void testInsertIntoDb() {
        final String email = "testing@mail.ru";
        final String name = "Test";
        final String surname = "Testov";
        final String picture = "google.com";
        final User userToInsert = new User();
        userToInsert.setEmail(email);
        userToInsert.setName(name);
        userToInsert.setSurname(surname);
        userToInsert.setPicture(picture);
        userRepository.save(userToInsert);
        Optional<User> userOptional = userRepository.findByEmail(email);
        Assertions.assertNotEquals(userOptional, null);
    }
    @MockBean
    SessionService sessionService;
    @MockBean
    UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToProfile() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/profile").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToSessionStudents() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/session/students").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPostSession() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/session").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToTutorList() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/tutor-list").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToStudentsList() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/students-list").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToSubjects() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/requests/subjects").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToRequests() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/requests").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPostRequests() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/requests").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPostEnroll() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/enroll").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPutProfile() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/profile").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToGetProfilebyId() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/profile/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPutServicesById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/services/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToGetServicesByUserId() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/services/user/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToDeleteServicesById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/services/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPutRequestsById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/requests/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToDeleteRequestsById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/requests/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToPutMyStudentsAccept() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/my-students/accept/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToDeleteMyStudents() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/my-students/remove/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldForbidAccessToUnauthenticatedRequestsToGetCardById() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/card/1").header("Authorization", "Bearer 123"))
                .andExpect(status().isForbidden());
    }
}
