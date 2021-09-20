package innotutor.innotutor_backend;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class DatabaseTest {
    @Test
    void testInsertIntoDb() throws SQLException {
        try (Connection connection = DriverManager.getConnection("");
             PreparedStatement ps = connection.prepareStatement("insert into card_rating")) {

        }

    }
}
