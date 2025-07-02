package fr.esgi.color_run.repository;

import fr.esgi.color_run.business.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User save(User u) throws SQLException;
    User findByEmail(String email) throws SQLException;
    User findById(Long id) throws SQLException;
    List<User> findAll() throws SQLException;
}


