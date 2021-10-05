package innotutor.innotutor_backend.security;

import lombok.Data;

@Data
public class CustomPrincipal {
    private String email;
    private String fullName;
}